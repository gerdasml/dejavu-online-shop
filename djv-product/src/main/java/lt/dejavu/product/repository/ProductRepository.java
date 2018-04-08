package lt.dejavu.product.repository;

import lt.dejavu.product.model.Product;


import java.util.List;

public interface ProductRepository {

    Product getProduct(long id);

    List<Product> getProductsByCategory(long categoryId);

    Long saveProduct(Product product);
}