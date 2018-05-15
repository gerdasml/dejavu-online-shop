package lt.dejavu.excel.strategy;

import lt.dejavu.excel.model.ConversionResult;

import java.util.UUID;

public interface ProcessingStrategy<T> {
    void start(UUID jobId);
    void process(UUID jobId, ConversionResult<T> item);
    void finish(UUID jobId);
}
