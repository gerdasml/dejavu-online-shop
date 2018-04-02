package lt.dejavu.product.repository.impl;

import lt.dejavu.product.model.Category;
import lt.dejavu.product.model.Category_;
import lt.dejavu.product.repository.CategoryRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
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
    public List<Category> getAllParentCategories() {
         List<Category> list = new LinkedList<>();
         list.add(new Category());
         return list;
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
    public Long saveCategory(Category category) {
        em.persist(category);
        return category.getId();
    }
}
