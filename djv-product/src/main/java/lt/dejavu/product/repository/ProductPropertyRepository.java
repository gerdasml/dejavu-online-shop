package lt.dejavu.product.repository;

import lt.dejavu.product.model.ProductProperty;
import lt.dejavu.product.model.ProductPropertyValue;

import java.util.Set;

public interface ProductPropertyRepository {

    void saveProperties(Set<ProductProperty> properties);

    Set<ProductProperty> findByIds(Set<Long> ids);

    void savePropertyValues (Set<ProductPropertyValue> productPropertyValues);
}
