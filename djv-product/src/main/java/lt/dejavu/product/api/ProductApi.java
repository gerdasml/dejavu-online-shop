package lt.dejavu.product.api;

import lt.dejavu.auth.exception.ApiSecurityException;
import lt.dejavu.auth.service.SecurityService;
import lt.dejavu.product.dto.ProductDto;
import lt.dejavu.product.dto.ProductImportStatusDto;
import lt.dejavu.product.exception.UnsupportedFileTypeException;
import lt.dejavu.product.model.SearchResult;
import lt.dejavu.product.model.SortBy;
import lt.dejavu.product.model.SortDirection;
import lt.dejavu.product.model.rest.request.ProductSearchRequest;
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
    public List<ProductDto> getAllProducts(@RequestParam(value="offset", required=false) Integer offset,
                                           @RequestParam(value="limit", required=false) Integer limit,
                                           @RequestParam(value="sortBy", required=false, defaultValue="PRICE") SortBy sortBy,
                                           @RequestParam(value="sortDirection", required=false, defaultValue="ASC") SortDirection sortDirection) {
        return productService.getAllProducts(limit, offset, sortBy, sortDirection);
    }

    @GetMapping("/count")
    public long getTotalProductCount() {
        return productService.getTotalProductCount();
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
    public List<ProductDto> getProductsByCategory(@PathVariable("categoryId") long categoryId,
                                                  @RequestParam(value="offset", required=false) Integer offset,
                                                  @RequestParam(value="limit", required=false) Integer limit,
                                                  @RequestParam(value="sortBy", required=false, defaultValue="PRICE") SortBy sortBy,
                                                  @RequestParam(value="sortDirection", required=false, defaultValue="ASC") SortDirection sortDirection) {
        return productService.getProductsByCategory(categoryId, offset, limit, sortBy, sortDirection);
    }

    @GetMapping(
            path="/byIdentifier",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            params = "identifier"
    )
    public ProductDto getProductByIdentifier(@RequestParam String identifier) {
        return productService.getProduct(identifier);
    }

    @PostMapping(
            path = "/search",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public SearchResult<ProductDto> productSearch(@RequestBody ProductSearchRequest request,
                                                  @RequestParam(value="offset", required=false) Integer offset,
                                                  @RequestParam(value="limit", required=false) Integer limit,
                                                  @RequestParam(value="sortBy", required=false, defaultValue="PRICE") SortBy sortBy,
                                                  @RequestParam(value="sortDirection", required=false, defaultValue="ASC") SortDirection sortDirection) {
        return productService.searchProducts(request, offset, limit, sortBy, sortDirection);
    }

    @PostMapping(
            path = "/",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )

    public Long createProduct(HttpServletRequest request,
                              @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
                              @RequestBody ProductDto productRequest) throws ApiSecurityException {
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
                              @RequestBody ProductDto productRequest) throws ApiSecurityException {
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
        if (!file.getOriginalFilename().endsWith(".xlsx")) {
            throw new UnsupportedFileTypeException("Only the '.xlsx' file type is supported for import");
        }
        return productService.importProducts(file.getBytes());
    }

    @GetMapping(path = "/import/status/{jobId}")
    public ProductImportStatusDto getImportStatus(HttpServletRequest request,
                                                  @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
                                                  @PathVariable("jobId") UUID jobId) throws ApiSecurityException {
        securityService.authorize(authHeader, request);
        return statusService.getStatus(jobId);
    }


    @GetMapping(path = "/import/statistics/{jobId}")
    public ProductImportStatusDto getImportStats(HttpServletRequest request,
                                                 @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
                                                 @PathVariable("jobId") UUID jobId) throws ApiSecurityException {
        securityService.authorize(authHeader, request);
        return statusService.getStatistics(jobId);
    }

    @GetMapping(path = "/import/status/")
    public List<ProductImportStatusDto> getImportStatuses(HttpServletRequest request,
                                                          @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) throws ApiSecurityException {
        securityService.authorize(authHeader, request);
        return statusService.getAllStatuses();
    }

    @PutMapping(path = "/import/status/{id}")
    public ProductImportStatusDto updateImportStatus(HttpServletRequest request,
                                                     @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
                                                     @PathVariable("id") UUID jobId,
                                                     @RequestBody ProductImportStatusDto newStatus) throws ApiSecurityException {
        securityService.authorize(authHeader, request);
        return statusService.updateStatus(jobId, newStatus);
    }
}
