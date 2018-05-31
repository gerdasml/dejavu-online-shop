package lt.dejavu.product.service;

import lt.dejavu.product.dto.*;

import java.util.List;

public interface CategoryService {
    CategoryDto getCategory(long id);

    List<CategoryDto> getRootCategories();

    CategoryDto getCategoryByIdentifier(String identifier);

    List<CategoryDto> getSubCategories(long category);

    List<CategoryTreeResponse> getCategoryTree();

    Long createCategory(CategoryDto categoryDto);

    void updateCategory(long categoryId, CategoryDto categoryDto);

    void deleteCategory(long categoryId);

    List<CategoryPropertyDto> getCategoryProperties(long categoryId);

    CategoryInfoDto getCategoryInfo(long categoryId);
}
