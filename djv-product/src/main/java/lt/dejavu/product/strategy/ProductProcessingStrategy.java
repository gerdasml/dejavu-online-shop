package lt.dejavu.product.strategy;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.dejavu.excel.model.ConversionResult;
import lt.dejavu.excel.model.ConversionStatus;
import lt.dejavu.excel.model.db.ImportStatus;
import lt.dejavu.excel.model.db.Status;
import lt.dejavu.excel.repository.ImportStatusRepository;
import lt.dejavu.excel.strategy.ProcessingStrategy;
import lt.dejavu.product.model.Product;
import lt.dejavu.product.model.ProductProperty;
import lt.dejavu.product.model.rest.request.ProductPropertyRequest;
import lt.dejavu.product.model.rest.request.ProductRequest;
import lt.dejavu.product.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Component
public class ProductProcessingStrategy implements ProcessingStrategy<Product> {
    private final ImportStatusRepository importStatusRepository;
    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    public ProductProcessingStrategy(ImportStatusRepository importStatusRepository, ProductRepository productRepository, ObjectMapper objectMapper) {
        this.importStatusRepository = importStatusRepository;
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void start(UUID jobId) {
        ImportStatus status = new ImportStatus();
        status.setId(jobId);
        status.setStatus(Status.RUNNING);
        status.setFailedItems("[]");
        status.setStartTime(Timestamp.from(Instant.now()));
        importStatusRepository.createImportStatus(status);
    }

    @Override
    public void process(UUID jobId, ConversionResult<Product> item) {
        try {
            Thread.sleep(5000); // TODO: remove this after testing is done
        } catch (InterruptedException e) {

        }
        if (item.getStatus() == ConversionStatus.SUCCESS) processSuccess(jobId, item.getResult());
        else processFailure(jobId, item.getResult());
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

        List<Product> failedProducts = new ArrayList<>();

        try {
            if (status.getFailureCount() > 0) {
                failedProducts = objectMapper.readValue(status.getFailedItems(), new TypeReference<List<Product>>() {
                });
            }
            failedProducts.add(product);
            String newPayload = objectMapper.writeValueAsString(failedProducts);
            status.setFailedItems(newPayload);
        } catch (IOException ignored) {
        }

        status.setFailureCount(status.getFailureCount() + 1);
        importStatusRepository.updateImportStatus(status);
    }
}
