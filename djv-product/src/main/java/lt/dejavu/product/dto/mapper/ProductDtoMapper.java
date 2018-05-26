package lt.dejavu.product.dto.mapper;

import lt.dejavu.product.dto.ProductDto;
import lt.dejavu.product.model.Category;
import lt.dejavu.product.model.Product;

import lt.dejavu.utils.collections.CommonCollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductDtoMapper {

    private final ProductPropertyDtoMapper propertiesMapper;

    @Autowired
    public ProductDtoMapper(ProductPropertyDtoMapper productPropertyDtoMapper) {
        this.propertiesMapper = productPropertyDtoMapper;
    }

    public ProductDto mapToDto(Product product) {
        if (product == null) {
            return null;
        }
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setSkuCode(product.getSkuCode());
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

    public List<ProductDto> mapToDto(Collection<Product> products) {
        return products.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public Product mapToProduct(ProductDto productRequest, Category category) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setSkuCode(productRequest.getSkuCode());
        product.setIdentifier(productRequest.getIdentifier());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setMainImageUrl(productRequest.getMainImageUrl());
        product.setAdditionalImagesUrls(productRequest.getAdditionalImagesUrls());
        product.setCreationDate(productRequest.getCreationDate());
        product.setCategory(category);
        return product;
    }

    public Product remapToProduct(Product oldProduct, ProductDto productDto, Category category) {
        oldProduct.setName(productDto.getName());
        oldProduct.setSkuCode(productDto.getSkuCode());
        oldProduct.setIdentifier(productDto.getIdentifier());
        oldProduct.setDescription(productDto.getDescription());
        oldProduct.setPrice(productDto.getPrice());
        oldProduct.setMainImageUrl(productDto.getMainImageUrl());
        CommonCollectionUtils.updateCollection(oldProduct.getAdditionalImagesUrls(), productDto.getAdditionalImagesUrls());
        oldProduct.setCreationDate(productDto.getCreationDate());
        oldProduct.setCategory(category);
        return oldProduct;
    }
}
