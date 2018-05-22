package lt.dejavu.product.repository;

import lt.dejavu.product.model.Category;
import lt.dejavu.product.model.ProductProperty;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface CategoryRepository {

    Category getCategory(long id);

    Category getCategory(String identifier);

    Set<Category> getRootCategories();

    Set<Category> getSubCategories(long parentId);

    long saveCategory(Category category);

    void updateCategory(Category category);

    void deleteCategory(Category category);

    Set<Category> getAllCategories();

    List<ProductProperty> getProductPropertiesForCategory(long categoryId);

    long getProductCount(long categoryId);

    BigDecimal getMinimumProductPrice(long categoryId);

    BigDecimal getMaximumProductPrice(long categoryId);
}
