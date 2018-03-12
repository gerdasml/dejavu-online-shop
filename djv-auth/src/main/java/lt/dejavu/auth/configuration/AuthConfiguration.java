package lt.dejavu.auth.configuration;

import lt.dejavu.auth.db.dao.UserDAO;
import lt.dejavu.auth.db.dao.UserTokenDAO;
import lt.dejavu.auth.db.mapper.UserMapper;
import lt.dejavu.auth.helpers.Hasher;
import lt.dejavu.auth.helpers.Sha256Hasher;
import org.killbill.commons.jdbi.mapper.UUIDMapper;
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
        dbi.registerMapper(new UserMapper());
        dbi.registerMapper(new UUIDMapper());
        return dbi;
    }

    @Bean
    public UserDAO userDAO(DBI dbi) {
        return dbi.onDemand(UserDAO.class);
    }

    @Bean
    public UserTokenDAO userTokenDAO(DBI dbi) {
        return dbi.onDemand(UserTokenDAO.class);
    }

    @Bean
    public Hasher hasher() {
        return new Sha256Hasher();
    }
}
