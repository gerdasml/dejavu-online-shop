package lt.dejavu.product.repository;

import lt.dejavu.product.model.Product;


import java.util.List;

public interface ProductRepository {

    Product getProduct(long id);

    List<Product> getProductsByCategory(int categoryId);

    Long saveProduct(Product product);
}
