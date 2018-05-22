package lt.dejavu.discount.service;

import lt.dejavu.discount.exception.DiscountNotFoundException;
import lt.dejavu.discount.model.db.ProductDiscount;
import lt.dejavu.product.model.DiscountTarget;
import lt.dejavu.discount.model.db.Discount;
import lt.dejavu.product.dto.discount.DiscountDto;
import lt.dejavu.product.dto.discount.ProductDiscountDto;
import lt.dejavu.discount.model.mapper.DiscountMapper;
import lt.dejavu.discount.repository.DiscountRepository;
import lt.dejavu.product.model.DiscountType;
import lt.dejavu.product.service.CategoryService;
import lt.dejavu.product.service.DiscountService;
import lt.dejavu.product.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;
    private final DiscountMapper discountMapper;
    private final ProductService productService;
    private final CategoryService categoryService;

    public DiscountServiceImpl(DiscountRepository discountRepository, DiscountMapper discountMapper, ProductService productService, CategoryService categoryService) {
        this.discountRepository = discountRepository;
        this.discountMapper = discountMapper;
        this.productService = productService;
        this.categoryService = categoryService;
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
    public void addDiscount(DiscountDto discount) {
        validateTarget(discount);
        discountRepository.addDiscount(discountMapper.mapToDiscount(discount));
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
        List<ProductDiscount> productDiscounts = discountRepository.getProductDiscounts(productId);
        ProductDiscount result = productDiscounts.stream().reduce(null, this::reduceDiscount);
        ProductDiscountDto dto = (ProductDiscountDto) discountMapper.mapToDto(result);
        dto.setFinalPrice(calculateNewPrice(result));
        return dto;
    }

    private ProductDiscount reduceDiscount(ProductDiscount first, ProductDiscount second) {
        if (first == null) return second;
        if (second == null) return first;
        BigDecimal firstPrice = calculateNewPrice(first);
        BigDecimal secondPrice = calculateNewPrice(second);
        if (firstPrice.compareTo(secondPrice) < 0) {
            return first;
        }
        return second;
    }

    private BigDecimal calculateNewPrice(ProductDiscount discount) {
        BigDecimal priceDelta;
        if ( discount.getType() == DiscountType.ABSOLUTE) {
            priceDelta = discount.getValue();
        } else {
            priceDelta = discount.getValue()
                                 .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                                 .multiply(discount.getTarget().getPrice());
        }
        BigDecimal newPrice = discount.getTarget().getPrice().subtract(priceDelta);
        // TODO: how to handle negative prices?
        newPrice = newPrice.max(BigDecimal.ZERO);
        return newPrice;
    }

    private void validateTarget(DiscountDto dto) {
        if (dto.getTargetType() == DiscountTarget.EVERYTHING) return;
        if (dto.getTargetType() == DiscountTarget.PRODUCT) {
            productService.getProduct(dto.getTargetId());
        } else {
            categoryService.getCategory(dto.getTargetId());
        }
    }
}
