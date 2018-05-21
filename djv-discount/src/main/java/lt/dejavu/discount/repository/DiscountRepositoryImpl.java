package lt.dejavu.discount.repository;

import lt.dejavu.auth.model.db.User;
import lt.dejavu.discount.model.db.Discount;
import lt.dejavu.discount.model.db.ProductDiscount;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

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

    @Override
    public Discount getDiscount(long id) {
        return em.find(Discount.class, id);
    }

    @Override
    public void updateDiscount(long id, Discount newDiscount) {
        Discount oldDiscount = getDiscount(id);
        newDiscount.setId(id);
        em.merge(newDiscount);
    }

    @Override
    public List<Discount> getAllDiscounts() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Discount> cq = cb.createQuery(Discount.class);
        Root<Discount> rootEntry = cq.from(Discount.class);
        CriteriaQuery<Discount> all = cq.select(rootEntry);
        return em.createQuery(all).getResultList();
    }

    @Override
    public void deleteDiscount(long id) {
        Discount discount = getDiscount(id);
        em.remove(discount);
    }
}
