package lt.dejavu.logging.request;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class CachingServletInputStream extends ServletInputStream {
    private ByteArrayInputStream input;

    public CachingServletInputStream(byte[] content) {
        /* create a new input stream from the cached request body */
        input = new ByteArrayInputStream(content);
    }

    @Override
    public boolean isFinished() {
        return input.available() == 0;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener listener) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public int read() throws IOException {
        return input.read();
    }
}
