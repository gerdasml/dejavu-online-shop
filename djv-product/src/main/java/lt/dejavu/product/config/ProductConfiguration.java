package lt.dejavu.product.config;

import lt.dejavu.product.repository.ProductRepository;
import lt.dejavu.product.repository.impl.ProductRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductConfiguration {

    @Bean
    public ProductRepository productRepository(){
        return new ProductRepositoryImpl();
    }
}
