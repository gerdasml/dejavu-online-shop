package lt.dejavu.product.api;

import lt.dejavu.product.model.Product;
import lt.dejavu.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public Product getProduct(@PathVariable("productId") long productId){
        return productService.getProduct(productId);
    }


}
