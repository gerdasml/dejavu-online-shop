package lt.dejavu.excel.iterator;

import java.util.Iterator;

public class PeekingIterator<T> implements Iterator<T> {
    private final Iterator<T> original;
    private T peek;

    public PeekingIterator(Iterator<T> original) {
        this.original = original;
    }

    @Override
    public boolean hasNext() {
        if (peek != null) return true;
        return original.hasNext();
    }

    @Override
    public T next() {
        if (peek != null) {
            T res = peek;
            peek = null;
            return res;
        }
        return original.next();
    }

    public T peek() {
        if (peek == null) {
            peek = original.next();
        }
        return peek;
    }
}
