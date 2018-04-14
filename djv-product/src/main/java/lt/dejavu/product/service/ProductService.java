package lt.dejavu.product.service;

import lt.dejavu.product.model.Product;
import lt.dejavu.product.model.rest.request.CreateProductRequest;

import java.util.List;

public interface ProductService {
    Product getProduct(long id);

    List<Product> getProductsByCategory(long categoryId);

    Long createProduct(CreateProductRequest request);
}
