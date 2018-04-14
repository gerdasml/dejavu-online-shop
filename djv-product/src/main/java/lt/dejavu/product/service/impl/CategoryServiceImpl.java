package lt.dejavu.product.service.impl;

import lt.dejavu.product.model.Category;
import lt.dejavu.product.model.rest.mapper.CategoryRequestMapper;
import lt.dejavu.product.model.rest.request.CreateCategoryRequest;
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
        return categoryViewMapper.map(categoryRepository.getCategory(id));
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
