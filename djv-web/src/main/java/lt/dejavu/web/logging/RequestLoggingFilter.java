package lt.dejavu.web.logging;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class RequestLoggingFilter extends AbstractRequestLoggingFilter {
    private static final Logger log = LogManager.getLogger(RequestLoggingFilter.class);

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        String body;
        try {
            body = IOUtils.toString(request.getInputStream());
        } catch (IOException e) {
            log.error("Failed to read request body", e);
            return;
        }

        log.debug("========================== {} received ====================================", getRequestDescription(request));
        log.debug("Headers     :");
        getHeaders(request).forEach((key, value) -> log.debug("    [{}] {}", key, value));
        log.debug("Request body:");
        Stream.of(body.split("\n")).forEach(log::debug);
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        log.debug("========================== {} handled =====================================", getRequestDescription(request));
    }

    private String getRequestDescription(HttpServletRequest request) {
        return String.format("%s %s", request.getMethod(), request.getRequestURI());
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames != null && headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            String value = request.getHeader(header);
            headers.put(header, value);
        }
        return headers;
    }
}
