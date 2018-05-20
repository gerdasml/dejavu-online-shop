package lt.dejavu.product.service;

import lt.dejavu.product.dto.CategoryDto;
import lt.dejavu.product.dto.CategoryTreeResponse;

import java.util.List;

public interface CategoryService {
    CategoryDto getCategory(long id);

    List<CategoryDto> getRootCategories();

    List<CategoryDto> getSubCategories(long category);

    List<CategoryTreeResponse> getCategoryTree();

    Long createCategory(CategoryDto categoryDto);

    void updateCategory(long categoryId, CategoryDto categoryDto);

    void deleteCategory(long categoryId);
}
