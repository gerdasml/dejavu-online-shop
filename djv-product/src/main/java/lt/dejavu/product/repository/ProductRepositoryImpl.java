package lt.dejavu.product.repository;

import lt.dejavu.product.db.ProductDAO;
import lt.dejavu.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {

    private final ProductDAO productDAO;

    @Autowired
    public ProductRepositoryImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public Product getProduct(long id) {
        return productDAO.getProduct(id);
    }

    @Override
    public List<Product> getProductsByCategory(int categoryId) {
        return null;
    }

    @Override
    public Long CreateProduct(Product id) {
        return null;
    }
}
