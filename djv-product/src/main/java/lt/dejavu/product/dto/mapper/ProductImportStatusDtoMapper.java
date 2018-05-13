package lt.dejavu.product.dto.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.dejavu.excel.model.db.ImportStatus;
import lt.dejavu.product.dto.ProductDto;
import lt.dejavu.product.dto.ProductImportStatusDto;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ProductImportStatusDtoMapper {
    private final ObjectMapper objectMapper;

    public ProductImportStatusDtoMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ProductImportStatusDto map(ImportStatus status) {
        ProductImportStatusDto dto = new ProductImportStatusDto();
        dto.setFailureCount(status.getFailureCount());
        dto.setSuccessCount(status.getSuccessCount());
        dto.setStatus(status.getStatus());
        try {
            dto.setFailedProducts(objectMapper.readValue(status.getFailedItems(), new TypeReference<List<ProductDto>>(){}));
        } catch (IOException ignored) {}

        return dto;
    }
}
