package lt.dejavu.discount.repository;

import lt.dejavu.discount.model.db.Discount;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class DiscountRepositoryImpl implements DiscountRepository {
    @PersistenceContext
    private EntityManager em;
    @Override
    public long addDiscount(Discount discount) {
        em.persist(discount);
        return discount.getId();
    }
}
