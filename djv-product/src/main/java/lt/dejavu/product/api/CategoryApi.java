package lt.dejavu.product.api;

import lt.dejavu.product.model.rest.request.CategoryRequest;
import lt.dejavu.product.service.CategoryService;
import lt.dejavu.product.view.CategoryView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public CategoryView getCategory(@PathVariable("categoryId") long categoryId){
        return categoryService.getCategory(categoryId);
    }

    @RequestMapping(
            path = "/rootCategories",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<CategoryView> getRootCategories(){
        return categoryService.getRootCategories();
    }


    @RequestMapping(
            path = "/sub/{categoryId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<CategoryView> getSubCategories(@PathVariable("categoryId") long categoryId){
        return categoryService.getSubCategories(categoryId);
    }

    @RequestMapping(
            path = "/create",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public Long createCategory(@RequestBody CategoryRequest categoryRequest){
        return categoryService.createCategory(categoryRequest);
    }
}
