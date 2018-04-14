package lt.dejavu.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {

    private long id;
    private String name;
    private String iconName;
    private String displayName;
    private CategoryDto parentCategory;
    private Long parentCategoryId;


}
