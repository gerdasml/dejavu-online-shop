package lt.dejavu.product.service.impl;

import lt.dejavu.excel.service.ExcelService;
import lt.dejavu.product.dto.ProductDto;
import lt.dejavu.product.dto.ProductPropertyDto;
import lt.dejavu.product.dto.discount.ProductDiscountDto;
import lt.dejavu.product.dto.mapper.ProductDtoMapper;
import lt.dejavu.product.dto.mapper.ProductPropertyDtoMapper;
import lt.dejavu.product.model.*;
import lt.dejavu.product.model.rest.request.ProductSearchRequest;
import lt.dejavu.product.exception.CategoryNotFoundException;
import lt.dejavu.product.exception.ProductNotFoundException;
import lt.dejavu.product.exception.ProductPropertyNotFoundException;
import lt.dejavu.product.repository.CategoryRepository;
import lt.dejavu.product.repository.ProductPropertyRepository;
import lt.dejavu.product.repository.ProductRepository;
import lt.dejavu.product.service.DiscountService;
import lt.dejavu.product.service.ProductService;
import lt.dejavu.product.strategy.IdentifierGenerator;
import lt.dejavu.utils.collections.UpdatableCollectionUtils;
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
    private final ProductPropertyRepository productPropertyRepository;

    private final IdentifierGenerator<Product> productIdentifierGenerator;

    private final ExcelService<Product> excelService;
    private final DiscountService discountService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository,
                              ProductDtoMapper productDtoMapper, ExcelService<Product> excelService,
                              ProductPropertyDtoMapper productPropertyDtoMapper, ProductPropertyRepository productPropertyRepository, IdentifierGenerator<Product> productIdentifierGenerator, DiscountService discountService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productDtoMapper = productDtoMapper;

        this.productPropertyDtoMapper = productPropertyDtoMapper;
        this.productPropertyRepository = productPropertyRepository;

        this.excelService = excelService;

        this.productIdentifierGenerator = productIdentifierGenerator;
        this.discountService = discountService;
    }

    @Override
    public List<ProductDto> getAllProducts(Integer limit, Integer offset) {
        if (limit == null) {
            limit = Integer.MAX_VALUE;
        }
        if (offset == null) {
            offset = 0;
        }
        return mapToDtoWithDiscount(productRepository.getAllProducts(offset, limit));
        }

    @Override
    public ProductDto getProduct(long id) {
        return mapToDtoWithDiscount(getProductIfExist(id));
    }

    @Override
    public ProductDto getProduct(String identifier) {
        return mapToDtoWithDiscount(getProductIfExist(identifier));
    }

    @Override
    public List<ProductDto> getProductsByCategory(long categoryId, Integer offset, Integer limit) {
        getCategoryIfExist(categoryId);
        if (limit == null) {
            limit = Integer.MAX_VALUE;
        }
        if (offset == null) {
            offset = 0;
        }
        return mapToDtoWithDiscount(productRepository.getProductsByCategory(categoryId, offset, limit));

    }

    @Override
    public SearchResult<ProductDto> searchProducts(ProductSearchRequest request, Integer offset, Integer limit) {
        if (limit == null) {
            limit = Integer.MAX_VALUE;
        }
        if (offset == null) {
            offset = 0;
        }
        SearchResult<Product> dbResult = productRepository.searchForProducts(request, offset, limit);
        SearchResult<ProductDto> result = new SearchResult<>();
        result.setTotal(dbResult.getTotal());
        result.setResults(mapToDtoWithDiscount(dbResult.getResults()));
        return result;
    }

    @Transactional
    @Override
    public Long createProduct(ProductDto request) {
        Category productCategory = resolveCategory(request.getCategoryId());
        Product product = productDtoMapper.mapToProduct(request, productCategory);
        product.setCreationDate(LocalDateTime.now());
        Long productId = productRepository.saveProduct(product);
        product.setIdentifier(productIdentifierGenerator.generateIdentifier(product));
        productRepository.updateProduct(product);
        Set<CategoryProperty> properties = getProductCategoryProperties(request, productCategory);
        Set<ProductProperty> propertyValues = productPropertyDtoMapper.mapProperties(product, properties, request.getProperties());
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
    public void updateProduct(long productId, ProductDto request) {
        Category productCategory = resolveCategory(request.getCategoryId());
        Product oldProduct = getProductIfExist(productId);
        productDtoMapper.remapToProduct(oldProduct, request, productCategory);
        oldProduct.setIdentifier(productIdentifierGenerator.generateIdentifier(oldProduct));
        Set<CategoryProperty> properties = getProductCategoryProperties(request, productCategory);
        Set<ProductProperty> propertyValues = productPropertyDtoMapper.mapProperties(oldProduct, properties, request.getProperties());
        UpdatableCollectionUtils.updateCollection(oldProduct.getProperties(), propertyValues);
        productRepository.updateProduct(oldProduct);
    }

    private ProductDto mapToDtoWithDiscount(Product product) {
        ProductDiscountDto discount = discountService.getProductDiscount(product);
        ProductDto productDto = productDtoMapper.mapToDto(product);
        productDto.setDiscount(discount);
        return productDto;
    }


    private List<ProductDto> mapToDtoWithDiscount(Collection<Product> products) {
        Map<Long, ProductDiscountDto> productsDiscounts = discountService.getProductsDiscounts(products);
        List<ProductDto> productDtos = productDtoMapper.mapToDto(products);
        productDtos.forEach(
                productDto -> productDto.setDiscount(productsDiscounts.get(productDto.getId()))
        );
        return productDtos;
    }

    private Set<CategoryProperty> getProductCategoryProperties(ProductDto request, Category productCategory) {
        Set<Long> propertyIds = getPropertyIds(request);
        Set<CategoryProperty> properties = productPropertyRepository.findByCategoryIdAndIds(productCategory.getId(), propertyIds);
        checkIfAllPropertiesWereFound(propertyIds, properties);
        return properties;
    }


    private Set<Long> getPropertyIds(ProductDto request) {
        return request.getProperties().stream().map(ProductPropertyDto::getPropertyId).collect(toSet());
    }
    
    @Override
    public ByteArrayOutputStream exportProducts() throws IOException {
        return excelService.toExcel(productRepository.getAllProducts(0, Integer.MAX_VALUE));
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
            throw new ProductPropertyNotFoundException("Not product all properties were found in given category");
            //TODO more details
        }

    }
}
