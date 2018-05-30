package lt.dejavu.product.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PropertySummaryDto {
    private Long propertyId;
    private String propertyName;
    private List<String> values;
}
