package lt.dejavu.product.model.rest.mapper;

import lt.dejavu.product.model.Category;
import lt.dejavu.product.model.Product;
import lt.dejavu.product.model.rest.request.ProductRequest;
import lt.dejavu.utils.collections.CommonCollectionUtils;
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

    public Product remapToProduct(Product oldProduct, ProductRequest productRequest, Category category) {
        oldProduct.setName(productRequest.getName());
        oldProduct.setIdentifier(productRequest.getIdentifier());
        oldProduct.setDescription(productRequest.getDescription());
        oldProduct.setPrice(productRequest.getPrice());
        oldProduct.setMainImageUrl(productRequest.getMainImageUrl());
        CommonCollectionUtils.updateCollection(oldProduct.getAdditionalImagesUrls(), productRequest.getAdditionalImagesUrls());
        oldProduct.setCreationDate(productRequest.getCreationDate());
        oldProduct.setCategory(category);
        return oldProduct;
    }
}
