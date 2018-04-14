package lt.dejavu.product.view.mapper;

import lt.dejavu.product.model.Category;
import lt.dejavu.product.view.CategoryView;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryViewMapper {

    public CategoryView map(Category category) {
        return map(category, false);
    }

    public CategoryView map(Category category, boolean fetched) {
        if (category == null) {
            return null;
        }
        CategoryView view = new CategoryView();
        view.setId(category.getId());
        view.setName(category.getName());
        view.setIconName(category.getIconName());
        view.setDisplayName(category.getDisplayName());
        if (category.getParentCategory() != null) {
            view.setParentCategoryId(category.getParentCategory().getId());
            if (fetched) {
                view.setParentCategory(map(category.getParentCategory(), true));
            }
        }
        return view;
    }

    public List<CategoryView>  map(List<Category> categories) {
        return map(categories, false);
    }

    public List<CategoryView> map(List<Category> categories, boolean fetched) {
        return categories.stream().map(c -> map(c, fetched)).collect(Collectors.toList());
    }
}
