package lt.dejavu.product.strategy;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.dejavu.excel.model.ConversionResult;
import lt.dejavu.excel.model.ConversionStatus;
import lt.dejavu.excel.model.db.ImportStatus;
import lt.dejavu.excel.model.db.Status;
import lt.dejavu.excel.repository.ImportStatusRepository;
import lt.dejavu.excel.strategy.ProcessingStrategy;
import lt.dejavu.product.dto.ProductDto;
import lt.dejavu.product.dto.ProductPropertyDto;
import lt.dejavu.product.model.rest.request.ProductPropertyRequest;
import lt.dejavu.product.model.rest.request.ProductRequest;
import lt.dejavu.product.service.ProductService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Component
public class ProductProcessingStrategy implements ProcessingStrategy<ProductDto> {
    private final ImportStatusRepository importStatusRepository;
    private final ProductService productService;
    private final ObjectMapper objectMapper;

    public ProductProcessingStrategy(ImportStatusRepository importStatusRepository, ProductService productService, ObjectMapper objectMapper) {
        this.importStatusRepository = importStatusRepository;
        this.productService = productService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void start(UUID jobId) {
        ImportStatus status = new ImportStatus();
        status.setId(jobId);
        status.setStatus(Status.RUNNING);
        importStatusRepository.createImportStatus(status);
    }

    @Override
    public void process(UUID jobId, ConversionResult<ProductDto> item) {
        if (item.getStatus() == ConversionStatus.SUCCESS) processSuccess(jobId, item.getResult());
        else processFailure(jobId, item.getResult());
    }

    @Override
    public void finish(UUID jobId) {
        ImportStatus status = importStatusRepository.getImportStatus(jobId);
        status.setStatus(Status.FINISHED);
        importStatusRepository.updateImportStatus(status);
    }

    private void processSuccess(UUID jobId, ProductDto product) {
        productService.createProduct(buildProductRequest(product));
        ImportStatus status = importStatusRepository.getImportStatus(jobId);
        status.setSuccessCount(status.getSuccessCount() + 1);
        importStatusRepository.updateImportStatus(status);
    }

    private void processFailure(UUID jobId, ProductDto product) {
        ImportStatus status = importStatusRepository.getImportStatus(jobId);

        List<ProductDto> failedProducts = new ArrayList<>();

        try {
            if (status.getFailureCount() > 0) {
                failedProducts = objectMapper.readValue(status.getFailedItems(), new TypeReference<List<ProductDto>>() {
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

    private ProductRequest buildProductRequest(ProductDto dto) {
        ProductRequest req = new ProductRequest();
        req.setCategoryId(dto.getCategoryId());
        req.setCreationDate(LocalDateTime.now());
        req.setDescription(dto.getDescription());
        req.setName(dto.getName());
        req.setPrice(dto.getPrice());
        req.setProperties(dto.getProperties().stream().map(this::buildProductPropertyRequest).collect(toList()));

        return req;
    }

    private ProductPropertyRequest buildProductPropertyRequest(ProductPropertyDto prop) {
        ProductPropertyRequest ppr = new ProductPropertyRequest();
        ppr.setName(prop.getName());
        ppr.setValue(prop.getValue());
        return ppr;
    }
}
