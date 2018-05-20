package lt.dejavu.product.service.impl;

import lt.dejavu.excel.service.ExcelService;
import lt.dejavu.product.model.rest.request.ProductSearchRequest;
import lt.dejavu.product.response.ProductResponse;
import lt.dejavu.product.response.mapper.ProductResponseMapper;
import lt.dejavu.product.exception.CategoryNotFoundException;
import lt.dejavu.product.exception.ProductNotFoundException;
import lt.dejavu.product.exception.ProductPropertyNotFoundException;
import lt.dejavu.product.model.Category;
import lt.dejavu.product.model.CategoryProperty;
import lt.dejavu.product.model.Product;
import lt.dejavu.product.model.ProductProperty;
import lt.dejavu.product.model.rest.mapper.ProductPropertyRequestMapper;
import lt.dejavu.product.model.rest.mapper.ProductRequestMapper;
import lt.dejavu.product.model.rest.request.ProductPropertyRequest;
import lt.dejavu.product.model.rest.request.ProductRequest;
import lt.dejavu.product.repository.CategoryRepository;
import lt.dejavu.product.repository.ProductPropertyRepository;
import lt.dejavu.product.repository.ProductRepository;
import lt.dejavu.product.service.ProductService;
import lt.dejavu.utils.collections.UpdatableCollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    //TODO single responsabilty?

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRequestMapper productRequestMapper;
    private final ProductResponseMapper productResponseMapper;

    private final ProductPropertyRequestMapper productPropertyRequestMapper;
    private final ProductPropertyRepository productPropertyRepository;


    private final ExcelService<Product> excelService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository,
                              ProductRequestMapper productRequestMapper, ProductResponseMapper productResponseMapper, ExcelService<Product> excelService,
                              ProductPropertyRequestMapper productPropertyRequestMapper, ProductPropertyRepository productPropertyRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productRequestMapper = productRequestMapper;
        this.productResponseMapper = productResponseMapper;

        this.productPropertyRequestMapper = productPropertyRequestMapper;
        this.productPropertyRepository = productPropertyRepository;

        this.excelService = excelService;

    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productResponseMapper.map(productRepository.getAllProducts());
    }

    @Override
    public ProductResponse getProduct(long id) {
        return productResponseMapper.map(getProductIfExist(id));
    }

    @Override
    public ProductResponse getProduct(String identifier) {
        return productResponseMapper.map(getProductIfExist(identifier));
    }

    @Override
    public List<ProductResponse> getProductsByCategory(long categoryId) {
        getCategoryIfExist(categoryId);
        return productResponseMapper.map(productRepository.getProductsByCategory(categoryId));
    }

    @Override
    public List<ProductResponse> searchProducts(ProductSearchRequest request) {
        // TODO: maybe add some additional search options?
        Category category = getCategoryIfExist(request.getCategoryIdentifier());
        return getProductsByCategory(category.getId());
    }

    @Transactional
    @Override
    public Long createProduct(ProductRequest request) {
        Category productCategory = resolveProductCategory(request);
        Product product = productRequestMapper.mapToProduct(request, productCategory);
        product.setCreationDate(LocalDateTime.now());
        Long productId = productRepository.saveProduct(product);
        Set<CategoryProperty> properties = getProductCategoryProperties(request, productCategory);
        Set<ProductProperty> propertyValues = productPropertyRequestMapper.mapProperties(product, properties, request.getProperties());
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
        Category productCategory = resolveProductCategory(request);
        Product oldProduct = getProductIfExist(productId);
        productRequestMapper.remapToProduct(oldProduct, request, productCategory);
        Set<CategoryProperty> properties = getProductCategoryProperties(request, productCategory);
        Set<ProductProperty> propertyValues = productPropertyRequestMapper.mapProperties(oldProduct, properties, request.getProperties());
        UpdatableCollectionUtils.updateCollection(oldProduct.getProperties(), propertyValues);
        productRepository.updateProduct(oldProduct);
    }

    private Set<CategoryProperty> getProductCategoryProperties(ProductRequest request, Category productCategory) {
        Set<Long> propertyIds = getPropertyIds(request);
        Set<CategoryProperty> properties = productPropertyRepository.findByCategoryIdAndIds(productCategory.getId(), propertyIds);
        checkIfAllPropertiesWereFound(propertyIds, properties);
        return properties;
    }


    private Set<Long> getPropertyIds(ProductRequest request) {
        return request.getProperties().stream().map(ProductPropertyRequest::getPropertyId).collect(toSet());
    }
    
    @Override
    public ByteArrayOutputStream exportProducts() throws IOException {
        return excelService.toExcel(productRepository.getAllProducts());
    }

    @Override
    public UUID importProducts(byte[] data) throws IOException {
        return excelService.fromExcel(data);
    }

    private Category resolveProductCategory(ProductRequest request) {
        if (request.getCategoryId() == null) {
            throw new IllegalStateException("Product must have category");
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

    private Product getProductIfExist(String identifier) {
        Product product = productRepository.getProduct(identifier);
        if (product == null) {
            throw new ProductNotFoundException("The product with the specified identifier was not found");
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

    private Category getCategoryIfExist(String identifier) {
        Category category = categoryRepository.getCategory(identifier);
        if (category == null) {
            throw new CategoryNotFoundException("The category with the specified identifier was not found");
        }
        return category;
    }

    private void checkIfAllPropertiesWereFound(Set<Long> propertyIds, Set<CategoryProperty> properties) {
        if (propertyIds.size() != properties.size()) {
            throw new ProductPropertyNotFoundException("Not product all properties were found in given category");
            //TODO more details
        }

    }
}
