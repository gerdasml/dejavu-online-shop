package lt.dejavu.product.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lt.dejavu.excel.model.db.Status;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
public class ProductImportStatusResponse {
    private UUID id;
    private Status status;
    private int successCount;
    private int failureCount;
    private int total;
    private List<ProductResponse> failedProducts;
    private Instant startTime;
}
