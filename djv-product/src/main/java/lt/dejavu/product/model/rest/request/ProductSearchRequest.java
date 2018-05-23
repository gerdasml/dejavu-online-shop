package lt.dejavu.product.model.rest.request;

import lombok.Getter;
import lombok.Setter;
import lt.dejavu.product.dto.ProductPropertyDto;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductSearchRequest {
    private String categoryIdentifier;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private List<ProductPropertyDto> properties;
}
