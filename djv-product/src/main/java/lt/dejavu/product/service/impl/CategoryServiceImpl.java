package lt.dejavu.product.service.impl;

import javafx.util.Pair;
import lt.dejavu.product.dto.CategoryDto;
import lt.dejavu.product.dto.CategoryInfoDto;
import lt.dejavu.product.dto.CategoryTreeResponse;
import lt.dejavu.product.dto.PropertySummaryDto;
import lt.dejavu.product.dto.mapper.CategoryDtoMapper;
import lt.dejavu.product.exception.CategoryNotFoundException;
import lt.dejavu.product.exception.IllegalRequestDataException;
import lt.dejavu.product.model.Category;
import lt.dejavu.product.model.ProductProperty;
import lt.dejavu.product.repository.CategoryRepository;
import lt.dejavu.product.repository.ProductPropertyRepository;
import lt.dejavu.product.repository.ProductRepository;
import lt.dejavu.product.service.CategoryService;
import lt.dejavu.product.strategy.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.*;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CategoryDtoMapper categoryDtoMapper;
    private final ProductPropertyRepository productPropertyRepository;
    private final IdentifierGenerator<Category> categoryIdentifierGenerator;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository,
                               CategoryDtoMapper categoryDtoMapper, ProductPropertyRepository productPropertyRepository,
                               IdentifierGenerator<Category> categoryIdentifierGenerator) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.categoryDtoMapper = categoryDtoMapper;
        this.productPropertyRepository = productPropertyRepository;
        this.categoryIdentifierGenerator = categoryIdentifierGenerator;
    }

    @Override
    public CategoryDto getCategory(long id) {
        return categoryDtoMapper.map(getCategoryIfExist(id));
    }

    @Override
    public List<CategoryDto> getRootCategories() {
        return categoryDtoMapper.map(categoryRepository.getRootCategories());
    }

    @Override
    public CategoryDto getCategoryByIdentifier(String identifier) {
        return categoryDtoMapper.map(getCategoryIfExist(identifier));
    }

    @Override
    public List<CategoryTreeResponse> getCategoryTree() {
        return categoryDtoMapper.mapToTree(categoryRepository.getAllCategories());
    }

    @Override
    public List<CategoryDto> getSubCategories(long categoryId) {
        return categoryDtoMapper.map(categoryRepository.getSubCategories(categoryId));
    }

    @Transactional
    @Override
    public Long createCategory(CategoryDto categoryDto) {
        Category parentCategory = resolveParentCategory(categoryDto);
        Category category = categoryDtoMapper.mapToCategory(categoryDto, parentCategory);
        category.setIdentifier(categoryIdentifierGenerator.generateIdentifier(category));
        Long categoryId = categoryRepository.saveCategory(category);
        productPropertyRepository.saveProperties(category.getProperties());
        return categoryId;
    }

    @Transactional
    @Override
    public void updateCategory(long categoryId, CategoryDto categoryDto) {
        validateNotSelfParent(categoryId, categoryDto.getParentCategoryId());
        Category oldCategory = getCategoryIfExist(categoryId);
        Category parentCategory = resolveParentCategory(categoryDto);
        Category newCategory = categoryDtoMapper.remapToCategory(oldCategory, categoryDto, parentCategory);
        newCategory.setIdentifier(categoryIdentifierGenerator.generateIdentifier(newCategory));
        categoryRepository.updateCategory(newCategory);
    }

    @Override
    public void deleteCategory(long categoryId) {
        Category category = getCategoryIfExist(categoryId);
        productRepository.reassignProductsToParent(category);
        categoryRepository.reassignCategoriesToParent(category);
        categoryRepository.deleteCategory(category);
    }

    public CategoryInfoDto getCategoryInfo(long categoryId) {
        getCategoryIfExist(categoryId);
        List<PropertySummaryDto> summaries = getPropertySummaries(categoryId);
        Long productCount = categoryRepository.getProductCount(categoryId);
        BigDecimal minPrice = categoryRepository.getMinimumProductPrice(categoryId);
        BigDecimal maxPrice = categoryRepository.getMaximumProductPrice(categoryId);

        CategoryInfoDto info = new CategoryInfoDto();
        info.setAvailableProperties(summaries);
        info.setProductCount(productCount);
        info.setMaxPrice(maxPrice);
        info.setMinPrice(minPrice);

        return info;
    }

    private List<PropertySummaryDto> getPropertySummaries(long categoryId) {
        List<ProductProperty> properties = categoryRepository.getProductPropertiesForCategory(categoryId);
        Map<Pair<Long, String>, Set<String>> mapping = properties.stream().collect(
                groupingBy(
                        prop -> new Pair<>(prop.getCategoryProperty().getId(), prop.getCategoryProperty().getName()),
                        mapping(ProductProperty::getValue, toSet())
                          ));
        return mapping.entrySet()
                      .stream()
                      .map(x -> buildPropertySummaryDto(x.getKey().getKey(), x.getKey().getValue(), new ArrayList<>(x.getValue())))
                      .collect(toList());
    }

    private PropertySummaryDto buildPropertySummaryDto(Long id, String name, List<String> values) {
        PropertySummaryDto dto = new PropertySummaryDto();
        dto.setPropertyId(id);
        dto.setPropertyName(name);
        dto.setValues(values);

        return dto;
    }

    private void validateNotSelfParent(long id, Long parentId) {
        if (parentId == null) {
            return;
        }
        if (parentId.equals(id)) {
            throw new IllegalRequestDataException("category cannot be self parent");
        }
    }

    private Category resolveParentCategory(CategoryDto categoryDto) {
        if (categoryDto.getParentCategoryId() == null) {
            return null;
        }
        Category parentCategory = categoryRepository.getCategory(categoryDto.getParentCategoryId());
        if (parentCategory == null) {
            throw new CategoryNotFoundException("cannot find category with parent id: " + categoryDto.getParentCategoryId());
        }
        return parentCategory;
    }

    private Category getCategoryIfExist(long categoryId) {
        Category category = categoryRepository.getCategory(categoryId);
        if (category == null) {
            throw new CategoryNotFoundException(categoryId);
        }
        return category;
    }

    private Category getCategoryIfExist(String identifier) {
        Category category = categoryRepository.getCategory(identifier);
        if (category == null) {
            throw new CategoryNotFoundException("The category with the specified identifier was not found");
        }
        return category;
    }
}
