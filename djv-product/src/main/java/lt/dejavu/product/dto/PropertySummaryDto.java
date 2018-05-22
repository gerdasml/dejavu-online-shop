package lt.dejavu.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PropertySummaryDto {
    private Long propertyId;
    private String propertyName;
    private List<String> values;
}
