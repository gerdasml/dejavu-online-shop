package lt.dejavu.product.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryResponse {

    private long id;
    private String name;
    private String icon;
    private String identifier;
    private Long parentCategoryId;
    private List<CategoryPropertyResponse> properties;
}
