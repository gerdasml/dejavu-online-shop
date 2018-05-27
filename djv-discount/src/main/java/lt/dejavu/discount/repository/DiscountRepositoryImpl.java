package lt.dejavu.discount.repository;

import lt.dejavu.discount.model.db.Discount;
import lt.dejavu.discount.model.db.ProductDiscount;
import lt.dejavu.discount.model.db.ProductDiscount_;
import lt.dejavu.product.model.Product_;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

import static java.util.stream.Collectors.toList;

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
    public List<Long> addDiscounts(List<Discount> discounts) {
        discounts.forEach(em::persist);
        return discounts.stream().map(Discount::getId).collect(toList());
    }

    @Override
    public Discount getDiscount(long id) {
        return em.find(Discount.class, id);
    }

    @Override
    public void updateDiscount(long id, Discount newDiscount) {
        Discount oldDiscount = getDiscount(id);
        if (oldDiscount == null) return;
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
        if (discount == null) return;
        em.remove(discount);
    }

    @Override
    public List<ProductDiscount> getProductDiscounts(long productId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ProductDiscount> query = cb.createQuery(ProductDiscount.class);
        Root<ProductDiscount> root = query.from(ProductDiscount.class);
        Predicate condition = cb.equal(root.get(ProductDiscount_.target).get(Product_.id), productId);
        query.where(condition);
        TypedQuery<ProductDiscount> q = em.createQuery(query);

        return q.getResultList();
    }
}
