package lt.dejavu.excel.strategy;

import lt.dejavu.excel.model.ConversionResult;

import java.util.UUID;

public interface ProcessingStrategy<T> {
    void process(UUID jobId, ConversionResult<T> item);
}
