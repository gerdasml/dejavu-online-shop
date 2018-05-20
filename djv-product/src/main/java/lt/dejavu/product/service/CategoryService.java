package lt.dejavu.product.service;

import lt.dejavu.product.response.CategoryResponse;
import lt.dejavu.product.response.CategoryTreeResponse;
import lt.dejavu.product.model.rest.request.CategoryRequest;

import java.util.List;

public interface CategoryService {
    CategoryResponse getCategory(long id);

    List<CategoryResponse> getRootCategories();

    List<CategoryResponse> getSubCategories(long category);

    List<CategoryTreeResponse> getCategoryTree();

    Long createCategory(CategoryRequest categoryRequest);

    void updateCategory(long categoryId, CategoryRequest categoryRequest);

    void deleteCategory(long categoryId);
}
