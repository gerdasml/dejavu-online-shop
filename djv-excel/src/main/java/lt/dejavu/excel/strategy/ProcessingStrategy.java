package lt.dejavu.excel.strategy;

import lt.dejavu.excel.model.ConversionResult;

import java.util.UUID;

public interface ProcessingStrategy<T> {
    void start(UUID jobId);
    void finishAnalysis(UUID jobId, int dataItemCount);
    void process(UUID jobId, ConversionResult<T> item);
    void fail(UUID jobId);
    void finish(UUID jobId);
}
