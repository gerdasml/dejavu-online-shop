package lt.dejavu.product.dto.mapper;

import lt.dejavu.product.dto.CategoryDto;
import lt.dejavu.product.dto.CategoryPropertyDto;
import lt.dejavu.product.dto.CategoryTreeResponse;
import lt.dejavu.product.model.Category;
import lt.dejavu.product.model.CategoryProperty;
import lt.dejavu.utils.collections.UpdatableCollectionUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

@Component
public class CategoryDtoMapper {

    public CategoryDto map(Category category) {
        if (category == null) {
            return null;
        }
        CategoryDto response = new CategoryDto();
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

    public List<CategoryDto> map(Set<Category> categories) {
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

    private CategoryPropertyDto map(CategoryProperty categoryProperty) {
        if (categoryProperty == null) {
            return null;
        }
        CategoryPropertyDto dto = new CategoryPropertyDto();
        dto.setPropertyId(categoryProperty.getId());
        dto.setName(categoryProperty.getName());
        return dto;
    }

    public List<CategoryPropertyDto> map(Collection<CategoryProperty> productProperties) {
        return productProperties.stream().map(this::map).collect(toList());
    }

    public Category mapToCategory(CategoryDto categoryDto, Category parentCategory) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setIconName(categoryDto.getIcon());
        category.setIdentifier(categoryDto.getIdentifier());
        category.setParentCategory(parentCategory);
        category.setProperties(mapToProperties(categoryDto.getProperties(), category));
        return category;
    }

    private Set<CategoryProperty> mapToProperties(Collection<CategoryPropertyDto> categoryPropertyDtos, Category category) {
        if (CollectionUtils.isEmpty(categoryPropertyDtos)) {
            return new LinkedHashSet<>();
        }
        return categoryPropertyDtos.stream()
                .map(dto -> {
                    CategoryProperty property = new CategoryProperty();
                    property.setId(dto.getPropertyId());
                    property.setName(dto.getName());
                    property.setCategory(category);
                    return property;
                }).collect(toCollection(LinkedHashSet::new));
    }

    public Category remapToCategory(Category oldCategory, CategoryDto categoryDto, Category parentCategory) {
        oldCategory.setName(categoryDto.getName());
        oldCategory.setIconName(categoryDto.getIcon());
        oldCategory.setIdentifier(categoryDto.getIdentifier());
        oldCategory.setParentCategory(parentCategory);
        UpdatableCollectionUtils.updateCollection(oldCategory.getProperties(), mapToProperties(categoryDto.getProperties(), oldCategory));
        return oldCategory;
    }
}
