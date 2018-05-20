package lt.dejavu.product.repository.impl;

import lt.dejavu.product.model.*;
import lt.dejavu.product.repository.ProductRepository;
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
public class ProductRepositoryImpl implements ProductRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Set<Product> getAllProducts() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> productRoot = cq.from(Product.class);
        productRoot.fetch(Product_.properties, JoinType.LEFT).fetch(ProductProperty_.categoryProperty, JoinType.LEFT);
        productRoot.fetch(Product_.additionalImagesUrls, JoinType.LEFT);
        CriteriaQuery<Product> all = cq.select(productRoot);
        return new LinkedHashSet<>(em.createQuery(all).getResultList());
    }

    @Override
    public Product getProduct(long id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);
        root.fetch(Product_.properties, JoinType.LEFT).fetch(ProductProperty_.categoryProperty, JoinType.LEFT);
        root.fetch(Product_.additionalImagesUrls, JoinType.LEFT);
        ParameterExpression<Long> idParameter = cb.parameter(Long.class);
        query.where(cb.equal(root.get(Product_.id), idParameter));
        List<Product> resultList = em.createQuery(query).setParameter(idParameter, id).getResultList();
        return resultList.size() != 0 ? resultList.get(0) : null;

    }

    @Override
    public Product getProduct(String identifier) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);
        root.fetch(Product_.properties, JoinType.LEFT).fetch(ProductProperty_.categoryProperty, JoinType.LEFT);
        root.fetch(Product_.additionalImagesUrls, JoinType.LEFT);
        ParameterExpression<String> idParameter = cb.parameter(String.class);
        query.where(cb.equal(root.get(Product_.identifier), idParameter));
        List<Product> resultList = em.createQuery(query).setParameter(idParameter, identifier).getResultList();
        return resultList.size() != 0 ? resultList.get(0) : null;
    }

    @Override
    public Set<Product> getProductsByCategory(long categoryId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);
        root.fetch(Product_.properties, JoinType.LEFT).fetch(ProductProperty_.categoryProperty, JoinType.LEFT);
        root.fetch(Product_.additionalImagesUrls, JoinType.LEFT);
        ParameterExpression<Long> categoryIdParameter = cb.parameter(Long.class);
        query.where(cb.equal(root.get(Product_.category).get(Category_.id), categoryIdParameter));
        return new LinkedHashSet<>(em.createQuery(query).setParameter(categoryIdParameter, categoryId).getResultList());
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
