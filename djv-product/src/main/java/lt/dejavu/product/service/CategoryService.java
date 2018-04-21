package lt.dejavu.product.service;

import lt.dejavu.product.model.rest.request.CategoryRequest;
import lt.dejavu.product.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto getCategory(long id);

    List<CategoryDto> getRootCategories();

    List<CategoryDto> getSubCategories(long category);

    Long createCategory(CategoryRequest categoryRequest);

    void updateCategory(long categoryId, CategoryRequest categoryRequest);

    void deleteCategory(long categoryId);
}
