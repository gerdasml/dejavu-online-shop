package lt.dejavu.auth.configuration;

import org.skife.jdbi.v2.DBI;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class AuthConfiguration {
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public DBI dbi(DataSource dataSource) {
        DBI dbi = new DBI(dataSource);
        // register mappers here
        return dbi;
    }
}
