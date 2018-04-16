package lt.dejavu.payment.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {
    private final static Logger log = LoggerFactory.getLogger(LoggingRequestInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        traceRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        traceResponse(response);
        return response;
    }

    private void traceRequest(HttpRequest request, byte[] body) {
        log.debug("--------------------------- begin request {} ---------------------------------------------", getRequestDescription(request));
        log.debug("Headers     :");
        request.getHeaders().forEach((key, value) -> log.debug("    [{}] {}", key, value));
        log.debug("Request body:");
        Stream.of(new String(body).split("\n")).forEach(log::debug);
        log.debug("-------------------------- end request {} ------------------------------------------------", getRequestDescription(request));
    }

    private void traceResponse(ClientHttpResponse response) throws IOException {
        StringBuilder inputStringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"));
        String line = bufferedReader.readLine();
        while (line != null) {
            inputStringBuilder.append(line);
            inputStringBuilder.append('\n');
            line = bufferedReader.readLine();
        }
        log.debug("~~~~~~~~~~~~~~~~~~~~~~~~~~~~ begin response ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.debug("Status code  : {}", response.getStatusCode());
        log.debug("Status text  : {}", response.getStatusText());
        log.debug("Headers      :");
        response.getHeaders().forEach((key, value) -> log.debug("    [{}] {}", key, value));
        log.debug("Response body:");
        Stream.of(inputStringBuilder.toString().split("\n")).forEach(log::debug);
        log.debug("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ end response ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    private String getRequestDescription(HttpRequest request) {
        return String.format("%s %s", request.getMethod(), request.getURI());
    }
}
