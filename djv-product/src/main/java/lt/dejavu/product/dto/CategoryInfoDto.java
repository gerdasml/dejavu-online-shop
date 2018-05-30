package lt.dejavu.product.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
public class CategoryInfoDto {
    private Long productCount;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private List<PropertySummaryDto> availableProperties;
}
