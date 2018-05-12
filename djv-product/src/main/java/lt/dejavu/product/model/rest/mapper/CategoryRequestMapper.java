package lt.dejavu.product.model.rest.mapper;

import lt.dejavu.product.model.Category;
import lt.dejavu.product.model.ProductProperty;
import lt.dejavu.product.model.rest.request.CategoryRequest;
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
        category.setProperties(mapToProperties(categoryRequest.getProperties(), category ));
        return category;
    }

    public Set<ProductProperty> mapToProperties(Collection<String> propertyNames, Category category) {
        return propertyNames.stream()
                .map(name -> {
                    ProductProperty property = new ProductProperty();
                    property.setName(name);
                    property.setCategory(category);
                    return property;
                }).collect(toCollection(LinkedHashSet::new));
    }
}
