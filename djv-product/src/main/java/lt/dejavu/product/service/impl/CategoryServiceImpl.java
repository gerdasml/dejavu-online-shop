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

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Autowired
    private CategoryRequestMapper categoryRequestMapper;

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
        Category category = categoryRequestMapper.mapToCategory(categoryRequest);
        if (categoryRequest.getParentCategory() != null) {
            Category parentCategory = categoryRepository.getCategory(categoryRequest.getParentCategory());
            if (parentCategory == null) {
                //TODO proper error
                throw new IllegalArgumentException("cannot find category with parent id" + categoryRequest.getParentCategory());
            }
            category.setParentCategory(parentCategory);
        }
        return categoryRepository.saveCategory(category);
    }
}
