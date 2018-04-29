package lt.dejavu.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryTreeDto {

    CategoryDto category;
    List<CategoryTreeDto> children;
}
