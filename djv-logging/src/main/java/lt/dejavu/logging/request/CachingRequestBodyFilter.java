package lt.dejavu.logging.request;

import lt.dejavu.logging.response.CachingPrintWriter;
import lt.dejavu.logging.response.CachingResponseWrapper;
import lt.dejavu.logging.response.CachingServletOutputStream;
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
public class CachingRequestBodyFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest currentRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse currentResponse = (HttpServletResponse) servletResponse;

        CachingRequestWrapper wrappedRequest = new CachingRequestWrapper(currentRequest);
        CachingResponseWrapper wrappedResponse = new CachingResponseWrapper(currentResponse);

        chain.doFilter(wrappedRequest, wrappedResponse);

        String cache = ((CachingServletOutputStream) wrappedResponse.getOutputStream()).getCache();
        System.out.println(cache);
    }
}
