package lt.dejavu.product.service;

import lt.dejavu.product.response.ProductResponse;
import lt.dejavu.product.model.rest.request.ProductRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductResponse getProduct(long id);

    List<ProductResponse> getAllProducts();

    List<ProductResponse> getProductsByCategory(long categoryId);

    Long createProduct(ProductRequest request);

    void deleteProduct(long productId);

    void updateProduct(long productId, ProductRequest request);

    ByteArrayOutputStream exportProducts() throws IOException;

    UUID importProducts(byte[] data) throws IOException;
}
