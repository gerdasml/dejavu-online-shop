package lt.dejavu.product.model.rest.mapper;

import lt.dejavu.product.model.Category;
import lt.dejavu.product.model.CategoryProperty;
import lt.dejavu.product.model.rest.request.CategoryPropertyRequest;
import lt.dejavu.product.model.rest.request.CategoryRequest;
import lt.dejavu.utils.collections.CommonCollectionUtils;
import lt.dejavu.utils.collections.UpdatableCollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import static java.util.stream.Collectors.toCollection;

@Component
public class CategoryRequestMapper {
    public Category mapToCategory(CategoryRequest categoryRequest, Category parentCategory) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setIconName(categoryRequest.getIcon());
        category.setIdentifier(categoryRequest.getIdentifier());
        category.setParentCategory(parentCategory);
        category.setProperties(mapToProperties(categoryRequest.getProperties(), category));
        return category;
    }

    private Set<CategoryProperty> mapToProperties(Collection<CategoryPropertyRequest> propertyRequests, Category category) {
        return propertyRequests.stream()
                .map(request -> {
                    CategoryProperty property = new CategoryProperty();
                    property.setId(request.getPropertyId());
                    property.setName(request.getName());
                    property.setCategory(category);
                    return property;
                }).collect(toCollection(LinkedHashSet::new));
    }

    public Category remapToCategory(Category oldCategory, CategoryRequest categoryRequest, Category parentCategory) {
        oldCategory.setName(categoryRequest.getName());
        oldCategory.setIconName(categoryRequest.getIcon());
        oldCategory.setIdentifier(categoryRequest.getIdentifier());
        oldCategory.setParentCategory(parentCategory);
        UpdatableCollectionUtils.updateCollection(oldCategory.getProperties(), mapToProperties(categoryRequest.getProperties(), oldCategory));
        return oldCategory;
    }
}
