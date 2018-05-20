package lt.dejavu.product.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryTreeResponse {

    CategoryResponse category;
    List<CategoryTreeResponse> children;
}
