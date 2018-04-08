package lt.dejavu.test.common;

import lt.dejavu.product.repository.CategoryRepository;
import lt.dejavu.product.repository.ProductRepository;
import lt.dejavu.product.repository.impl.CategoryRepositoryImpl;
import lt.dejavu.product.repository.impl.ProductRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryTestConfiguration {


    @Bean
    public CategoryRepository categoryRepository() {
        return new CategoryRepositoryImpl();
    }

    @Bean
    public ProductRepository productRepository(){
        return new ProductRepositoryImpl();
    }

}


