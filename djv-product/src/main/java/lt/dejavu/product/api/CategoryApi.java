package lt.dejavu.product.api;

import lt.dejavu.auth.exception.ApiSecurityException;
import lt.dejavu.auth.service.SecurityService;
import lt.dejavu.product.response.CategoryResponse;
import lt.dejavu.product.response.CategoryTreeResponse;
import lt.dejavu.product.model.rest.request.CategoryRequest;
import lt.dejavu.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("${rest.category}")
public class CategoryApi {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SecurityService securityService;

    @GetMapping(
            path = "/{categoryId}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public CategoryResponse getCategory(@PathVariable("categoryId") long categoryId) {
        return categoryService.getCategory(categoryId);
    }

    @GetMapping(
            path = "/rootCategories",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<CategoryResponse> getRootCategories() {
        return categoryService.getRootCategories();
    }

    @GetMapping(
            path = "/categoryTree",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<CategoryTreeResponse> getCategoryTree() {
        return categoryService.getCategoryTree();
    }

    @GetMapping(
            path = "/sub/{categoryId}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<CategoryResponse> getSubCategories(@PathVariable("categoryId") long categoryId) {
        return categoryService.getSubCategories(categoryId);
    }

    @PostMapping(
            path = "/create",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )

    public Long createCategory(HttpServletRequest request,
                               @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
                               @RequestBody CategoryRequest categoryRequest) throws ApiSecurityException {
        securityService.authorize(authHeader, request);
        return categoryService.createCategory(categoryRequest);
    }

    @PutMapping(
            path = "/{categoryId}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void updateCategory(HttpServletRequest request,
                               @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
                               @PathVariable("categoryId") long categoryId,
                               @RequestBody CategoryRequest categoryRequest) throws ApiSecurityException {
        securityService.authorize(authHeader, request);
        categoryService.updateCategory(categoryId, categoryRequest);
    }

    @DeleteMapping(
            path = "/{categoryId}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void deleteCategory(HttpServletRequest request,
                               @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
                               @PathVariable("categoryId") long categoryId) throws ApiSecurityException {
        securityService.authorize(authHeader, request);
        categoryService.deleteCategory(categoryId);
    }
}
