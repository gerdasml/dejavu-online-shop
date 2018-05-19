package lt.dejavu.product.response.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.dejavu.excel.model.db.ImportStatus;
import lt.dejavu.product.response.ProductDto;
import lt.dejavu.product.response.ProductImportStatusDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class ProductImportStatusDtoMapper {
    private final ObjectMapper objectMapper;
    private final Logger logger = LogManager.getLogger(ProductImportStatusDtoMapper.class);

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
        dto.setFailedProducts(status.getFailedItems().stream().map(this::parseProductDtoString).collect(toList()));

        return dto;
    }

    private ProductDto parseProductDtoString(String item){
        try {
            return objectMapper.readValue(item, new TypeReference<ProductDto>() {
            });
        } catch (IOException ex) {
            logger.warn(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    public ImportStatus map(ProductImportStatusDto dto) {
        ImportStatus status = new ImportStatus();
        status.setStatus(dto.getStatus());
        status.setId(dto.getId());
        status.setStartTime(Timestamp.from(dto.getStartTime()));
        status.setFailedItems(dto.getFailedProducts().stream().map(this::productDtoToString).collect(toList()));
        status.setFailureCount(dto.getFailureCount());
        status.setSuccessCount(dto.getSuccessCount());
        status.setTotal(dto.getTotal());

        return status;
    }

    private String productDtoToString(ProductDto item){
        try {
            return objectMapper.writeValueAsString(item);
        } catch (IOException ex) {
            logger.warn(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    public List<ProductImportStatusDto> map(List<ImportStatus> statuses) {
        return statuses.stream().map(this::map).collect(toList());
    }
}
