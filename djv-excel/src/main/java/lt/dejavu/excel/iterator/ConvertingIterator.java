package lt.dejavu.excel.iterator;

import java.util.Iterator;
import java.util.function.Function;

public class ConvertingIterator <T, K> implements Iterator<K> {
    private final Function<T, K> converter;
    private final Iterator<T> original;

    public ConvertingIterator(Iterator<T> original, Function<T, K> converter) {
        this.converter = converter;
        this.original = original;
    }

    @Override
    public boolean hasNext() {
        return original.hasNext();
    }

    @Override
    public K next() {
        return converter.apply(original.next());
    }
}
