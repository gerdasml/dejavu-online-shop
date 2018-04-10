package lt.dejavu.product.service.impl;

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

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryRequestMapper categoryRequestMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryRequestMapper = categoryRequestMapper;
    }

    @Override
    public Category getCategory(long id) {
        return categoryRepository.getCategory(id);
    }

    @Override
    public List<Category> getRootCategories() {
        return categoryRepository.getRootCategories();
    }

    @Override
    public List<Category> getSubCategories(long categoryId) {
        return categoryRepository.getSubCategories(categoryId);
    }

    @Override
    public Long createCategory(CreateCategoryRequest categoryRequest) {
        Category parentCategory = resolveParentCategory(categoryRequest);
        Category category = categoryRequestMapper.mapToCategory(categoryRequest, parentCategory);
        return categoryRepository.saveCategory(category);
    }

    private Category resolveParentCategory(CreateCategoryRequest categoryRequest) {
        if (categoryRequest.getParentCategory() != null) {
            Category parentCategory = categoryRepository.getCategory(categoryRequest.getParentCategory());
            if (parentCategory == null) {
                //TODO proper error or migrate to pure hibernate
                throw new IllegalArgumentException("cannot find categoryId with parent id: " + categoryRequest.getParentCategory());
            }
            return parentCategory;
        }
        return null;
    }
}
