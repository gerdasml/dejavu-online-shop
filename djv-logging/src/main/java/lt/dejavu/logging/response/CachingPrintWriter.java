package lt.dejavu.logging.response;

import java.io.PrintWriter;
import java.io.Writer;

public class CachingPrintWriter extends PrintWriter {
    private StringBuilder cache = new StringBuilder();

    public CachingPrintWriter(Writer out) {
        super(out);
    }

    @Override
    public void write(int c) {
        cache.append((char)c);
        super.write(c);
    }

    @Override
    public void write(char[] chars, int offset, int length) {
        cache.append(chars, offset, length);
        super.write(chars, offset, length);
    }

    @Override
    public void write(String string, int offset, int length) {
        cache.append(string, offset, length);
        super.write(string, offset, length);
    }

    public String getCache() {
        return cache.toString();
    }
}
