package lt.dejavu.product.response.mapper;

import lt.dejavu.product.response.ProductPropertyDto;
import lt.dejavu.product.model.ProductProperty;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductPropertiesResponseMapper {

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

    public List<ProductPropertyDto> map(Collection<ProductProperty> productProperties) {
        return productProperties.stream().map(this::map).collect(Collectors.toList());
    }
}
