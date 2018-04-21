package lt.dejavu.product.repository;

import lt.dejavu.product.model.Category;

import java.util.List;

public interface CategoryRepository {

    Category getCategory(long id);

    List<Category> getRootCategories();

    List<Category> getSubCategories(long parentId);

    long saveCategory(Category category);

    void updateCategory(Category category);

    void deleteCategory(Category category);
}
