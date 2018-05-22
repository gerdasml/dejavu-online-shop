package lt.dejavu.product.service;

import lt.dejavu.product.dto.ProductDto;
import lt.dejavu.product.model.rest.request.ProductSearchRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductDto getProduct(long id);

    List<ProductDto> getAllProducts(Long limit, Long offset);

    ProductDto getProduct(String identifier);

    List<ProductDto> getProductsByCategory(long categoryId);

    Long createProduct(ProductDto request);
    
    List<ProductDto> searchProducts(ProductSearchRequest request);

    void deleteProduct(long productId);

    void updateProduct(long productId, ProductDto request);

    ByteArrayOutputStream exportProducts() throws IOException;

    UUID importProducts(byte[] data) throws IOException;
}
