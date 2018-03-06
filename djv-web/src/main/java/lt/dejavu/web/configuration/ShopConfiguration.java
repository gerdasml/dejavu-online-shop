package lt.dejavu.web.configuration;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class ShopConfiguration {
    // Example dataSource configuration
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public String test(DataSource ds) {
        int x = 5;
        return "lol";
    }
}
