package lt.dejavu.product.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CategoryTreeResponse {

    CategoryDto category;
    List<CategoryTreeResponse> children;
}
