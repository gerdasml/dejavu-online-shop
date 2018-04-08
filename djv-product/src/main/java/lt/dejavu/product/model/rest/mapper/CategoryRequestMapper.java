package lt.dejavu.product.model.rest.mapper;

import lt.dejavu.product.model.Category;
import lt.dejavu.product.model.rest.request.CreateCategoryRequest;
import org.springframework.stereotype.Component;

@Component
public class CategoryRequestMapper {
    
    public Category mapToCategory(CreateCategoryRequest categoryRequest){
        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setIconName(categoryRequest.getIconName());
        category.setDisplayName(categoryRequest.getDisplayName());
        return category;
    }


}
