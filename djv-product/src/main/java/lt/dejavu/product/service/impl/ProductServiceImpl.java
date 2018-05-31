package lt.dejavu.product.service.impl;

import lt.dejavu.excel.service.ExcelService;
import lt.dejavu.product.dto.ProductDto;
import lt.dejavu.product.dto.ProductPropertyDto;
import lt.dejavu.product.dto.mapper.ProductDtoMapper;
import lt.dejavu.product.dto.mapper.ProductPropertyDtoMapper;
import lt.dejavu.product.exception.ProductAlreadyExistException;
import lt.dejavu.product.model.*;
import lt.dejavu.product.model.rest.request.ProductSearchRequest;
import lt.dejavu.product.exception.CategoryNotFoundException;
import lt.dejavu.product.exception.ProductNotFoundException;
import lt.dejavu.product.exception.ProductPropertyNotFoundException;
import lt.dejavu.product.repository.CategoryRepository;
import lt.dejavu.product.repository.PropertyRepository;
import lt.dejavu.product.repository.ProductRepository;
import lt.dejavu.product.service.DiscountService;
import lt.dejavu.product.service.ProductService;
import lt.dejavu.utils.collections.UpdatableCollectionUtils;
import lt.dejavu.utils.debug.Profiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.stream.Collectors.toSet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductDtoMapper productDtoMapper;

    private final ProductPropertyDtoMapper productPropertyDtoMapper;
    private final PropertyRepository propertyRepository;

    private final ExcelService<Product> excelService;
    private final DiscountService discountService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository,
                              ProductDtoMapper productDtoMapper, ExcelService<Product> excelService,
                              ProductPropertyDtoMapper productPropertyDtoMapper, PropertyRepository propertyRepository,
                              DiscountService discountService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productDtoMapper = productDtoMapper;

        this.productPropertyDtoMapper = productPropertyDtoMapper;
        this.propertyRepository = propertyRepository;

        this.excelService = excelService;

        this.discountService = discountService;
    }

    @Override
    public List<ProductDto> getAllProducts(Integer limit, Integer offset, SortBy sortBy, SortDirection sortDirection) {
        if (limit == null) {
            limit = Integer.MAX_VALUE;
        }
        if (offset == null) {
            offset = 0;
        }
        return discountService.attachDiscount(productRepository.getAllProducts(offset, limit, sortBy, sortDirection));
    }

    @Override
    public ProductDto getProduct(long id) {
        return discountService.attachDiscount(getProductIfExist(id));
    }

    @Override
    public ProductDto getProduct(String identifier) {
        return discountService.attachDiscount(getProductIfExist(identifier));
    }

    @Override
    public List<ProductDto> getProductsByCategory(long categoryId, Integer offset, Integer limit, SortBy sortBy, SortDirection sortDirection) {
        getCategoryIfExist(categoryId);
        if (limit == null) {
            limit = Integer.MAX_VALUE;
        }
        if (offset == null) {
            offset = 0;
        }
        return discountService.attachDiscount(productRepository.getProductsByCategory(categoryId, offset, limit, sortBy, sortDirection));

    }

    @Override
    public SearchResult<ProductDto> searchProducts(ProductSearchRequest request, Integer offset, Integer limit, SortBy sortBy, SortDirection sortDirection) {
        if (limit == null) {
            limit = Integer.MAX_VALUE;
        }
        if (offset == null) {
            offset = 0;
        }
        SearchResult<Product> dbResult = productRepository.searchForProducts(request, offset, limit, sortBy, sortDirection);
        SearchResult<ProductDto> result = new SearchResult<>();
        result.setTotal(dbResult.getTotal());
        result.setResults(discountService.attachDiscount(dbResult.getResults()));
        return result;
    }

    @Transactional
    @Override
    public Long createProduct(ProductDto request) {
        Category productCategory = resolveCategory(request.getCategoryId());
        checkSku(request.getSkuCode());
        Product product = productDtoMapper.mapToProduct(request, productCategory);
        product.setCreationDate(LocalDateTime.now());
        Long productId = productRepository.saveProduct(product);
        Set<CategoryProperty> properties = getProductCategoryProperties(request);
        Set<ProductProperty> propertyValues = productPropertyDtoMapper.mapProperties(product, properties, request.getProperties());
        propertyRepository.savePropertyValues(propertyValues);
        return productId;
    }

    @Override
    public void deleteProduct(long productId) {
        Product product = getProductIfExist(productId);
        productRepository.deleteProduct(product);
    }

    @Transactional
    @Override
    public void updateProduct(long productId, ProductDto request) {
        Category productCategory = resolveCategory(request.getCategoryId());
        checkSku(request.getSkuCode());
        Product oldProduct = getProductIfExist(productId);
        productDtoMapper.remapToProduct(oldProduct, request, productCategory);
        Set<CategoryProperty> properties = getProductCategoryProperties(request);
        Set<ProductProperty> propertyValues = productPropertyDtoMapper.mapProperties(oldProduct, properties, request.getProperties());
        UpdatableCollectionUtils.updateCollection(oldProduct.getProperties(), propertyValues);
        productRepository.updateProduct(oldProduct);
    }

    private Set<CategoryProperty> getProductCategoryProperties(ProductDto request) {
        Set<Long> propertyIds = getPropertyIds(request);
        Set<CategoryProperty> properties = propertyRepository.findByIds(propertyIds);
        checkIfAllPropertiesWereFound(propertyIds, properties);
        return properties;
    }

    private void checkSku(String sku){
        if (productRepository.productWithSkuExits(sku)){
            throw new ProductAlreadyExistException("Products with such sku code already exist");
        }
    }


    private Set<Long> getPropertyIds(ProductDto request) {
        return request.getProperties().stream().map(ProductPropertyDto::getPropertyId).collect(toSet());
    }
    
    @Override
    public ByteArrayOutputStream exportProducts() throws IOException {
        Set<Product> allProducts = Profiler.time("Get all products", () -> productRepository.getAllProducts(0, Integer.MAX_VALUE, null, null));
        return excelService.toExcel(allProducts);
    }

    @Override
    public UUID importProducts(byte[] data) throws IOException {
        return excelService.fromExcel(data);
    }

    @Override
    public long getTotalProductCount() {
        return productRepository.getTotalProductCount();
    }

    private Category resolveCategory(Long categoryId) {
        if (categoryId == null) {
            throw new IllegalStateException("Product must have category");
        }
        Category category = getCategoryIfExist(categoryId);
        return getCategoryIfExist(categoryId);
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
            throw new ProductPropertyNotFoundException("Not all product properties were found in given category");
            //TODO more details
        }

    }
}
