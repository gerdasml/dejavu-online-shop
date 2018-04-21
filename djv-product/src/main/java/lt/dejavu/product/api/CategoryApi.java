package lt.dejavu.product.api;

import lt.dejavu.product.dto.CategoryDto;
import lt.dejavu.product.dto.CategoryTreeDto;
import lt.dejavu.product.model.rest.request.CategoryRequest;
import lt.dejavu.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            path = "/categoryTree",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<CategoryTreeDto> getCategoryTree() {
        return categoryService.getCategoryTree();
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

    public Long createCategory(@RequestBody CategoryRequest categoryRequest){
        return categoryService.createCategory(categoryRequest);
    }

    @PutMapping(
            path = "/{categoryId}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void updateCategory(@PathVariable("categoryId") long categoryId, @RequestBody CategoryRequest categoryRequest){
        categoryService.updateCategory(categoryId, categoryRequest);
    }

    @DeleteMapping(
            path = "/{categoryId}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void deleteCategory(@PathVariable("categoryId") long categoryId){
        categoryService.deleteCategory(categoryId);
    }
}
