package lt.dejavu.product.service;

import lt.dejavu.product.dto.ProductDto;
import lt.dejavu.product.model.SearchResult;
import lt.dejavu.product.model.SortBy;
import lt.dejavu.product.model.SortDirection;
import lt.dejavu.product.model.rest.request.ProductSearchRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductDto getProduct(long id);

    List<ProductDto> getAllProducts(Integer limit, Integer offset, SortBy sortBy, SortDirection sortDirection);

    ProductDto getProduct(String identifier);

    List<ProductDto> getProductsByCategory(long categoryId, Integer offset, Integer limit, SortBy sortBy, SortDirection sortDirection);

    Long createProduct(ProductDto request);
    
    SearchResult<ProductDto> searchProducts(ProductSearchRequest request, Integer offset, Integer limit, SortBy sortBy, SortDirection sortDirection);

    void deleteProduct(long productId);

    void updateProduct(long productId, ProductDto request);

    ByteArrayOutputStream exportProducts() throws IOException;

    UUID importProducts(byte[] data) throws IOException;

    long getTotalProductCount();
}
