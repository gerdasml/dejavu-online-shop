package lt.dejavu.product.service;

import lt.dejavu.product.model.Category;
import lt.dejavu.product.model.rest.request.CreateCategoryRequest;

import java.util.List;

public interface CategoryService {
    Category getCategory(long id);

    List<Category> getRootCategories();

    List<Category> getSubCategories(long category);

    Long createCategory(CreateCategoryRequest categoryRequest);
}
