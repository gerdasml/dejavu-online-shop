package lt.dejavu.product.view;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryView {

    private long id;
    private String name;
    private String iconName;
    private String displayName;
    private CategoryView parentCategory;
    private Long parentCategoryId;


}
