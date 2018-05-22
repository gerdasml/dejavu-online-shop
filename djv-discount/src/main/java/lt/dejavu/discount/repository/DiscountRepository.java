package lt.dejavu.discount.repository;

import lt.dejavu.discount.model.db.Discount;
import lt.dejavu.discount.model.db.ProductDiscount;

import java.util.List;

public interface DiscountRepository {
    long addDiscount(Discount discount);

    Discount getDiscount(long id);

    void updateDiscount(long id, Discount newDiscount);

    List<Discount> getAllDiscounts();

    void deleteDiscount(long id);

    List<ProductDiscount> getProductDiscounts(long productId);
}
