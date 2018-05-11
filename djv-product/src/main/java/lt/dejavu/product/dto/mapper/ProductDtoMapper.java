package lt.dejavu.product.dto.mapper;

import lt.dejavu.product.dto.ProductDto;
import lt.dejavu.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductDtoMapper {

    private final ProductPropertiesDtoMapper propertiesMapper;

    @Autowired
    public ProductDtoMapper(ProductPropertiesDtoMapper productPropertiesDtoMapper) {
        this.propertiesMapper = productPropertiesDtoMapper;
    }

    public ProductDto map(Product product) {
        if (product == null) {
            return null;
        }
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setCreationDate(product.getCreationDate());
        dto.setIdentifier(product.getIdentifier());
        dto.setMainImageUrl(product.getMainImageUrl());
        dto.setAdditionalImagesUrls(product.getAdditionalImagesUrls());
        dto.setPrice(product.getPrice());
        dto.setProperties(propertiesMapper.map(product.getPropertyValues()));
        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
        }
        return dto;
    }

    public List<ProductDto> map(List<Product> products) {
        return products.stream().map(this::map).collect(Collectors.toList());
    }
}
