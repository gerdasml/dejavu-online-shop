package lt.dejavu.product.repository.impl;

import lt.dejavu.product.model.Category;
import lt.dejavu.product.model.Category_;
import lt.dejavu.product.repository.CategoryRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public class  CategoryRepositoryImpl implements CategoryRepository {

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
}
