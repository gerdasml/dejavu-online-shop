package lt.dejavu.product.service;

import lt.dejavu.product.dto.ProductDto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductDto getProduct(long id);

    List<ProductDto> getAllProducts();

    List<ProductDto> getProductsByCategory(long categoryId);

    Long createProduct(ProductDto request);

    void deleteProduct(long productId);

    void updateProduct(long productId, ProductDto request);

    ByteArrayOutputStream exportProducts() throws IOException;

    UUID importProducts(byte[] data) throws IOException;
}
