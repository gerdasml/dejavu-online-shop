package lt.dejavu.product.service.impl;

import lt.dejavu.product.dto.CategoryDto;
import lt.dejavu.product.dto.CategoryTreeDto;
import org.springframework.beans.factory.annotation.Autowired;
import lt.dejavu.product.exception.CategoryNotFoundException;
import lt.dejavu.product.exception.IllegalRequestDataException;
import lt.dejavu.product.dto.mapper.CategoryDtoMapper;
import lt.dejavu.product.model.Category;
import lt.dejavu.product.model.rest.mapper.CategoryRequestMapper;
import lt.dejavu.product.model.rest.request.CategoryRequest;
import lt.dejavu.product.repository.CategoryRepository;
import lt.dejavu.product.service.CategoryService;
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
        return categoryDtoMapper.map(getCategoryIfExist(id));
    }

    @Override
    public List<CategoryDto> getRootCategories() {
        return categoryDtoMapper.map(categoryRepository.getRootCategories());
    }

    @Override
    public List<CategoryTreeDto> getCategoryTree(){
        return categoryDtoMapper.mapToTree(categoryRepository.getAllCategories());
    }


    @Override
    public List<CategoryDto> getSubCategories(long categoryId) {
        return categoryDtoMapper.map(categoryRepository.getSubCategories(categoryId));
    }

    @Override
    public Long createCategory(CategoryRequest categoryRequest) {
        Category parentCategory = resolveParentCategory(categoryRequest);
        Category category = categoryRequestMapper.mapToCategory(categoryRequest, parentCategory);
        return categoryRepository.saveCategory(category);
    }

    @Override
    public void updateCategory(long categoryId, CategoryRequest categoryRequest) {
        validateNotSelfParent(categoryId, categoryRequest.getParentCategory());
        Category oldCategory = getCategoryIfExist(categoryId);
        Category parentCategory = resolveParentCategory(categoryRequest);
        Category newCategory = categoryRequestMapper.mapToCategory(categoryRequest, parentCategory);
        newCategory.setId(oldCategory.getId());
        categoryRepository.updateCategory(newCategory);
    }

    @Override
    public void deleteCategory(long categoryId) {
        Category category = getCategoryIfExist(categoryId);
        categoryRepository.deleteCategory(category);
    }

    private void validateNotSelfParent(long id, Long parentId){
        if (parentId == null) {
            return;
        }
        if (parentId.equals(id)){
            throw new IllegalRequestDataException("category cannot be self parent");
        }
    }

    private Category resolveParentCategory(CategoryRequest categoryRequest) {
        if (categoryRequest.getParentCategory() == null) {
            return null;
        }
        Category parentCategory = categoryRepository.getCategory(categoryRequest.getParentCategory());
        if (parentCategory == null) {
            throw new CategoryNotFoundException("cannot find category with parent id: " + categoryRequest.getParentCategory());
        }
        return parentCategory;
    }

    private Category getCategoryIfExist(long categoryId){
        Category category = categoryRepository.getCategory(categoryId);
        if (category == null) {
            throw new CategoryNotFoundException(categoryId);
        }
        return category;
    }
}
