package lt.dejavu.onlineshop;

import lt.dejavu.onlineshop.model.Product;
import lt.dejavu.onlineshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * This class is here only for demo purposes.
 * It creates a new product and saves it through the productRepo.
 */
@Component
public class DatabaseLoader implements CommandLineRunner {
    private final ProductRepository productRepository;

    @Autowired
    public DatabaseLoader(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        this.productRepository.save(new Product("Pienas", "Labai skanus ir naturalus"));
    }
}
