package lt.dejavu.product.service.impl;

import lt.dejavu.product.dto.ProductDto;
import lt.dejavu.product.dto.mapper.ProductDtoMapper;
import lt.dejavu.product.exception.CategoryNotFoundException;
import lt.dejavu.product.exception.ProductNotFoundException;
import lt.dejavu.product.model.Category;
import lt.dejavu.product.model.Product;
import lt.dejavu.product.model.ProductProperty;
import lt.dejavu.product.model.ProductPropertyValue;
import lt.dejavu.product.model.rest.mapper.ProductPropertyRequestMapper;
import lt.dejavu.product.model.rest.mapper.ProductRequestMapper;
import lt.dejavu.product.model.rest.request.ProductPropertyRequest;
import lt.dejavu.product.model.rest.request.ProductRequest;
import lt.dejavu.product.repository.CategoryRepository;
import lt.dejavu.product.repository.ProductPropertyRepository;
import lt.dejavu.product.repository.ProductRepository;
import lt.dejavu.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
public class ProductServiceImpl implements ProductService {

    //TODO single responsabilty?

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRequestMapper productRequestMapper;
    private final ProductDtoMapper productDtoMapper;
    private final ProductPropertyRequestMapper productPropertyRequestMapper;
    private final ProductPropertyRepository productPropertyRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository,
                              ProductRequestMapper productRequestMapper, ProductDtoMapper productDtoMapper,
                              ProductPropertyRequestMapper productPropertyRequestMapper, ProductPropertyRepository productPropertyRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productRequestMapper = productRequestMapper;
        this.productDtoMapper = productDtoMapper;
        this.productPropertyRequestMapper = productPropertyRequestMapper;
        this.productPropertyRepository = productPropertyRepository;
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

    @Transactional
    @Override
    public Long createProduct(ProductRequest request) {
        Category productCategory = resolveProductCategory(request);
        Product product = productRequestMapper.mapToProduct(request, productCategory);
        product.setCreationDate(LocalDateTime.now());
        Set<ProductProperty> properties =  productPropertyRepository.findByIds(
                request.getProperties().stream().map(ProductPropertyRequest::getPropertyId).collect(toSet())
        );
        Long productId =  productRepository.saveProduct(product);
        Set<ProductPropertyValue> propertyValues = productPropertyRequestMapper.mapProperties(product, properties ,request.getProperties());
        productPropertyRepository.savePropertyValues(propertyValues);
        return productId;
    }

    @Override
    public void deleteProduct(long productId) {
        Product product = getProductIfExist(productId);
        productRepository.deleteProduct(product);
    }

    @Transactional
    @Override
    public void updateProduct(long productId, ProductRequest request) {
        Product oldProduct = getProductIfExist(productId);
        Product newProduct = productRequestMapper.mapToProduct(request, resolveProductCategory(request));
        newProduct.setId(oldProduct.getId());
        productRepository.updateProduct(newProduct);
        Set<ProductProperty> properties =  productPropertyRepository.findByIds(
                request.getProperties().stream().map(ProductPropertyRequest::getPropertyId).collect(toSet())
        );
        Set<ProductPropertyValue> propertyValues = productPropertyRequestMapper.mapProperties(newProduct, properties ,request.getProperties());
        productPropertyRepository.savePropertyValues(propertyValues);
    }

    private Category resolveProductCategory(ProductRequest request) {
        if (request.getCategoryId() == null) {
            return null;
        }
        return getCategoryIfExist(request.getCategoryId());
    }

    private Product getProductIfExist(long productId) {
        Product product = productRepository.getProduct(productId);
        if (product == null) {
            throw new ProductNotFoundException(productId);
        }
        return product;
    }

    private Category getCategoryIfExist(long categoryId) {
        Category category = categoryRepository.getCategory(categoryId);
        if (category == null) {
            throw new CategoryNotFoundException("cannot find category with id " + categoryId);
        }
        return category;
    }
}
