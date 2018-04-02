package lt.dejavu.product.config;

import lt.dejavu.product.repository.CategoryRepository;
import lt.dejavu.product.repository.impl.CategoryRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryConfiguration {

    @Bean
    public CategoryRepository categoryRepository() {
        return new CategoryRepositoryImpl();
    }
}
