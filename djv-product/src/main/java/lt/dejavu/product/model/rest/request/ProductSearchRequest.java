package lt.dejavu.product.model.rest.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductSearchRequest {
    private String categoryIdentifier;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
}
