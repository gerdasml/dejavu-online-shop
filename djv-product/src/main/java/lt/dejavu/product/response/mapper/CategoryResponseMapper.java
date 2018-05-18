package lt.dejavu.product.response.mapper;

import lt.dejavu.product.response.CategoryResponse;
import lt.dejavu.product.response.CategoryPropertyResponse;
import lt.dejavu.product.response.CategoryTreeResponse;
import lt.dejavu.product.model.Category;
import lt.dejavu.product.model.CategoryProperty;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Component
public class CategoryResponseMapper {

    public CategoryResponse map(Category category) {
        if (category == null) {
            return null;
        }
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setIcon(category.getIconName());
        response.setIdentifier(category.getIdentifier());
        if (category.getParentCategory() != null) {
            response.setParentCategoryId(category.getParentCategory().getId());
        }
        response.setProperties(map(category.getProperties()));
        return response;
    }

    public List<CategoryResponse> map(Set<Category> categories) {
        return categories.stream().map(this::map).collect(toList());
    }

    public List<CategoryTreeResponse> mapToTree(Set<Category> categories) {
        if (categories == null || categories.isEmpty()) {
            return Collections.emptyList();
        }
        return categories.stream()
                .filter(category -> category.getParentCategory() == null)
                .map(rootCategory -> mapToTree(rootCategory, categories)).collect(toList());

    }

    private CategoryTreeResponse mapToTree(Category category, Set<Category> allCategories) {
        CategoryTreeResponse categoryTree = new CategoryTreeResponse();
        categoryTree.setCategory(map(category));
        categoryTree.setChildren(mapToTree(category.getId(), allCategories));
        return categoryTree;
    }

    private List<CategoryTreeResponse> mapToTree(Long parentCategoryId, Set<Category> allCategories) {
        return allCategories.stream()
                .filter(category -> category.getParentCategory() != null && parentCategoryId.equals(category.getParentCategory().getId()))
                .map(childCategory -> mapToTree(childCategory, allCategories)).collect(toList());
    }

    private CategoryPropertyResponse map(CategoryProperty categoryProperty) {
        if (categoryProperty == null) {
            return null;
        }
        CategoryPropertyResponse dto = new CategoryPropertyResponse();
        dto.setPropertyId(categoryProperty.getId());
        dto.setName(categoryProperty.getName());
        return dto;
    }

    private List<CategoryPropertyResponse> map(Collection<CategoryProperty> productProperties) {
        return productProperties.stream().map(this::map).collect(toList());
    }
}
