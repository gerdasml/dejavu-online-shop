package lt.dejavu.product.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.dejavu.excel.model.db.Status;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ProductImportStatusDto {
    private UUID id;
    private Status status;
    private int successCount;
    private int failureCount;
    private int total;
    private List<ProductDto> failedProducts;
    private Instant startTime;
}
