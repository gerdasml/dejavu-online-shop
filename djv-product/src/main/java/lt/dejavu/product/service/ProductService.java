package lt.dejavu.product.service;

import lt.dejavu.product.model.rest.request.CreateProductRequest;
import lt.dejavu.product.view.ProductView;

import java.util.List;

public interface ProductService {
    ProductView getProduct(long id);

    List<ProductView> getProductsByCategory(long categoryId);

    Long createProduct(CreateProductRequest request);

    void deleteProduct(long productId);

    void updateProduct(long productId, CreateProductRequest request);
}
