package lt.dejavu.product.repository.impl;

import lt.dejavu.product.model.ProductProperty;
import lt.dejavu.product.model.ProductPropertyValue;
import lt.dejavu.product.model.ProductProperty_;
import lt.dejavu.product.repository.ProductPropertyRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.LinkedHashSet;
import java.util.Set;

@Repository
@Transactional
public class ProductPropertyRepositoryImpl implements ProductPropertyRepository {
    @PersistenceContext
    private EntityManager em;


    @Override
    public Set<ProductProperty> findByIds(Set<Long> ids) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ProductProperty> cq = cb.createQuery(ProductProperty.class);
        Root<ProductProperty> root = cq.from(ProductProperty.class);
        CriteriaQuery<ProductProperty> query = cq.select(root).where(root.get(ProductProperty_.id).in(ids));
        return new LinkedHashSet<>(em.createQuery(query).getResultList());
    }

    @Override
    public void saveProperties(Set<ProductProperty> properties) {
        properties.forEach(em::persist);
    }

    @Override
    public void savePropertyValues(Set<ProductPropertyValue> productPropertyValues) {
        productPropertyValues.forEach(em::persist);
    }
}
