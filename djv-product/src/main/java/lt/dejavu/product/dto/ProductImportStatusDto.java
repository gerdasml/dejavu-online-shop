package lt.dejavu.product.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lt.dejavu.excel.model.db.Status;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class ProductImportStatusDto {
    private Status status;
    private int successCount;
    private int failureCount;
    private List<ProductDto> failedProducts;
}
