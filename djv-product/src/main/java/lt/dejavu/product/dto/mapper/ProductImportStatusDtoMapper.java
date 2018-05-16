package lt.dejavu.product.dto.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.dejavu.excel.model.db.ImportStatus;
import lt.dejavu.product.dto.ProductDto;
import lt.dejavu.product.dto.ProductImportStatusDto;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class ProductImportStatusDtoMapper {
    private final ObjectMapper objectMapper;

    public ProductImportStatusDtoMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ProductImportStatusDto map(ImportStatus status) {
        ProductImportStatusDto dto = new ProductImportStatusDto();
        dto.setId(status.getId());
        dto.setFailureCount(status.getFailureCount());
        dto.setSuccessCount(status.getSuccessCount());
        dto.setTotal(status.getTotal());
        dto.setStatus(status.getStatus());
        dto.setStartTime(status.getStartTime().toInstant());
        try {
            dto.setFailedProducts(objectMapper.readValue(status.getFailedItems(), new TypeReference<List<ProductDto>>(){}));
        } catch (IOException ignored) {}

        return dto;
    }

    public ImportStatus map(ProductImportStatusDto dto) {
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

    public List<ProductImportStatusDto> map(List<ImportStatus> statuses) {
        return statuses.stream().map(this::map).collect(toList());
    }
}
