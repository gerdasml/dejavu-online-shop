package lt.dejavu.product.repository.impl;

import lt.dejavu.product.model.Category_;
import lt.dejavu.product.model.Product;
import lt.dejavu.product.model.Product_;
import lt.dejavu.product.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ProductRepositoryImpl implements ProductRepository  {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public Product getProduct(long id) {
        return em.find(Product.class, id);
    }

    @Override
    public List<Product> getProductsByCategory(long categoryId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> query =  cb.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);
        ParameterExpression<Long> categoryIdParameter = cb.parameter(Long.class);
        query.where(cb.equal(root.get(Product_.category).get(Category_.id), categoryIdParameter));
        return em.createQuery(query).getResultList();
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
