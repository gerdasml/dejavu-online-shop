package lt.dejavu.product.service.impl;

import lt.dejavu.product.model.Category;
import lt.dejavu.product.repository.CategoryRepository;
import lt.dejavu.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category getCategory(long id) {
        return categoryRepository.getCategory(id);
    }
}
