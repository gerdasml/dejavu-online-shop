package lt.dejavu.product.model.rest.mapper;

import lt.dejavu.product.exception.ProductPropertyNotFoundException;
import lt.dejavu.product.model.CategoryProperty;
import lt.dejavu.product.model.Product;
import lt.dejavu.product.model.ProductProperty;
import lt.dejavu.product.model.rest.request.ProductPropertyRequest;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.*;

import java.util.Set;

@Component
public class ProductPropertyRequestMapper {

    public Set<ProductProperty> mapProperties(Product product, Set<CategoryProperty> properties, Set<ProductPropertyRequest> propertyRequests) {
        return propertyRequests.stream().map(req -> mapProperty(product, properties, req)).collect(toSet());
    }

    private ProductProperty mapProperty(Product product, Set<CategoryProperty> properties, ProductPropertyRequest request) {
        ProductProperty property = new ProductProperty();
        property.setProduct(product);
        property.setCategoryProperty(
                properties.stream()
                        .filter(prop -> prop.getId().equals(request.getPropertyId()))
                        .findFirst()
                        .orElseThrow(() -> new ProductPropertyNotFoundException(request.getPropertyId())));
        property.setValue(request.getValue());
        return property;
    }
}
