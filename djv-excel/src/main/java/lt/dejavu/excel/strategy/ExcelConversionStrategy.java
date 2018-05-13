package lt.dejavu.excel.strategy;

import lt.dejavu.excel.iterator.PeekingIterator;
import lt.dejavu.excel.model.ConversionResult;

import java.util.List;

public interface ExcelConversionStrategy<T> {
    List<List<String>> toRows(T item);

    List<String> getHeader();

    ConversionResult<T> takeOne(PeekingIterator<List<String>> rowIterator);
}
