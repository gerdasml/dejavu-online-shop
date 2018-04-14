package lt.dejavu.product.service;

import lt.dejavu.product.model.rest.request.CategoryRequest;
import lt.dejavu.product.view.CategoryView;

import java.util.List;

public interface CategoryService {
    CategoryView getCategory(long id);

    List<CategoryView> getRootCategories();

    List<CategoryView> getSubCategories(long category);

    Long createCategory(CategoryRequest categoryRequest);
}
