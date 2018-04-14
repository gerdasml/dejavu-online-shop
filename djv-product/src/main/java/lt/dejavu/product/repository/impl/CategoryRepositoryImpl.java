package lt.dejavu.product.repository.impl;

import lt.dejavu.product.model.Category;
import lt.dejavu.product.model.Category_;
import lt.dejavu.product.repository.CategoryRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
@Transactional
public class CategoryRepositoryImpl implements CategoryRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Category getCategory(long id) {
        return em.find(Category.class, id);
    }

    @Override
    public List<Category> getRootCategories() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Category> query =  cb.createQuery(Category.class);
        Root<Category> root = query.from(Category.class);
        query.where(cb.isNull(root.get(Category_.parentCategory)));
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Category> getSubCategories(long parentId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Category> query =  cb.createQuery(Category.class);
        Root<Category> root = query.from(Category.class);
        Join<Category,Category> parentJoin = root.join(Category_.parentCategory);
        query.where(cb.equal(parentJoin.get(Category_.id), parentId));
        return em.createQuery(query).getResultList();
    }

    @Override
    public long saveCategory(Category category) {
        em.persist(category);
        return category.getId();
    }

}
