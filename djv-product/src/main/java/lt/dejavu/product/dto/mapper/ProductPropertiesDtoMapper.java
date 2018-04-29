package lt.dejavu.product.dto.mapper;

import lt.dejavu.product.dto.ProductPropertyDto;
import lt.dejavu.product.model.ProductProperty;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductPropertiesDtoMapper {

    public ProductPropertyDto map(ProductProperty product) {
        if (product == null) {
            return null;
        }
        ProductPropertyDto dto = new ProductPropertyDto();
        dto.setName(product.getName());
        dto.setValue(product.getValue());
        return dto;
    }

    public List<ProductPropertyDto> map(Collection<ProductProperty> products) {
        return products.stream().map(this::map).collect(Collectors.toList());
    }
}
