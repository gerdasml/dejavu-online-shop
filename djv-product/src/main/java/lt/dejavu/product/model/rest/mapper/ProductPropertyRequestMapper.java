package lt.dejavu.product.model.rest.mapper;

import lt.dejavu.product.model.ProductProperty;
import lt.dejavu.product.model.rest.request.ProductPropertyRequest;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProductPropertyRequestMapper {

    public ProductProperty map(ProductPropertyRequest request) {
        ProductProperty productProperty = new ProductProperty();
        productProperty.setName(request.getName());
        productProperty.setValue(request.getValue());
        return productProperty;
    }

    public Set<ProductProperty> map(Collection<ProductPropertyRequest> products) {
        return products.stream().map(this::map).collect(Collectors.toSet());
    }
}
