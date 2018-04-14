package lt.dejavu.product.service;

import lt.dejavu.product.model.rest.request.CreateCategoryRequest;
import lt.dejavu.product.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto getCategory(long id);

    List<CategoryDto> getRootCategories();

    List<CategoryDto> getSubCategories(long category);

    Long createCategory(CreateCategoryRequest categoryRequest);
}
