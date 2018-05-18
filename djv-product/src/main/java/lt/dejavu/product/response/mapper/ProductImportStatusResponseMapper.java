package lt.dejavu.product.response.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.dejavu.excel.model.db.ImportStatus;
import lt.dejavu.product.response.ProductImportStatusResponse;
import lt.dejavu.product.response.ProductResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class ProductImportStatusResponseMapper {
    private final ObjectMapper objectMapper;

    public ProductImportStatusResponseMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ProductImportStatusResponse map(ImportStatus status) {
        ProductImportStatusResponse dto = new ProductImportStatusResponse();
        dto.setId(status.getId());
        dto.setFailureCount(status.getFailureCount());
        dto.setSuccessCount(status.getSuccessCount());
        dto.setTotal(status.getTotal());
        dto.setStatus(status.getStatus());
        dto.setStartTime(status.getStartTime().toInstant());
        try {
            dto.setFailedProducts(objectMapper.readValue(status.getFailedItems(), new TypeReference<List<ProductResponse>>(){}));
        } catch (IOException ignored) {}

        return dto;
    }

    public ImportStatus map(ProductImportStatusResponse dto) {
        ImportStatus status = new ImportStatus();
        status.setStatus(dto.getStatus());
        status.setId(dto.getId());
        status.setStartTime(Timestamp.from(dto.getStartTime()));
        try {
            status.setFailedItems(objectMapper.writeValueAsString(dto.getFailedProducts()));
        } catch (JsonProcessingException ignored) { }
        status.setFailureCount(dto.getFailureCount());
        status.setSuccessCount(dto.getSuccessCount());
        status.setTotal(dto.getTotal());

        return status;
    }

    public List<ProductImportStatusResponse> map(List<ImportStatus> statuses) {
        return statuses.stream().map(this::map).collect(toList());
    }
}
