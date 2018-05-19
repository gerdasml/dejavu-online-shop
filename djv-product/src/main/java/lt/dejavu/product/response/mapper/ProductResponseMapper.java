package lt.dejavu.product.response.mapper;

import lt.dejavu.product.response.ProductDto;
import lt.dejavu.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProductResponseMapper {

    private final ProductPropertiesResponseMapper propertiesMapper;

    @Autowired
    public ProductResponseMapper(ProductPropertiesResponseMapper productPropertiesResponseMapper) {
        this.propertiesMapper = productPropertiesResponseMapper;
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
        dto.setProperties(propertiesMapper.map(product.getProperties()));
        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
        }
        return dto;
    }

    public List<ProductDto> map(Set<Product> products) {
        return products.stream().map(this::map).collect(Collectors.toList());
    }
}
