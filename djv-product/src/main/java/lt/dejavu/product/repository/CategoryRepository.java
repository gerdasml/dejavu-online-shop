package lt.dejavu.product.repository;

import lt.dejavu.product.model.Category;

import java.util.Set;

public interface CategoryRepository {

    Category getCategory(long id);

    Set<Category> getRootCategories();

    Set<Category> getSubCategories(long parentId);

    long saveCategory(Category category);

    void updateCategory(Category category);

    void deleteCategory(Category category);

    Set<Category> getAllCategories();
}
