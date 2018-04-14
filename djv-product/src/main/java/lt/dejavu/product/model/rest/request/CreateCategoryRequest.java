package lt.dejavu.product.model.rest.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CreateCategoryRequest {
    private String name;
    private String iconName;
    private String displayName;
    private Long parentCategory;
}
