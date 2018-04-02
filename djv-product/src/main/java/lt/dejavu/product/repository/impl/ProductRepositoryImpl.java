package lt.dejavu.product.repository.impl;

import lt.dejavu.product.model.Product;
import lt.dejavu.product.repository.ProductRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ProductRepositoryImpl extends SimpleJpaRepository<Product, Long> implements ProductRepository  {

    public ProductRepositoryImpl(EntityManager em) {
        super(Product.class, em);
    }

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
    public Long saveProduct(Product product) {
        Product savedProduct = save(product);
        return savedProduct.getId();
    }
}
