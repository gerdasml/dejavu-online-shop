package lt.dejavu.web.logging;

import org.apache.commons.io.IOUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class CachingRequestWrapper extends HttpServletRequestWrapper {
    private ByteArrayOutputStream cache;

    public CachingRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (cache == null)
            cacheInputStream();

        return new CachingServletInputStream(cache.toByteArray());
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    private void cacheInputStream() throws IOException {
        cache = new ByteArrayOutputStream();
        IOUtils.copy(super.getInputStream(), cache);
    }
}
