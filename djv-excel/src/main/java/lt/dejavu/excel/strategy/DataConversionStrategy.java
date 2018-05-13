package lt.dejavu.excel.strategy;

import lt.dejavu.excel.model.ConversionResult;

import java.util.List;

public interface DataConversionStrategy<T> {
    List<List<String>> toRows(T item);
    ConversionResult<T> toItem(List<String> row);
    List<String> getHeader();
}
