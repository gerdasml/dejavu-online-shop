package lt.dejavu.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryDto {

    private long id;
    private String name;
    private String icon;
    private String identifier;
    private Long parentCategoryId;
    private List<String> properties;
}
