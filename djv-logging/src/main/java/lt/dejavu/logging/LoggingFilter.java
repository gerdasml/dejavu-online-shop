package lt.dejavu.logging;

import lt.dejavu.logging.logger.RequestLogger;
import lt.dejavu.logging.logger.RequestLoggerImpl;
import lt.dejavu.logging.request.CachingRequestWrapper;
import lt.dejavu.logging.response.CachingResponseWrapper;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoggingFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        // Extract HttpServletRequests
        HttpServletRequest currentRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse currentResponse = (HttpServletResponse) servletResponse;

        // Wrap requests in caching implementations before executing
        CachingRequestWrapper wrappedRequest = new CachingRequestWrapper(currentRequest);
        CachingResponseWrapper wrappedResponse = new CachingResponseWrapper(currentResponse);

        // Create a logger
        RequestLogger logger = new RequestLoggerImpl(wrappedRequest, wrappedResponse);

        logger.logBefore();
        chain.doFilter(wrappedRequest, wrappedResponse);
        logger.logAfter();
    }
}
