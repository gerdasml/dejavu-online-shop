package lt.dejavu.product.service;

import lt.dejavu.product.dto.CategoryDto;
import lt.dejavu.product.dto.CategoryTreeDto;
import lt.dejavu.product.model.rest.request.CategoryRequest;

import java.util.List;

public interface CategoryService {
    CategoryDto getCategory(long id);

    List<CategoryDto> getRootCategories();

    List<CategoryDto> getSubCategories(long category);

    List<CategoryTreeDto> getCategoryTree();

    Long createCategory(CategoryRequest categoryRequest);

    void updateCategory(long categoryId, CategoryRequest categoryRequest);

    void deleteCategory(long categoryId);
}
