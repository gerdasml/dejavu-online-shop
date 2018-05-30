package lt.dejavu.product.repository;

import lt.dejavu.product.model.CategoryProperty;
import lt.dejavu.product.model.ProductProperty;

import java.util.List;
import java.util.Set;

public interface PropertyRepository {

    void saveProperties(Set<CategoryProperty> properties);

    Set<CategoryProperty> findByIds(Set<Long> ids);

    List<CategoryProperty> getCategoryProperties(long categoryId);

    void savePropertyValues(Set<ProductProperty> productProperties);
}
