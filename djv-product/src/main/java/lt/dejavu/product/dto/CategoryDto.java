package lt.dejavu.product.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CategoryDto {

    private Long id;
    private String name;
    private String icon;
    private String identifier;
    private Long parentCategoryId;
    private List<CategoryPropertyDto> properties;
}
