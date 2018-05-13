package lt.dejavu.excel.strategy;

public interface ProcessingStrategy<T> {
    void process(T item);
}
