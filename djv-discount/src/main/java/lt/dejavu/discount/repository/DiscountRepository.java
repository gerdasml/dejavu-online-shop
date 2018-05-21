package lt.dejavu.discount.repository;

import lt.dejavu.discount.model.db.Discount;

public interface DiscountRepository {
    long addDiscount(Discount discount);
}
