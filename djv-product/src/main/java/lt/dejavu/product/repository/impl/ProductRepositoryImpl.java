package lt.dejavu.product.repository.impl;

import lt.dejavu.product.model.*;
import lt.dejavu.product.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ProductRepositoryImpl implements ProductRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Product> getAllProducts() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> productRoot = cq.from(Product.class);
        productRoot.join(Product_.propertyValues, JoinType.LEFT).join(ProductPropertyValue_.productProperty, JoinType.LEFT);
        CriteriaQuery<Product> all = cq.select(productRoot);
        return em.createQuery(all).getResultList();
    }

    @Override
    public Product getProduct(long id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);
        root.fetch(Product_.propertyValues, JoinType.LEFT).fetch(ProductPropertyValue_.productProperty, JoinType.LEFT);
        ParameterExpression<Long> idParameter = cb.parameter(Long.class);
        query.where(cb.equal(root.get(Product_.id), idParameter));
        List<Product> resultList = em.createQuery(query).setParameter(idParameter, id).getResultList();
        return resultList.size() != 0 ? resultList.get(0) : null;

    }

    @Override
    public List<Product> getProductsByCategory(long categoryId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);
        root.join(Product_.propertyValues, JoinType.LEFT).join(ProductPropertyValue_.productProperty, JoinType.LEFT);
        ParameterExpression<Long> categoryIdParameter = cb.parameter(Long.class);
        query.where(cb.equal(root.get(Product_.category).get(Category_.id), categoryIdParameter));
        return em.createQuery(query).setParameter(categoryIdParameter, categoryId).getResultList();
    }

    @Override
    public long saveProduct(Product product) {
        em.persist(product);
        return product.getId();
    }

    @Override
    public void deleteProduct(Product product) {
        em.remove(product);
    }

    @Override
    public void updateProduct(Product product) {
        em.merge(product);
    }
}
