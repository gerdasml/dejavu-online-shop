package lt.dejavu.product.service.impl;

import lt.dejavu.product.dto.CategoryDto;
import lt.dejavu.product.dto.mapper.CategoryDtoMapper;
import lt.dejavu.product.model.Category;
import lt.dejavu.product.model.rest.mapper.CategoryRequestMapper;
import lt.dejavu.product.model.rest.request.CreateCategoryRequest;
import lt.dejavu.product.repository.CategoryRepository;
import lt.dejavu.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryRequestMapper categoryRequestMapper;
    private final CategoryDtoMapper categoryDtoMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryRequestMapper categoryRequestMapper, CategoryDtoMapper categoryDtoMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryRequestMapper = categoryRequestMapper;
        this.categoryDtoMapper = categoryDtoMapper;
    }

    @Override
    public CategoryDto getCategory(long id) {
        return categoryDtoMapper.map(categoryRepository.getCategory(id));
    }

    @Override
    public List<CategoryDto> getRootCategories() {
        return categoryDtoMapper.map(categoryRepository.getRootCategories());
    }

    @Override
    public List<CategoryDto> getSubCategories(long categoryId) {
        return categoryDtoMapper.map(categoryRepository.getSubCategories(categoryId));
    }

    @Override
    public Long createCategory(CreateCategoryRequest categoryRequest) {
        Category parentCategory = resolveParentCategory(categoryRequest);
        Category category = categoryRequestMapper.mapToCategory(categoryRequest, parentCategory);
        return categoryRepository.saveCategory(category);
    }

    private Category resolveParentCategory(CreateCategoryRequest categoryRequest) {
        if (categoryRequest.getParentCategory() == null) {
            return null;
        }
        Category parentCategory = categoryRepository.getCategory(categoryRequest.getParentCategory());
        if (parentCategory == null) {
            //TODO proper error or migrate to pure hibernate
            throw new IllegalArgumentException("cannot find categoryId with parent id: " + categoryRequest.getParentCategory());
        }
        return parentCategory;
    }
}
