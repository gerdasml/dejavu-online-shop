package lt.dejavu.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPropertyDto {
    Long propertyId;
    String name;
    String value;
}
