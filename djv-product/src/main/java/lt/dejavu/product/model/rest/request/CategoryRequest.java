package lt.dejavu.product.model.rest.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CategoryRequest {
    private String name;
    private String icon;
    private String identifier;
    private Long parentCategoryId;
    private List<CategoryPropertyRequest> properties;
}
