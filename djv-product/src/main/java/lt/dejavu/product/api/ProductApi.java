package lt.dejavu.product.api;

import lt.dejavu.product.model.rest.request.ProductRequest;
import lt.dejavu.product.service.ProductService;
import lt.dejavu.product.view.ProductView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${rest.basePath}/product")
public class ProductApi {

    @Autowired
    private ProductService productService;

    @RequestMapping(
            path = "/{productId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ProductView getProduct(@PathVariable("productId") long productId){
        return productService.getProduct(productId);
    }

    @RequestMapping(
            path = "/category/{categoryId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<ProductView> getProductsByCategory(@PathVariable("categoryId") long categoryId){
        return productService.getProductsByCategory(categoryId);
    }

    @RequestMapping(
            path = "/",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public Long createProduct(@RequestBody ProductRequest productRequest){
        return productService.createProduct(productRequest);
    }

    @RequestMapping(
            path = "/{productId}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void updateProduct(@PathVariable("productId") long productId, @RequestBody ProductRequest productRequest){
        productService.updateProduct(productId, productRequest);
    }

    @RequestMapping(
            path = "/{productId}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void deleteProduct(@PathVariable("productId") long productId){
        productService.deleteProduct(productId);
    }

}
