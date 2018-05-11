package lt.dejavu.product.dto.mapper;

import lt.dejavu.product.dto.CategoryDto;
import lt.dejavu.product.dto.CategoryTreeDto;
import lt.dejavu.product.model.Category;
import lt.dejavu.product.model.ProductProperty;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Component
public class CategoryDtoMapper {

    public CategoryDto map(Category category) {
        if (category == null) {
            return null;
        }
        CategoryDto view = new CategoryDto();
        view.setId(category.getId());
        view.setName(category.getName());
        view.setIcon(category.getIconName());
        view.setIdentifier(category.getIdentifier());
        if (category.getParentCategory() != null) {
            view.setParentCategoryId(category.getParentCategory().getId());
        }
        view.setProperties(category.getProperties().stream().map(ProductProperty::getName).collect(toList()));
        return view;
    }

    public List<CategoryDto> map(List<Category> categories) {
        return categories.stream().map(this::map).collect(toList());
    }

    public List<CategoryTreeDto> mapToTree(List<Category> categories) {
        if (categories == null || categories.isEmpty()) {
            return Collections.emptyList();
        }
        return categories.stream()
                         .filter(category -> category.getParentCategory() == null)
                         .map(rootCategory -> mapToTree(rootCategory, categories)).collect(toList());

    }

    private CategoryTreeDto mapToTree(Category category, List<Category> allCategories) {
        CategoryTreeDto categoryTree = new CategoryTreeDto();
        categoryTree.setCategory(map(category));
        categoryTree.setChildren(mapToTree(category.getId(), allCategories));
        return categoryTree;
    }

    private List<CategoryTreeDto> mapToTree(Long parentCategoryId, List<Category> allCategories) {
        return allCategories.stream()
                            .filter(category -> category.getParentCategory() != null && parentCategoryId.equals(category.getParentCategory().getId()))
                            .map(childCategory -> mapToTree(childCategory, allCategories)).collect(toList());
    }
}
