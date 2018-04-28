package lt.dejavu.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {

    private long id;
    private String name;
    private String icon;
    private String displayName;
    private Long parentCategoryId;


}
