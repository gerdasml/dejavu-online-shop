package lt.dejavu.product.model.rest.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.dejavu.product.dto.ProductPropertyDto;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
public class ProductSearchRequest {
    private String categoryIdentifier;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private List<ProductPropertyDto> properties;
    private String name;
}
