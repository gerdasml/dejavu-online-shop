package lt.dejavu.product.api;

import lt.dejavu.product.model.rest.request.CreateProductRequest;
import lt.dejavu.product.service.ProductService;
import lt.dejavu.product.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${rest.product}")
public class ProductApi {

    @Autowired
    private ProductService productService;

    @GetMapping(
            path = "/{productId}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ProductDto getProduct(@PathVariable("productId") long productId){
        return productService.getProduct(productId);
    }

    @GetMapping(
            path = "/category/{categoryId}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<ProductDto> getProductsByCategory(@PathVariable("categoryId") long categoryId){
        return productService.getProductsByCategory(categoryId);
    }

    @PostMapping(
            path = "/",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public Long createProduct(@RequestBody CreateProductRequest categoryRequest){
        return productService.createProduct(categoryRequest);
    }


}
