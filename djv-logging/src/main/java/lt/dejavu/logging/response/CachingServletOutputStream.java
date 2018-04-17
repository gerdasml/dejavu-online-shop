package lt.dejavu.logging.response;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;

public class CachingServletOutputStream extends ServletOutputStream {
    private StringWriter cache = new StringWriter();
    private OutputStream original;

    public CachingServletOutputStream(OutputStream original) {
        this.original = original;
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setWriteListener(WriteListener listener) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void write(int b) throws IOException {
        cache.write(b);
        original.write(b);
    }

    public String getCache() {
        return cache.toString();
    }
}
// https://gist.github.com/kamlesh0606/fa94281dd2e64908b035