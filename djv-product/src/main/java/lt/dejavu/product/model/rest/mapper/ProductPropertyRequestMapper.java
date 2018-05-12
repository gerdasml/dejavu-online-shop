package lt.dejavu.product.model.rest.mapper;

import lt.dejavu.product.exception.ProductPropertyNotFoundException;
import lt.dejavu.product.model.Product;
import lt.dejavu.product.model.ProductProperty;
import lt.dejavu.product.model.ProductPropertyValue;
import lt.dejavu.product.model.rest.request.ProductPropertyRequest;
import org.springframework.stereotype.Component;
import static java.util.stream.Collectors.*;
import java.util.List;
import java.util.Set;

@Component
public class ProductPropertyRequestMapper {

    public Set<ProductPropertyValue> mapProperties(Product product, Set<ProductProperty> properties, Set<ProductPropertyRequest> propertyRequests){
            return propertyRequests.stream().map(req -> mapProperty(product,properties,req )).collect(toSet());
    }

    private ProductPropertyValue mapProperty(Product product, Set<ProductProperty> properties, ProductPropertyRequest request){
        ProductPropertyValue property = new ProductPropertyValue();
        property.setProduct(product);
        property.setProductProperty(
                properties.stream()
                    .filter(prop -> prop.getId().equals(request.getPropertyId()))
                    .findFirst()
                    .orElseThrow(() -> new ProductPropertyNotFoundException(request.getPropertyId())));
        property.setValue(request.getPropertyValue());
        return property;
    }
}
