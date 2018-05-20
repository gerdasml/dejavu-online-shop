package lt.dejavu.product.strategy;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.dejavu.excel.model.ConversionResult;
import lt.dejavu.excel.model.ConversionStatus;
import lt.dejavu.excel.model.db.FailedImportItem;
import lt.dejavu.excel.model.db.ImportStatus;
import lt.dejavu.excel.model.db.Status;
import lt.dejavu.excel.repository.ImportStatusRepository;
import lt.dejavu.excel.strategy.ProcessingStrategy;
import lt.dejavu.product.model.Product;
import lt.dejavu.product.repository.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Component
public class ProductProcessingStrategy implements ProcessingStrategy<Product> {
    private final ImportStatusRepository importStatusRepository;
    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;
    private final Logger logger = LogManager.getLogger(ProductProcessingStrategy.class);

    public ProductProcessingStrategy(ImportStatusRepository importStatusRepository, ProductRepository productRepository, ObjectMapper objectMapper) {
        this.importStatusRepository = importStatusRepository;
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void start(UUID jobId) {
        ImportStatus status = new ImportStatus();
        status.setId(jobId);
        status.setStatus(Status.ANALYZING);
        status.setFailedItems(new LinkedList<>());
        status.setStartTime(Timestamp.from(Instant.now()));
        importStatusRepository.createImportStatus(status);
    }

    @Override
    public void finishAnalysis(UUID jobId, int dataItemCount) {
        ImportStatus status = importStatusRepository.getImportStatus(jobId);
        status.setTotal(dataItemCount);
        status.setStatus(Status.IMPORTING);
        importStatusRepository.updateImportStatus(status);
    }

    @Override
    public void process(UUID jobId, ConversionResult<Product> item) {
        if (item.getStatus() == ConversionStatus.SUCCESS)
            processSuccess(jobId, item.getResult());
        else
            processFailure(jobId, item.getResult());
    }

    @Override
    public void fail(UUID jobId) {
        ImportStatus status = importStatusRepository.getImportStatus(jobId);
        status.setStatus(Status.FAILED);
        importStatusRepository.updateImportStatus(status);
    }

    @Override
    public void finish(UUID jobId) {
        ImportStatus status = importStatusRepository.getImportStatus(jobId);
        status.setStatus(Status.FINISHED);
        importStatusRepository.updateImportStatus(status);
    }

    private void processSuccess(UUID jobId, Product product) {
        productRepository.saveProduct(product);
        ImportStatus status = importStatusRepository.getImportStatus(jobId);
        status.setSuccessCount(status.getSuccessCount() + 1);
        importStatusRepository.updateImportStatus(status);
    }

    private void processFailure(UUID jobId, Product product) {
        ImportStatus status = importStatusRepository.getImportStatus(jobId);
        try {
            String newPayload = objectMapper.writeValueAsString(product);
            status.getFailedItems().add(new FailedImportItem(status, newPayload));
        } catch (IOException e) {
            logger.warn(e);
        }

        status.setFailureCount(status.getFailureCount() + 1);
        importStatusRepository.updateImportStatus(status);
    }
}
