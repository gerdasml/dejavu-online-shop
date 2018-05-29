package lt.dejavu.product.service;

import lt.dejavu.product.dto.ProductDto;
import lt.dejavu.product.dto.discount.DiscountDto;
import lt.dejavu.product.dto.discount.ProductDiscountDto;
import lt.dejavu.product.model.Product;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface DiscountService {
    List<DiscountDto> getAllDiscounts();

    DiscountDto getDiscountById(long id);

    long addDiscount(DiscountDto discount);

    List<Long> addDiscounts(List<DiscountDto> discounts);

    void updateDiscount(long id, DiscountDto newDiscount);

    void deleteDiscount(long id);

    ProductDiscountDto getProductDiscount(long productId);

    ProductDiscountDto getProductDiscount(Product product);

    Map<Long, ProductDiscountDto> getProductsDiscounts(Collection<Product> products);

    ProductDto attachDiscount(Product product);

    List<ProductDto> attachDiscount(Collection<Product> products);

}
