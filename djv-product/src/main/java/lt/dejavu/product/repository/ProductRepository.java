package lt.dejavu.product.repository;

import lt.dejavu.product.model.*;
import lt.dejavu.product.model.rest.request.ProductSearchRequest;

import java.util.Set;

public interface ProductRepository {

    Product getProduct(long id);

    Product getProduct(String identifier);

    Set<Product> getAllProducts(int offset, int limit, SortBy sortBy, SortDirection sortDirection);

    Set<Product> getProductsByCategory(long categoryId, int offset, int limit, SortBy sortBy, SortDirection sortDirection);

    long saveProduct(Product product);

    void deleteProduct(Product product);

    void updateProduct(Product product);

    void reassignProductsToParent(Category oldCategory);

    long getTotalProductCount();

    SearchResult<Product> searchForProducts(ProductSearchRequest request, int offset, int limit, SortBy sortBy, SortDirection sortDirection);
}
