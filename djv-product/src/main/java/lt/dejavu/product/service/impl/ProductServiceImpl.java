package lt.dejavu.product.service.impl;

import lt.dejavu.product.model.Category;
import lt.dejavu.product.model.Product;
import lt.dejavu.product.model.rest.mapper.ProductRequestMapper;
import lt.dejavu.product.model.rest.request.CreateProductRequest;
import lt.dejavu.product.repository.CategoryRepository;
import lt.dejavu.product.repository.ProductRepository;
import lt.dejavu.product.service.ProductService;
import lt.dejavu.product.view.ProductView;
import lt.dejavu.product.view.mapper.ProductViewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRequestMapper productRequestMapper;
    private final ProductViewMapper productViewMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository,
                              ProductRequestMapper productRequestMapper, ProductViewMapper productViewMapper){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productRequestMapper = productRequestMapper;
        this.productViewMapper = productViewMapper;
    }

    @Override
    public ProductView getProduct(long id) {
        return productViewMapper.map(productRepository.getProduct(id));
    }

    @Override
    public List<ProductView> getProductsByCategory(long categoryId) {
        return productViewMapper.map(productRepository.getProductsByCategory(categoryId));
    }

    @Override
    public Long createProduct(CreateProductRequest request) {
        Category productCategory = resolveProductCategory(request);
        Product product = productRequestMapper.mapToProduct(request, productCategory);
        return productRepository.saveProduct(product);
    }

    private Category resolveProductCategory(CreateProductRequest request){
        if (request.getCategoryId() == null) {
            return null;
        }
        Category category = categoryRepository.getCategory(request.getCategoryId());
        if (category == null) {
            //TODO proper error
            throw new IllegalArgumentException("cannot find categoryId with id: " + request.getCategoryId());
        }
        return category;
    }
}
