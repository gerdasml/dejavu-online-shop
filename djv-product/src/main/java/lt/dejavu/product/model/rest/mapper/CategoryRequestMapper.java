package lt.dejavu.product.model.rest.mapper;

import lt.dejavu.product.model.Category;
import lt.dejavu.product.model.rest.request.CategoryRequest;
import org.springframework.stereotype.Component;

@Component
public class CategoryRequestMapper {
    public Category mapToCategory(CategoryRequest categoryRequest, Category parentCategory){
        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setIconName(categoryRequest.getIconName());
        category.setIdentifier(categoryRequest.getIdentifier());
        category.setParentCategory(parentCategory);
        return category;
    }
}
