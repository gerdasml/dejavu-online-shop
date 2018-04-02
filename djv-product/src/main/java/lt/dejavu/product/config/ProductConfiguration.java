package lt.dejavu.product.config;

import lt.dejavu.product.repository.ProductRepository;
import lt.dejavu.product.repository.impl.ProductRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class ProductConfiguration {

    @Autowired
    EntityManager em;

    @Bean
    public ProductRepository productRepository(){
        return new ProductRepositoryImpl(em);
    }
}
