package lt.dejavu.product.model.rest.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CategoryPropertyRequest {
    private Long propertyId;
    private String name;
}
