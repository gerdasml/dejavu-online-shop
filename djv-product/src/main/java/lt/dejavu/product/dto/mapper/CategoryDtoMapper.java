package lt.dejavu.product.dto.mapper;

import lt.dejavu.product.model.Category;
import lt.dejavu.product.dto.CategoryDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryDtoMapper {


    public CategoryDto map(Category category) {
        if (category == null) {
            return null;
        }
        CategoryDto view = new CategoryDto();
        view.setId(category.getId());
        view.setName(category.getName());
        view.setIconName(category.getIconName());
        view.setDisplayName(category.getDisplayName());
        if (category.getParentCategory() != null) {
            view.setParentCategoryId(category.getParentCategory().getId());
        }
        return view;
    }

    public List<CategoryDto> map(List<Category> categories) {
        return categories.stream().map(this::map).collect(Collectors.toList());
    }
}
