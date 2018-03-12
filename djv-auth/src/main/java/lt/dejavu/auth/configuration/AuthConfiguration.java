package lt.dejavu.auth.configuration;

import lt.dejavu.auth.configuration.properties.AuthProperties;
import lt.dejavu.auth.db.dao.UserDAO;
import lt.dejavu.auth.db.dao.UserTokenDAO;
import lt.dejavu.auth.db.mapper.UserMapper;
import lt.dejavu.auth.codec.AuthHeaderCodec;
import lt.dejavu.auth.codec.SignedTokenCodec;
import lt.dejavu.auth.codec.TokenCodec;
import lt.dejavu.auth.model.UserType;
import lt.dejavu.auth.model.token.Endpoint;
import lt.dejavu.auth.service.*;
import org.killbill.commons.jdbi.mapper.UUIDMapper;
import org.skife.jdbi.v2.DBI;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Configuration
@EnableConfigurationProperties({AuthProperties.class})
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
    public SignatureService signatureService(AuthProperties properties) {
        return new SignatureServiceImpl(properties.getSecret());
    }

    @Bean
    public AccessibilityService accessibilityService(AuthProperties properties) {
        return new AccessibilityServiceImpl(properties);
    }
}
