package lt.dejavu.product.dto.mapper;

import lt.dejavu.product.dto.ProductPropertyDto;
import lt.dejavu.product.model.ProductPropertyValue;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductPropertiesDtoMapper {

    public ProductPropertyDto map(ProductPropertyValue productProperty) {
        if (productProperty == null) {
            return null;
        }
        ProductPropertyDto dto = new ProductPropertyDto();
        dto.setId(productProperty.getId());
        dto.setName(productProperty.getProductProperty().getName());
        dto.setValue(productProperty.getValue());
        return dto;
    }

    public List<ProductPropertyDto> map(Collection<ProductPropertyValue> productProperties) {
        return productProperties.stream().map(this::map).collect(Collectors.toList());
    }
}
