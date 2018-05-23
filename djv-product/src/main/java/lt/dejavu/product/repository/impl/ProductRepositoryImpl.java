package lt.dejavu.product.repository.impl;

import lt.dejavu.product.model.*;
import lt.dejavu.product.model.rest.request.ProductSearchRequest;
import lt.dejavu.product.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public class ProductRepositoryImpl implements ProductRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Set<Product> getAllProducts(int offset, int limit) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> productRoot = cq.from(Product.class);
        productRoot.fetch(Product_.properties, JoinType.LEFT).fetch(ProductProperty_.categoryProperty, JoinType.LEFT);
        productRoot.fetch(Product_.additionalImagesUrls, JoinType.LEFT);
        CriteriaQuery<Product> all = cq.select(productRoot);
        TypedQuery<Product> query = em.createQuery(all);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return new LinkedHashSet<>(query.getResultList());
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
    public Set<Product> getProductsByCategory(long categoryId, int offset, int limit) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);
        root.fetch(Product_.properties, JoinType.LEFT).fetch(ProductProperty_.categoryProperty, JoinType.LEFT);
        root.fetch(Product_.additionalImagesUrls, JoinType.LEFT);
        ParameterExpression<Long> categoryIdParameter = cb.parameter(Long.class);
        query.where(cb.equal(root.get(Product_.category).get(Category_.id), categoryIdParameter));
        TypedQuery<Product> q = em.createQuery(query).setParameter(categoryIdParameter, categoryId);
        q.setFirstResult(offset);
        q.setMaxResults(limit);
        return new LinkedHashSet<>(q.getResultList());
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

    @Override
    public long getTotalProductCount() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Product> root = countQuery.from(Product.class);
        countQuery.select(cb.count(root));

        return em.createQuery(countQuery).getSingleResult();
    }

    @Override
    public Set<Product> searchForProducts(ProductSearchRequest request,
                                          int offset,
                                          int limit) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);
        List<Predicate> predicates = new ArrayList<>();
        if (request.getCategoryIdentifier() != null) {
            predicates.add(cb.equal(
                    root.get(Product_.category).get(Category_.identifier),
                    request.getCategoryIdentifier())
                          );
        }
        if (request.getMinPrice() != null) {
            predicates.add(cb.greaterThanOrEqualTo(
                    root.get(Product_.price),
                    request.getMinPrice()
                                                  ));
        }
        if (request.getMaxPrice() != null) {
            predicates.add(cb.lessThanOrEqualTo(
                    root.get(Product_.price),
                    request.getMaxPrice()
                                                  ));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));
        TypedQuery<Product> q = em.createQuery(query);
        q.setFirstResult(offset);
        q.setMaxResults(limit);

        return new LinkedHashSet<>(q.getResultList());
    }
}
