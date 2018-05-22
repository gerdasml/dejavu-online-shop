package lt.dejavu.product.repository.impl;

import lt.dejavu.product.model.*;
import lt.dejavu.product.repository.CategoryRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public class CategoryRepositoryImpl implements CategoryRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Category getCategory(long id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Category> query = cb.createQuery(Category.class);
        Root<Category> root = query.from(Category.class);
        root.fetch(Category_.properties, JoinType.LEFT);
        ParameterExpression<Long> idParameter = cb.parameter(Long.class);
        query.where(cb.equal(root.get(Category_.id), idParameter));
        List<Category> resultList = em.createQuery(query).setParameter(idParameter, id).getResultList();
        return resultList.size() != 0 ? resultList.get(0) : null;
    }

    @Override
    public Category getCategory(String identifier) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Category> query = cb.createQuery(Category.class);
        Root<Category> root = query.from(Category.class);
        root.fetch(Category_.properties, JoinType.LEFT);
        ParameterExpression<String> idParameter = cb.parameter(String.class);
        query.where(cb.equal(root.get(Category_.identifier), idParameter));
        List<Category> resultList = em.createQuery(query).setParameter(idParameter, identifier).getResultList();
        return resultList.size() != 0 ? resultList.get(0) : null;
    }

    @Override
    public Set<Category> getRootCategories() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Category> query = cb.createQuery(Category.class);
        Root<Category> root = query.from(Category.class);
        root.fetch(Category_.properties, JoinType.LEFT);
        query.where(cb.isNull(root.get(Category_.parentCategory)));
        return new LinkedHashSet<>(em.createQuery(query).getResultList());
    }

    @Override
    public Set<Category> getSubCategories(long parentId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Category> query = cb.createQuery(Category.class);
        Root<Category> root = query.from(Category.class);
        Join<Category, Category> parentJoin = root.join(Category_.parentCategory);
        root.fetch(Category_.properties, JoinType.LEFT);
        query.where(cb.equal(parentJoin.get(Category_.id), parentId));
        return new LinkedHashSet<>(em.createQuery(query).getResultList());
    }

    @Override
    public long saveCategory(Category category) {
        em.persist(category);
        return category.getId();
    }

    @Override
    public void updateCategory(Category category) {
        em.merge(category);
    }

    @Override
    public void deleteCategory(Category category) {
        em.remove(category);
    }

    @Override
    public Set<Category> getAllCategories() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Category> query = cb.createQuery(Category.class);
        Root<Category> root = query.from(Category.class);
        root.fetch(Category_.properties, JoinType.LEFT);
        root.fetch(Category_.parentCategory, JoinType.LEFT);
        return new LinkedHashSet<>(em.createQuery(query).getResultList());

    }

    @Override
    public List<ProductProperty> getProductPropertiesForCategory(long categoryId) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ProductProperty> query = cb.createQuery(ProductProperty.class);
        Root<ProductProperty> root = query.from(ProductProperty.class);

        ParameterExpression<Long> idParameter = cb.parameter(Long.class);
        query.where(cb.equal(root.get(ProductProperty_.categoryProperty).get(CategoryProperty_.category).get(Category_.id), idParameter));

        return em.createQuery(query).setParameter(idParameter, categoryId).getResultList();
    }

    @Override
    public long getProductCount(long categoryId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Product> root = countQuery.from(Product.class);
        countQuery.select(cb.count(root));
        ParameterExpression<Long> idParameter = cb.parameter(Long.class);
        countQuery.where(cb.equal(root.get(Product_.category).get(Category_.id), idParameter));

        return em.createQuery(countQuery).setParameter(idParameter, categoryId).getSingleResult();
    }

    @Override
    public BigDecimal getMinimumProductPrice(long categoryId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> query = cb.createQuery(BigDecimal.class);
        Root<Product> root = query.from(Product.class);
        query.select(cb.min(root.get(Product_.price)));
        ParameterExpression<Long> idParameter = cb.parameter(Long.class);
        query.where(cb.equal(root.get(Product_.category).get(Category_.id), idParameter));

        return em.createQuery(query).setParameter(idParameter, categoryId).getSingleResult();
    }

    @Override
    public BigDecimal getMaximumProductPrice(long categoryId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> query = cb.createQuery(BigDecimal.class);
        Root<Product> root = query.from(Product.class);
        query.select(cb.max(root.get(Product_.price)));
        ParameterExpression<Long> idParameter = cb.parameter(Long.class);
        query.where(cb.equal(root.get(Product_.category).get(Category_.id), idParameter));

        return em.createQuery(query).setParameter(idParameter, categoryId).getSingleResult();
    }
}
