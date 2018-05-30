package lt.dejavu.product.repository.impl;

import lt.dejavu.product.model.*;
import lt.dejavu.product.repository.ProductPropertyRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.LinkedHashSet;
import java.util.Set;

@Repository
@Transactional
public class ProductPropertyRepositoryImpl implements ProductPropertyRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Set<CategoryProperty> findByIds(Set<Long> ids) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CategoryProperty> cq = cb.createQuery(CategoryProperty.class);
        Root<CategoryProperty> root = cq.from(CategoryProperty.class);
        CriteriaQuery<CategoryProperty> query = cq.select(root).where(root.get(CategoryProperty_.id).in(ids));
        return new LinkedHashSet<>(em.createQuery(query).getResultList());
    }

    @Override
    public void saveProperties(Set<CategoryProperty> properties) {
        properties.forEach(em::persist);
    }

    @Override
    public void savePropertyValues(Set<ProductProperty> productProperties) {
        productProperties.forEach(em::persist);
    }
}
