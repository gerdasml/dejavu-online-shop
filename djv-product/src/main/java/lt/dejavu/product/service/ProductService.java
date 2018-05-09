package lt.dejavu.product.service;

import lt.dejavu.product.dto.ProductDto;
import lt.dejavu.product.model.rest.request.ProductRequest;

import java.util.List;

public interface ProductService {
    ProductDto getProduct(long id);

    List<ProductDto> getAllProducts();

    List<ProductDto> getProductsByCategory(long categoryId);

    Long createProduct(ProductRequest request);

    void deleteProduct(long productId);

    void updateProduct(long productId, ProductRequest request);
}
