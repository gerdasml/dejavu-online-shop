package lt.dejavu.product.model.rest.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ProductPropertyRequest {
    private long propertyId;
    private String name; // not used, but still exist for update create consistency
    private String value;
}
