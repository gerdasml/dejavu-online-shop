package lt.dejavu.product.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductPropertyDto {
    Long propertyId;
    String name;
    String value;
}
