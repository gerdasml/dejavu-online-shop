package lt.dejavu.product.api;

import lt.dejavu.product.dto.CategoryDto;
import lt.dejavu.product.model.rest.request.CreateCategoryRequest;
import lt.dejavu.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${rest.category}")
public class CategoryApi {

    @Autowired
    private CategoryService categoryService;

    @GetMapping(
            path = "/{categoryId}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public CategoryDto getCategory(@PathVariable("categoryId") long categoryId) {
        return categoryService.getCategory(categoryId);
    }

    @GetMapping(
            path = "/rootCategories",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<CategoryDto> getRootCategories() {
        return categoryService.getRootCategories();
    }


    @GetMapping(
            path = "/sub/{categoryId}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<CategoryDto> getSubCategories(@PathVariable("categoryId") long categoryId) {
        return categoryService.getSubCategories(categoryId);
    }

    @PostMapping(
            path = "/create",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public Long createCategory(@RequestBody CreateCategoryRequest categoryRequest) {
        return categoryService.createCategory(categoryRequest);
    }
}
