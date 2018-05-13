package lt.dejavu.product.api;

import lt.dejavu.auth.exception.ApiSecurityException;
import lt.dejavu.auth.service.SecurityService;
import lt.dejavu.excel.service.ExcelService;
import lt.dejavu.product.dto.ProductDto;
import lt.dejavu.product.dto.ProductImportStatusDto;
import lt.dejavu.product.model.Product;
import lt.dejavu.product.model.rest.request.ProductRequest;
import lt.dejavu.product.service.ProductImportStatusService;
import lt.dejavu.product.service.ProductService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("${rest.product}")
public class ProductApi {

    @Autowired
    private ProductService productService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private ProductImportStatusService statusService;

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

    @GetMapping(path = "/export")
    public void export(HttpServletResponse response) throws IOException {
        // TODO: disable logging for this somehow
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=products.xlsx");
        ByteArrayOutputStream output = productService.exportProducts();
        IOUtils.write(output.toByteArray(), response.getOutputStream());
        response.flushBuffer();
    }

    @PostMapping(path = "/import")
    public UUID importProducts(HttpServletRequest request,
                               @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
                               @RequestParam("file") MultipartFile file) throws ApiSecurityException, IOException {
        securityService.authorize(authHeader, request);
        return productService.importProducts(file.getBytes());
    }

    @GetMapping(path = "/import/status/{jobId}")
    public ProductImportStatusDto getImportStatus(HttpServletRequest request,
                                                  @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
                                                  @PathVariable("jobId") UUID jobId) throws ApiSecurityException {
        securityService.authorize(authHeader, request);
        return statusService.getStatus(jobId);
    }
}
