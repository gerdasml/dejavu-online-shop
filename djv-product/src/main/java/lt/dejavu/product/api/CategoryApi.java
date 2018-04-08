package lt.dejavu.product.api;

import lt.dejavu.product.model.Category;
import lt.dejavu.product.model.rest.request.CreateCategoryRequest;
import lt.dejavu.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${rest.basePath}/category")
public class CategoryApi {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(
            path = "/{categoryId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public Category getCategory(@PathVariable("categoryId") long categoryId){
        return categoryService.getCategory(categoryId);
    }

    @RequestMapping(
            path = "/rootCategories",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<Category> getRootCategories(){
        return categoryService.getRootCategories();
    }


    @RequestMapping(
            path = "/sub/{categoryId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<Category> getSubCategories(@PathVariable("categoryId") long categoryId){
        return categoryService.getSubCategories(categoryId);
    }

    @RequestMapping(
            path = "/create",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public Long createCategory(@RequestBody CreateCategoryRequest categoryRequest){
        return categoryService.createCategory(categoryRequest);
    }
}
