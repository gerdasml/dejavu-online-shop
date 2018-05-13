package lt.dejavu.product.api;

import lt.dejavu.auth.exception.ApiSecurityException;
import lt.dejavu.auth.service.SecurityService;
import lt.dejavu.excel.service.ExcelService;
import lt.dejavu.product.dto.ProductDto;
import lt.dejavu.product.model.rest.request.ProductRequest;
import lt.dejavu.product.service.ProductService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("${rest.product}")
public class ProductApi {

    @Autowired
    private ProductService productService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private ExcelService<ProductDto> excelService;

    @GetMapping(
            path = "/",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping(
            path = "/{productId}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ProductDto getProduct(@PathVariable("productId") long productId) {
        return productService.getProduct(productId);
    }

    @GetMapping(
            path = "/category/{categoryId}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<ProductDto> getProductsByCategory(@PathVariable("categoryId") long categoryId) {
        return productService.getProductsByCategory(categoryId);
    }

    @PostMapping(
            path = "/",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )

    public Long createProduct(HttpServletRequest request,
                              @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
                              @RequestBody ProductRequest productRequest) throws ApiSecurityException {
        securityService.authorize(authHeader, request);
        return productService.createProduct(productRequest);
    }

    @PutMapping(
            path = "/{productId}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void updateProduct(HttpServletRequest request,
                              @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
                              @PathVariable("productId") long productId,
                              @RequestBody ProductRequest productRequest) throws ApiSecurityException {
        securityService.authorize(authHeader, request);
        productService.updateProduct(productId, productRequest);
    }

    @DeleteMapping(
            path = "/{productId}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void deleteProduct(HttpServletRequest request,
                              @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
                              @PathVariable("productId") long productId) throws ApiSecurityException {
        securityService.authorize(authHeader, request);
        productService.deleteProduct(productId);
    }

    @GetMapping(path = "export")
    public void export(HttpServletResponse response) throws IOException {
        // TODO: disable logging for this somehow
//        File f = new File("C:\\Users\\vstri\\Desktop\\uni\\psk\\djv-wiki\\documents\\2018_05_11_pavyzdinis_excel_dokumentas.xlsx");
//        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=products.xlsx");
//        IOUtils.copy(new FileInputStream(f), response.getOutputStream());
//        response.flushBuffer();
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=products.xlsx");
        ByteArrayOutputStream output = excelService.toExcel(getAllProducts());
        IOUtils.write(output.toByteArray(), response.getOutputStream());
        response.flushBuffer();
    }
}
