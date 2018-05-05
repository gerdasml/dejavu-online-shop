package lt.dejavu.product.model.rest.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ProductPropertyRequest {
    String name;
    String value;
}