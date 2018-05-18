package lt.dejavu.product.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPropertyResponse {
    Long propertyId;
    String name;
    String value;
}
