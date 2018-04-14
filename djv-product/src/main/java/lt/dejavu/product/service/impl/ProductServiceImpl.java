package lt.dejavu.product.service.impl;

import lt.dejavu.product.exception.CategoryNotFoundException;
import lt.dejavu.product.exception.ProductNotFoundException;
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

        return productViewMapper.map(getProductIfExist(id));
    }

    @Override
    public List<ProductView> getProductsByCategory(long categoryId) {
        getCategoryIfExist(categoryId);
        return productViewMapper.map(productRepository.getProductsByCategory(categoryId));
    }

    @Override
    public Long createProduct(CreateProductRequest request) {
        Category productCategory = resolveProductCategory(request);
        Product product = productRequestMapper.mapToProduct(request, productCategory);
        return productRepository.saveProduct(product);
    }

    @Override
    public void deleteProduct(long productId) {
        Product product = getProductIfExist(productId);
        productRepository.deleteProduct(product);
    }

    @Override
    public void updateProduct(long productId, CreateProductRequest request) {
        Product oldProduct = getProductIfExist(productId);
        Product newProduct = productRequestMapper.mapToProduct(request, resolveProductCategory(request));
        newProduct.setId(oldProduct.getId());
        productRepository.updateProduct(newProduct);
    }


    private Category resolveProductCategory(CreateProductRequest request){
        if (request.getCategoryId() == null) {
            return null;
        }
        return getCategoryIfExist(request.getCategoryId());
    }

    private Product getProductIfExist(long productId){
        Product product = productRepository.getProduct(productId);
        if (product == null) {
            throw new ProductNotFoundException("cannot find specified product");
        }
        return product;
    }
    private Category getCategoryIfExist(long categoryId){
        Category category = categoryRepository.getCategory(categoryId);
        if (category == null) {
            throw new CategoryNotFoundException("cannot find specified category");
        }
        return category;
    }
}
