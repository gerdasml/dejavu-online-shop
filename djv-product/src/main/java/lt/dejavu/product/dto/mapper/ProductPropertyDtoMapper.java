package lt.dejavu.product.dto.mapper;

import lt.dejavu.product.dto.ProductPropertyDto;
import lt.dejavu.product.exception.ProductPropertyNotFoundException;
import lt.dejavu.product.model.CategoryProperty;
import lt.dejavu.product.model.Product;
import lt.dejavu.product.model.ProductProperty;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@Component
public class ProductPropertyDtoMapper {

    public ProductPropertyDto map(ProductProperty productProperty) {
        if (productProperty == null) {
            return null;
        }
        ProductPropertyDto dto = new ProductPropertyDto();
        dto.setPropertyId(productProperty.getCategoryProperty().getId());
        dto.setName(productProperty.getCategoryProperty().getName());
        dto.setValue(productProperty.getValue());
        return dto;
    }

    public Set<ProductPropertyDto> map(Collection<ProductProperty> productProperties) {
        return productProperties.stream().map(this::map).collect(Collectors.toSet());
    }

    public Set<ProductProperty> mapProperties(Product product, Set<CategoryProperty> properties, Set<ProductPropertyDto> propertyRequests) {
        return propertyRequests.stream().map(req -> mapProperty(product, properties, req)).collect(toSet());
    }

    private ProductProperty mapProperty(Product product, Set<CategoryProperty> properties, ProductPropertyDto request) {
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
