package lt.dejavu.product.model.rest.mapper;

import lt.dejavu.product.model.Category;
import lt.dejavu.product.model.Product;
import lt.dejavu.product.model.rest.request.ProductRequest;
import org.springframework.stereotype.Component;

@Component
public class ProductRequestMapper {

    public Product mapToProduct(ProductRequest productRequest, Category category){
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setDescription(productRequest.getDescription());
        product.setCreationDate(productRequest.getCreationDate());
        product.setCategory(category);
        return product;
    }

}
