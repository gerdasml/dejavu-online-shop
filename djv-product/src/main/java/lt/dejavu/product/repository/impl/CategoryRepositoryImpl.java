package lt.dejavu.product.repository.impl;

import lt.dejavu.product.model.*;
import lt.dejavu.product.repository.CategoryRepository;
import lt.dejavu.product.strategy.IdentifierGenerator;
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

    private final IdentifierGenerator<Category> categoryIdentifierGenerator;

    @PersistenceContext
    private EntityManager em;

    public CategoryRepositoryImpl(IdentifierGenerator<Category> categoryIdentifierGenerator) {
        this.categoryIdentifierGenerator = categoryIdentifierGenerator;
    }

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
        query.select(root).distinct(true);
        root.fetch(Category_.properties, JoinType.LEFT);
        Fetch<Category, Category> fetch = root.fetch(Category_.parentCategory, JoinType.LEFT);
        fetch.fetch(Category_.properties, JoinType.LEFT);
        fetch = fetch.fetch(Category_.parentCategory, JoinType.LEFT);
        fetch.fetch(Category_.properties, JoinType.LEFT);
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
        category.setIdentifier(categoryIdentifierGenerator.generateIdentifier(category));
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
        return new LinkedHashSet<>(em.createQuery(query).getResultList());
    }

    @Override
    public void reassignCategoriesToParent(Category oldCategory) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<Category> updateQuery = cb.createCriteriaUpdate(Category.class);
        Category parent = oldCategory.getParentCategory();
        Root<Category> productRoot = updateQuery.from(Category.class);
        updateQuery.set(Category_.parentCategory, parent);
        updateQuery.where(cb.equal(productRoot.get(Category_.parentCategory), oldCategory));
        em.createQuery(updateQuery).executeUpdate();
    }

    @Override
    public List<ProductProperty> getProductPropertiesForCategory(long categoryId) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ProductProperty> query = cb.createQuery(ProductProperty.class);
        Root<ProductProperty> root = query.from(ProductProperty.class);
        Join<Category, Category> parentJoin = root.join(ProductProperty_.categoryProperty).join(CategoryProperty_.category).join(Category_.parentCategory);
        Join<Category, Category> granpaJoin = parentJoin.join(Category_.parentCategory);
        ParameterExpression<Long> idParameter = cb.parameter(Long.class);

        query.where(cb.or(
                cb.equal(root.get(ProductProperty_.categoryProperty).get(CategoryProperty_.category).get(Category_.id), idParameter),
                cb.equal(parentJoin.get(Category_.id), idParameter),
                cb.equal(granpaJoin.get(Category_.id), idParameter)));
        return em.createQuery(query).setParameter(idParameter, categoryId).getResultList();
    }

//    @Override
//    public List<CategoryProperty> getCategoryProperties(long categoryId) {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<CategoryProperty> query = cb.createQuery(CategoryProperty.class);
//        Root<Category> root = query.from(Category.class);
//        Selection<CategoryProperty> selection = query.select(root.get(Category_.));
//        Join<Category, Category> parentJoin = root.join(Category_.parentCategory);
//        ParameterExpression<Long> idParameter = cb.parameter(Long.class);
//
//        query.where(cb.or(
//                cb.equal(root.get(ProductProperty_.categoryProperty).get(CategoryProperty_.category).get(Category_.id), idParameter),
//                cb.equal(parentJoin.get(Category_.id), idParameter),
//                cb.equal(granpaJoin.get(Category_.id), idParameter)));
//        return em.createQuery(query).setParameter(idParameter, categoryId).getResultList();
//    }

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
