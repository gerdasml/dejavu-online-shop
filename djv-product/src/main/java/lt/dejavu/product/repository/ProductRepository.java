package lt.dejavu.product.repository;

import lt.dejavu.product.model.Product;


import java.util.List;

public interface ProductRepository {

    Product getProduct(long id);

    List<Product> getProductsByCategory(long categoryId);

    long saveProduct(Product product);

    void deleteProduct(Product product);

    void updateProduct(Product product);
}
