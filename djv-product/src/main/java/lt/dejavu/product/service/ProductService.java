package lt.dejavu.product.service;

import lt.dejavu.product.dto.ProductDto;
import lt.dejavu.product.model.rest.request.CreateProductRequest;

import java.util.List;

public interface ProductService {
    ProductDto getProduct(long id);

    List<ProductDto> getProductsByCategory(long categoryId);

    Long createProduct(CreateProductRequest request);
}
