package lt.dejavu.product.service;

import lt.dejavu.product.model.rest.request.ProductRequest;
import lt.dejavu.product.view.ProductView;

import java.util.List;

public interface ProductService {
    ProductView getProduct(long id);

    List<ProductView> getProductsByCategory(long categoryId);

    Long createProduct(ProductRequest request);

    void deleteProduct(long productId);

    void updateProduct(long productId, ProductRequest request);
}
