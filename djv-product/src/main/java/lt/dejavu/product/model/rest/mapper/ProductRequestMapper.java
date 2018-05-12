package lt.dejavu.product.model.rest.mapper;

import lt.dejavu.product.model.Category;
import lt.dejavu.product.model.Product;
import lt.dejavu.product.model.rest.request.ProductRequest;
import org.springframework.stereotype.Component;

@Component
public class ProductRequestMapper {

    public Product mapToProduct(ProductRequest productRequest, Category category) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setIdentifier(productRequest.getIdentifier());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setMainImageUrl(productRequest.getMainImageUrl());
        product.setAdditionalImagesUrls(productRequest.getAdditionalImagesUrls());
        product.setCreationDate(productRequest.getCreationDate());
        product.setCategory(category);
        return product;
    }
}
