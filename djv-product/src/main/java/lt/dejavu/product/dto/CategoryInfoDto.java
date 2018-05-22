package lt.dejavu.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryInfoDto {
    private Long productCount;
    private List<PropertySummaryDto> availableProperties;
}
