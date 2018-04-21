package lt.dejavu.product.service.impl;

import lt.dejavu.product.exception.CategoryNotFoundException;
import lt.dejavu.product.exception.IllegalRequestDataException;
import lt.dejavu.product.model.Category;
import lt.dejavu.product.model.rest.mapper.CategoryRequestMapper;
import lt.dejavu.product.model.rest.request.CategoryRequest;
import lt.dejavu.product.repository.CategoryRepository;
import lt.dejavu.product.service.CategoryService;
import lt.dejavu.product.view.CategoryView;
import lt.dejavu.product.view.mapper.CategoryViewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryRequestMapper categoryRequestMapper;
    private final CategoryViewMapper categoryViewMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryRequestMapper categoryRequestMapper,  CategoryViewMapper categoryViewMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryRequestMapper = categoryRequestMapper;
        this.categoryViewMapper = categoryViewMapper;
    }

    @Override
    public CategoryView getCategory(long id) {
        return categoryViewMapper.map(getCategoryIfExist(id));
    }

    @Override
    public List<CategoryView> getRootCategories() {
        return categoryViewMapper.map(categoryRepository.getRootCategories());
    }

    @Override
    public List<CategoryView> getSubCategories(long categoryId) {
        return categoryViewMapper.map(categoryRepository.getSubCategories(categoryId));
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
            throw new CategoryNotFoundException("cannot find category with id " + categoryId);
        }
        return category;
    }
}
