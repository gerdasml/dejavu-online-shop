package lt.dejavu.product.service;

import lt.dejavu.product.dto.discount.DiscountDto;
import lt.dejavu.product.dto.discount.ProductDiscountDto;

import java.util.List;

public interface DiscountService {
    List<DiscountDto> getAllDiscounts();

    DiscountDto getDiscountById(long id);

    long addDiscount(DiscountDto discount);

    void updateDiscount(long id, DiscountDto newDiscount);

    void deleteDiscount(long id);

    ProductDiscountDto getProductDiscount(long productId);
}
