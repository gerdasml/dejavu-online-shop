package lt.dejavu.product.repository;

import lt.dejavu.product.model.Product;

import java.util.List;
import java.util.Set;

public interface ProductRepository {

    Product getProduct(long id);

    Product getProduct(String identifier);

    Set<Product> getAllProducts(int offset, int limit);

    Set<Product> getProductsByCategory(long categoryId, int offset, int limit);

    long saveProduct(Product product);

    void deleteProduct(Product product);

    void updateProduct(Product product);

    long getTotalProductCount();
}
