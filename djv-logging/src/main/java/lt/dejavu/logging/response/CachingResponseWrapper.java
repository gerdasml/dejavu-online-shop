package lt.dejavu.logging.response;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.PrintWriter;

public class CachingResponseWrapper extends HttpServletResponseWrapper {
    private ServletOutputStream stream;

    public CachingResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public PrintWriter getWriter() {
        return new PrintWriter(stream);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (stream == null) {
            stream = new CachingServletOutputStream(super.getOutputStream());
        }
        return stream;
    }

    public String getCache() {
        return ((CachingServletOutputStream) stream).getCache();
    }
}
