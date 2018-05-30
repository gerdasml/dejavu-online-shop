package lt.dejavu.product.repository;

import lt.dejavu.product.model.CategoryProperty;
import lt.dejavu.product.model.ProductProperty;

import java.util.Set;

public interface ProductPropertyRepository {

    void saveProperties(Set<CategoryProperty> properties);

    Set<CategoryProperty> findByIds(Set<Long> ids);

    void savePropertyValues(Set<ProductProperty> productProperties);
}
