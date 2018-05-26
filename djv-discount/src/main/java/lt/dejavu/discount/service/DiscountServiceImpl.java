package lt.dejavu.discount.service;

import lt.dejavu.discount.exception.DiscountNotFoundException;
import lt.dejavu.discount.model.db.CategoryDiscount;
import lt.dejavu.discount.model.db.Discount;
import lt.dejavu.discount.model.db.ProductDiscount;
import lt.dejavu.discount.model.mapper.DiscountMapper;
import lt.dejavu.discount.repository.DiscountRepository;
import lt.dejavu.product.dto.discount.DiscountDto;
import lt.dejavu.product.dto.discount.ProductDiscountDto;
import lt.dejavu.product.exception.CategoryNotFoundException;
import lt.dejavu.product.exception.ProductNotFoundException;
import lt.dejavu.product.model.Category;
import lt.dejavu.product.model.DiscountTarget;
import lt.dejavu.product.model.DiscountType;
import lt.dejavu.product.model.Product;
import lt.dejavu.product.repository.CategoryRepository;
import lt.dejavu.product.repository.ProductRepository;
import lt.dejavu.product.service.DiscountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Service
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;
    private final DiscountMapper discountMapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    public DiscountServiceImpl(DiscountRepository discountRepository, DiscountMapper discountMapper, ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.discountRepository = discountRepository;
        this.discountMapper = discountMapper;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<DiscountDto> getAllDiscounts() {
        return discountMapper.mapToDto(discountRepository.getAllDiscounts());
    }

    @Override
    public DiscountDto getDiscountById(long id) {
        Discount discount = discountRepository.getDiscount(id);
        if (discount == null) {
            throw new DiscountNotFoundException("The discount with the specified ID was not found");
        }
        return discountMapper.mapToDto(discount);
    }

    @Override
    public long addDiscount(DiscountDto discount) {
        validateTarget(discount);
        Discount dbDiscount = discountMapper.mapToDiscount(discount);
        discountRepository.addDiscount(dbDiscount);
        return dbDiscount.getId();
    }

    @Override
    public void updateDiscount(long id, DiscountDto newDiscount) {
        validateTarget(newDiscount);
        if (discountRepository.getDiscount(id) == null) {
            throw new DiscountNotFoundException("The discount with the specified ID was not found");
        }
        discountRepository.updateDiscount(id, discountMapper.mapToDiscount(newDiscount));
    }

    @Override
    public void deleteDiscount(long id) {
        discountRepository.deleteDiscount(id);
    }

    @Override
    public ProductDiscountDto getProductDiscount(long productId) {
        Product product = productRepository.getProduct(productId);
        List<Discount> productDiscounts =
                discountRepository.getAllDiscounts()
                                  .stream()
                                  .filter(this::isDiscountActive)
                                  .filter(d -> doesDiscountApplyToProduct(d, product))
                                  .collect(toList());
        Discount result = productDiscounts.stream().reduce(null, (a, b) -> reduceDiscount(a, b, product));
        if (result == null) {
            return null;
        }
        ProductDiscountDto dto =
                (ProductDiscountDto) discountMapper.mapToDto(
                        discountMapper.mapToProductDiscount(result)
                                                            );
        dto.setFinalPrice(calculateNewPrice(result, product));
        return dto;
    }

    @Override
    public Map<Long, ProductDiscountDto> getProductsDiscounts(Collection<Product> products) {
        List<Discount> allDiscounts = discountRepository.getAllDiscounts();
        Map<Long, ProductDiscountDto> discountDtoMap = new HashMap<>();
        products.forEach(p -> discountDtoMap.put(p.getId(), getDiscountForProduct(p, allDiscounts)));
        return discountDtoMap;
    }

    private ProductDiscountDto getDiscountForProduct(Product product, List<Discount> allDiscounts){
        List<Discount> productDiscounts =
                allDiscounts
                        .stream()
                        .filter(this::isDiscountActive)
                        .filter(d -> doesDiscountApplyToProduct(d, product))
                        .collect(toList());
        Discount result = productDiscounts.stream().reduce(null, (a, b) -> reduceDiscount(a, b, product));
        if (result == null) {
            return null;
        }
        ProductDiscountDto dto =
                (ProductDiscountDto) discountMapper.mapToDto(
                        discountMapper.mapToProductDiscount(result)
                );
        dto.setFinalPrice(calculateNewPrice(result, product));
        return dto;
    }

    @Override
    public ProductDiscountDto getProductDiscount(Product product) {
        List<Discount> productDiscounts =
                discountRepository.getAllDiscounts()
                        .stream()
                        .filter(this::isDiscountActive)
                        .filter(d -> doesDiscountApplyToProduct(d, product))
                        .collect(toList());
        Discount result = productDiscounts.stream().reduce(null, (a, b) -> reduceDiscount(a, b, product));
        if (result == null) {
            return null;
        }
        ProductDiscountDto dto =
                (ProductDiscountDto) discountMapper.mapToDto(
                        discountMapper.mapToProductDiscount(result)
                );
        dto.setFinalPrice(calculateNewPrice(result, product));
        return dto;
    }

    private boolean isDiscountActive(Discount discount) {
        Instant currentTime = Instant.now();
        return currentTime.isAfter(discount.getActiveFrom().toInstant()) &&
               currentTime.isBefore(discount.getActiveTo().toInstant());
    }

    private boolean doesDiscountApplyToProduct(Discount d, Product product) {
        if (d instanceof ProductDiscount) {
            Product target = ((ProductDiscount) d).getTarget();
            return target != null && target.getId().equals(product.getId());
        }
        if (d instanceof CategoryDiscount) {
            Category target = ((CategoryDiscount) d).getTarget();
            return target != null && productBelongsToCategory(product, target);
        }
        return true;
    }

    private boolean productBelongsToCategory(Product product, Category category) {
        Category current = product.getCategory();
        while (current != null) {
            if (current.getId().equals(category.getId())) {
                return true;
            }
            current = current.getParentCategory();
        }
        return false;
    }

    private Discount reduceDiscount(Discount first, Discount second, Product product) {
        if (first == null) return second;
        if (second == null) return first;
        BigDecimal firstPrice = calculateNewPrice(first, product);
        BigDecimal secondPrice = calculateNewPrice(second, product);
        if (firstPrice.compareTo(secondPrice) < 0) {
            return first;
        }
        return second;
    }

    private BigDecimal calculateNewPrice(Discount discount, Product product) {
        BigDecimal priceDelta;
        if (discount.getType() == DiscountType.ABSOLUTE) {
            priceDelta = discount.getValue();
        } else {
            priceDelta = discount.getValue()
                                 .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                                 .multiply(product.getPrice())
                                 .setScale(2, RoundingMode.HALF_UP);
        }
        BigDecimal newPrice = product.getPrice().subtract(priceDelta);
        // TODO: how to handle negative prices?
        newPrice = newPrice.max(BigDecimal.ZERO);
        return newPrice;
    }

    private void validateTarget(DiscountDto dto) {
        if (dto.getTargetType() == DiscountTarget.EVERYTHING) return;
        if (dto.getTargetType() == DiscountTarget.PRODUCT) {
            Product product = productRepository.getProduct(dto.getTargetId());
            if (product == null) {
                throw new ProductNotFoundException(dto.getTargetId());
            }
        } else {
            Category category = categoryRepository.getCategory(dto.getTargetId());
            if (category == null) {
                throw new CategoryNotFoundException(dto.getTargetId());
            }
        }
    }
}
