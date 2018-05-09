package lt.dejavu.product.service.impl;

import lt.dejavu.product.exception.CategoryNotFoundException;
import lt.dejavu.product.exception.ProductNotFoundException;
import lt.dejavu.product.dto.ProductDto;
import lt.dejavu.product.dto.mapper.ProductDtoMapper;
import lt.dejavu.product.model.Category;
import lt.dejavu.product.model.Product;
import lt.dejavu.product.model.rest.mapper.ProductRequestMapper;
import lt.dejavu.product.model.rest.request.ProductRequest;
import lt.dejavu.product.repository.CategoryRepository;
import lt.dejavu.product.repository.ProductRepository;
import lt.dejavu.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRequestMapper productRequestMapper;
    private final ProductDtoMapper productDtoMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository,
                              ProductRequestMapper productRequestMapper, ProductDtoMapper productDtoMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productRequestMapper = productRequestMapper;
        this.productDtoMapper = productDtoMapper;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productDtoMapper.map(productRepository.getAllProducts());
    }

    @Override
    public ProductDto getProduct(long id) {
        return productDtoMapper.map(getProductIfExist(id));
    }

    @Override
    public List<ProductDto> getProductsByCategory(long categoryId) {
        getCategoryIfExist(categoryId);
        return productDtoMapper.map(productRepository.getProductsByCategory(categoryId));
    }

    @Override
    public Long createProduct(ProductRequest request) {
        Category productCategory = resolveProductCategory(request);
        Product product = productRequestMapper.mapToProduct(request, productCategory);
        product.setCreationDate(LocalDateTime.now());
        return productRepository.saveProduct(product);
    }

    @Override
    public void deleteProduct(long productId) {
        Product product = getProductIfExist(productId);
        productRepository.deleteProduct(product);
    }

    @Override
    public void updateProduct(long productId, ProductRequest request) {
        Product oldProduct = getProductIfExist(productId);
        Product newProduct = productRequestMapper.mapToProduct(request, resolveProductCategory(request));
        newProduct.setId(oldProduct.getId());
        productRepository.updateProduct(newProduct);
    }


    private Category resolveProductCategory(ProductRequest request){
        if (request.getCategoryId() == null) {
            return null;
        }
        return getCategoryIfExist(request.getCategoryId());
    }

    private Product getProductIfExist(long productId){
        Product product = productRepository.getProduct(productId);
        if (product == null) {
            throw new ProductNotFoundException(productId);
        }
        return product;
    }
    private Category getCategoryIfExist(long categoryId){
        Category category = categoryRepository.getCategory(categoryId);
        if (category == null) {
            throw new CategoryNotFoundException("cannot find category with id " + categoryId);
        }
        return category;
    }
}
