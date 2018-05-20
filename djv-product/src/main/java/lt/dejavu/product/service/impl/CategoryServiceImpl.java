package lt.dejavu.product.service.impl;

import lt.dejavu.product.response.CategoryResponse;
import lt.dejavu.product.response.CategoryTreeResponse;
import lt.dejavu.product.response.mapper.CategoryResponseMapper;
import lt.dejavu.product.exception.CategoryNotFoundException;
import lt.dejavu.product.exception.IllegalRequestDataException;
import lt.dejavu.product.model.Category;
import lt.dejavu.product.model.rest.mapper.CategoryRequestMapper;
import lt.dejavu.product.model.rest.request.CategoryRequest;
import lt.dejavu.product.repository.CategoryRepository;
import lt.dejavu.product.repository.ProductPropertyRepository;
import lt.dejavu.product.service.CategoryService;
import lt.dejavu.product.strategy.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryRequestMapper categoryRequestMapper;
    private final CategoryResponseMapper categoryResponseMapper;
    private final ProductPropertyRepository productPropertyRepository;
    private final IdentifierGenerator<Category> categoryIdentifierGenerator;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryRequestMapper categoryRequestMapper, CategoryResponseMapper categoryResponseMapper, ProductPropertyRepository productPropertyRepository, IdentifierGenerator<Category> categoryIdentifierGenerator) {
        this.categoryRepository = categoryRepository;
        this.categoryRequestMapper = categoryRequestMapper;
        this.categoryResponseMapper = categoryResponseMapper;
        this.productPropertyRepository = productPropertyRepository;
        this.categoryIdentifierGenerator = categoryIdentifierGenerator;
    }

    @Override
    public CategoryResponse getCategory(long id) {
        return categoryResponseMapper.map(getCategoryIfExist(id));
    }

    @Override
    public CategoryResponse getCategoryByIdentifier(String identifier) {
        return categoryResponseMapper.map(getCategoryIfExist(identifier));
    }

    @Override
    public List<CategoryResponse> getRootCategories() {
        return categoryResponseMapper.map(categoryRepository.getRootCategories());
    }

    @Override
    public List<CategoryTreeResponse> getCategoryTree() {
        return categoryResponseMapper.mapToTree(categoryRepository.getAllCategories());
    }

    @Override
    public List<CategoryResponse> getSubCategories(long categoryId) {
        return categoryResponseMapper.map(categoryRepository.getSubCategories(categoryId));
    }

    @Transactional
    @Override
    public Long createCategory(CategoryRequest categoryRequest) {
        Category parentCategory = resolveParentCategory(categoryRequest);
        Category category = categoryRequestMapper.mapToCategory(categoryRequest, parentCategory);
        category.setIdentifier(categoryIdentifierGenerator.generateIdentifier(category));
        Long categoryId = categoryRepository.saveCategory(category);
        productPropertyRepository.saveProperties(category.getProperties());
        return categoryId;
    }

    @Transactional
    @Override
    public void updateCategory(long categoryId, CategoryRequest categoryRequest) {
        validateNotSelfParent(categoryId, categoryRequest.getParentCategoryId());
        Category oldCategory = getCategoryIfExist(categoryId);
        Category parentCategory = resolveParentCategory(categoryRequest);
        Category newCategory = categoryRequestMapper.remapToCategory(oldCategory, categoryRequest, parentCategory);
        newCategory.setIdentifier(categoryIdentifierGenerator.generateIdentifier(newCategory));
        categoryRepository.updateCategory(newCategory);
    }

    @Override
    public void deleteCategory(long categoryId) {
        Category category = getCategoryIfExist(categoryId);
        categoryRepository.deleteCategory(category);
    }

    private void validateNotSelfParent(long id, Long parentId) {
        if (parentId == null) {
            return;
        }
        if (parentId.equals(id)) {
            throw new IllegalRequestDataException("category cannot be self parent");
        }
    }

    private Category resolveParentCategory(CategoryRequest categoryRequest) {
        if (categoryRequest.getParentCategoryId() == null) {
            return null;
        }
        Category parentCategory = categoryRepository.getCategory(categoryRequest.getParentCategoryId());
        if (parentCategory == null) {
            throw new CategoryNotFoundException("cannot find category with parent id: " + categoryRequest.getParentCategoryId());
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
