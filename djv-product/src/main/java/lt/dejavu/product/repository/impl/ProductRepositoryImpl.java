package lt.dejavu.product.repository.impl;

import lt.dejavu.product.model.*;
import lt.dejavu.product.model.rest.request.ProductSearchRequest;
import lt.dejavu.product.repository.ProductRepository;
import lt.dejavu.product.strategy.IdentifierGenerator;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public class ProductRepositoryImpl implements ProductRepository {

    private final IdentifierGenerator<Product> identifierGenerator;

    @PersistenceContext
    private EntityManager em;

    public ProductRepositoryImpl(IdentifierGenerator<Product> identifierGenerator) {
        this.identifierGenerator = identifierGenerator;
    }

    @Override
    public Set<Product> getAllProducts(int offset, int limit, SortBy sortBy, SortDirection sortDirection) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> productRoot = cq.from(Product.class);
        productRoot.fetch(Product_.properties, JoinType.LEFT).fetch(ProductProperty_.categoryProperty, JoinType.LEFT);
        productRoot.fetch(Product_.additionalImagesUrls, JoinType.LEFT);
        CriteriaQuery<Product> all = cq.select(productRoot);

        if (sortBy != null && sortDirection != null) {
            Path<?> path = null;
            if (sortBy == SortBy.CREATION_DATE) {
                path = productRoot.get(Product_.creationDate);
            } else if(sortBy == SortBy.PRICE) {
                path = productRoot.get(Product_.price);
            }
            if (sortDirection == SortDirection.ASC) {
                all.orderBy(cb.asc(path));
            } else {
                all.orderBy(cb.desc(path));
            }
        }
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
    public Set<Product> getProductsByCategory(long categoryId, int offset, int limit, SortBy sortBy, SortDirection sortDirection) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);
        root.fetch(Product_.properties, JoinType.LEFT).fetch(ProductProperty_.categoryProperty, JoinType.LEFT);
        root.fetch(Product_.additionalImagesUrls, JoinType.LEFT);
        root.fetch(Product_.category, JoinType.LEFT).fetch(Category_.parentCategory, JoinType.LEFT);
        ParameterExpression<Long> categoryIdParameter = cb.parameter(Long.class);
        query.where(cb.equal(root.get(Product_.category).get(Category_.id), categoryIdParameter));
        if (sortBy != null && sortDirection != null) {
            Path<?> path = null;
            if (sortBy == SortBy.CREATION_DATE) {
                path = root.get(Product_.creationDate);
            } else if (sortBy == SortBy.PRICE) {
                path = root.get(Product_.price);
            }
            if (sortDirection == SortDirection.ASC) {
                query.orderBy(cb.asc(path));
            } else {
                query.orderBy(cb.desc(path));
            }
        }
        TypedQuery<Product> q = em.createQuery(query).setParameter(categoryIdParameter, categoryId);
        q.setFirstResult(offset);
        q.setMaxResults(limit);
        return new LinkedHashSet<>(q.getResultList());
    }

    @Override
    public long saveProduct(Product product) {

        em.persist(product);
        product.setIdentifier(identifierGenerator.generateIdentifier(product));
        em.flush();
        return product.getId();
    }

    @Override
    public void deleteProduct(Product product) {
        em.remove(product);
    }

    @Override
    public void updateProduct(Product product) {
        product.setIdentifier(identifierGenerator.generateIdentifier(product));
        em.merge(product);
    }

    @Override
    public void reassignProductsToParent(Category oldCategory) {
        Category parent = oldCategory.getParentCategory();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<Product> updateQuery = cb.createCriteriaUpdate(Product.class);
        Root<Product> productRoot = updateQuery.from(Product.class);
        updateQuery.set(Product_.category, parent);
        updateQuery.where(cb.equal(productRoot.get(Product_.category), oldCategory));
        em.createQuery(updateQuery).executeUpdate();
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
    public SearchResult<Product> searchForProducts(ProductSearchRequest request,
                                                   int offset,
                                                   int limit,
                                                   SortBy sortBy,
                                                   SortDirection sortDirection) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);
        query.where(buildPredicate(cb, root, request));

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Product> countRoot = countQuery.from(Product.class);
        countQuery.select(cb.count(countRoot));
        countQuery.where(buildPredicate(cb, countRoot, request));

        if (sortBy != null && sortDirection != null) {
            Path<?> path = null;
            if (sortBy == SortBy.CREATION_DATE) {
                path = root.get(Product_.creationDate);
            } else if (sortBy == SortBy.PRICE) {
                path = root.get(Product_.price);
            }
            if (sortDirection == SortDirection.ASC) {
                query.orderBy(cb.asc(path));
            } else {
                query.orderBy(cb.desc(path));
            }
        }

        TypedQuery<Product> q = em.createQuery(query);
        q.setFirstResult(offset);
        q.setMaxResults(limit);

        SearchResult<Product> result = new SearchResult<>();
        result.setResults(new LinkedHashSet<>(q.getResultList()));
        result.setTotal(em.createQuery(countQuery).getSingleResult());

        return result;
    }

    private Predicate buildPredicate(CriteriaBuilder cb, Root<Product> root, ProductSearchRequest request) {
        List<Predicate> predicates = new ArrayList<>();
        if (request.getCategoryIdentifier() != null) {
            Join<Product, Category> join = root.join(Product_.category, JoinType.LEFT);
            predicates.add(cb.equal(
                    join.get(Category_.identifier),
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
        if (request.getName() != null && !request.getName().isEmpty()) {
            predicates.add(cb.like(
                    cb.lower(root.get(Product_.name)),
                    "%" + request.getName().toLowerCase() + "%"
                                  ));
        }

        if (request.getProperties() != null && request.getProperties().size() > 0) {
            SetJoin<Product, ProductProperty> join = root.join(Product_.properties);
            predicates.add(cb.or(request.getProperties()
                                        .stream()
                                        .map(prop -> cb.and(
                                                cb.equal(join.get(ProductProperty_.categoryProperty).get(CategoryProperty_.id), prop.getPropertyId()),
                                                cb.equal(join.get(ProductProperty_.value), prop.getValue())
                                                           )).toArray(Predicate[]::new)));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }

    @Override
    public boolean productWithSkuExits(String sku) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);
        ParameterExpression<String> skuParameter = cb.parameter(String.class);
        query.where(cb.equal(root.get(Product_.skuCode), skuParameter));
        List<Product> resultList = em.createQuery(query).setParameter(skuParameter, sku).getResultList();
        return resultList.size() != 0;
    }
}
