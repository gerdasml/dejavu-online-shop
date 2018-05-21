package lt.dejavu.discount.service;

import lt.dejavu.discount.model.dto.DiscountDto;
import lt.dejavu.discount.model.dto.ProductDiscountDto;

import java.util.List;

public interface DiscountService {
    List<DiscountDto> getAllDiscounts();

    DiscountDto getDiscountById(long id);

    void addDiscount(DiscountDto discount);

    void updateDiscount(long id, DiscountDto newDiscount);

    void deleteDiscount(long id);

    ProductDiscountDto getProductDiscount(long productId);
}
