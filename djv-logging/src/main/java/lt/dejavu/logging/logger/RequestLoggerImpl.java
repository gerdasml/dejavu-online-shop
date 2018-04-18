package lt.dejavu.logging.logger;

import lt.dejavu.logging.request.CachingRequestWrapper;
import lt.dejavu.logging.response.CachingResponseWrapper;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class RequestLoggerImpl implements RequestLogger {
    private static final Logger log = LogManager.getLogger(RequestLoggerImpl.class);
    private static final String API_PREFIX = "/api";

    private final CachingRequestWrapper request;
    private final CachingResponseWrapper response;


    public RequestLoggerImpl(CachingRequestWrapper request, CachingResponseWrapper response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public void logBefore() {
        if (!isApiCall()) {
            log.debug("Received {}", getRequestDescription());
            return;
        }
        String body;
        try {
            body = IOUtils.toString(request.getInputStream());
        } catch (IOException e) {
            log.error("Failed to read request body: {}", e.getMessage());
            return;
        }

        log.debug("========================== {} received ==========================", getRequestDescription());
        log.debug("Request headers       :");
        getHeaders(request.getHeaderNames(), request::getHeader).forEach((key, value) -> log.debug("    [{}] {}", key, value));
        log.debug("Request body          :");
        Stream.of(body.split("\n")).forEach(log::debug);
    }

    @Override
    public void logAfter() {
        if (!isApiCall()) return;
        log.debug("Response status code  : {}", response.getStatus());
        log.debug("Response headers      : ");
        getHeaders(response.getHeaderNames(), response::getHeader).forEach((key, value) -> log.debug("    [{}] {}", key, value));
        log.debug("Response body         : {}", response.getCache());

        log.debug("========================== {} handled ==========================", getRequestDescription());
    }

    private Map<String, String> getHeaders(Enumeration<String> headerNames, Function<String, String> headerValueProvider) {
        List<String> headerList = new ArrayList<>();
        while (headerNames != null && headerNames.hasMoreElements()) {
            headerList.add(headerNames.nextElement());
        }
        return getHeaders(headerList, headerValueProvider);
    }

    private Map<String, String> getHeaders(Collection<String> headerNames, Function<String, String> headerValueProvider) {
        Map<String, String> headers = new HashMap<>();
        headerNames.forEach(header -> headers.put(header, headerValueProvider.apply(header)));
        return headers;
    }

    private boolean isApiCall() {
        return request.getRequestURI().startsWith(API_PREFIX);
    }

    private String getRequestDescription() {
        return String.format("%s %s", request.getMethod(), request.getRequestURI());
    }
}
