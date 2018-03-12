package lt.dejavu.product.service;

import lt.dejavu.product.model.Product;
import lt.dejavu.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public Product getProduct(long id) {
        return productRepository.getProduct(id);
    }
}
