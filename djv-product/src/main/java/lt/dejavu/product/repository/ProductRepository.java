package lt.dejavu.product.repository;

import lt.dejavu.product.model.Category;
import lt.dejavu.product.model.Product;
import lt.dejavu.product.model.SearchResult;
import lt.dejavu.product.model.rest.request.ProductSearchRequest;

import java.util.Set;

public interface ProductRepository {

    Product getProduct(long id);

    Product getProduct(String identifier);

    Set<Product> getAllProducts(int offset, int limit);

    Set<Product> getProductsByCategory(long categoryId, int offset, int limit);

    long saveProduct(Product product);

    void deleteProduct(Product product);

    void updateProduct(Product product);

    void reassignProductsToParent(Category oldCategory);

    long getTotalProductCount();

    SearchResult<Product> searchForProducts(ProductSearchRequest request, int offset, int limit);
}
