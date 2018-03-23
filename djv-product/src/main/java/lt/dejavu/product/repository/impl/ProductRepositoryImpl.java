package lt.dejavu.product.repository.impl;

import lt.dejavu.product.model.Product;
import lt.dejavu.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ProductRepositoryImpl implements ProductRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Product getProduct(long id) {
        return em.find(Product.class, id);
    }

    @Override
    public List<Product> getProductsByCategory(int categoryId) {
        //TODO
        return null;
    }

    @Override
    public Long createProduct(Product product) {
        em.persist(product);
        return product.getId();
    }
}
