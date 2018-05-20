package lt.dejavu.product.service.impl;

import lt.dejavu.product.dto.CategoryDto;
import lt.dejavu.product.dto.CategoryTreeResponse;
import lt.dejavu.product.dto.mapper.CategoryDtoMapper;
import lt.dejavu.product.exception.CategoryNotFoundException;
import lt.dejavu.product.exception.IllegalRequestDataException;
import lt.dejavu.product.model.Category;
import lt.dejavu.product.repository.CategoryRepository;
import lt.dejavu.product.repository.ProductPropertyRepository;
import lt.dejavu.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryDtoMapper categoryDtoMapper;
    private final ProductPropertyRepository productPropertyRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryDtoMapper categoryDtoMapper, ProductPropertyRepository productPropertyRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryDtoMapper = categoryDtoMapper;
        this.productPropertyRepository = productPropertyRepository;
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
}
