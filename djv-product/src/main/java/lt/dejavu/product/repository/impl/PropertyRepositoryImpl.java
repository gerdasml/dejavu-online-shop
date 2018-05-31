package lt.dejavu.product.repository.impl;

import lt.dejavu.product.model.*;
import lt.dejavu.product.repository.PropertyRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public class PropertyRepositoryImpl implements PropertyRepository {
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
    public List<CategoryProperty> getCategoryProperties(long categoryId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CategoryProperty> query = cb.createQuery(CategoryProperty.class);
        Root<CategoryProperty> root = query.from(CategoryProperty.class);
        ParameterExpression<Long> idParameter = cb.parameter(Long.class);
        query.select(root);
        Subquery<Long> subQuery = query.subquery(Long.class);
        Root<Category> subRoot = subQuery.from(Category.class);
        subQuery.select(subRoot.get(Category_.parentCategory).get(Category_.id));
        subQuery.where(cb.equal(subRoot.get(Category_.id), idParameter));

        query.where(cb.or(
                cb.equal(root.get(CategoryProperty_.category).get(Category_.id), idParameter),
                cb.equal(root.get(CategoryProperty_.category).get(Category_.id), subQuery)
                ));

        return em.createQuery(query).setParameter(idParameter, categoryId).getResultList();
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
