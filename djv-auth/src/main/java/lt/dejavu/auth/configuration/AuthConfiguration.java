package lt.dejavu.auth.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.dejavu.auth.configuration.properties.AuthProperties;
import lt.dejavu.auth.db.dao.UserDAO;
import lt.dejavu.auth.db.dao.UserTokenDAO;
import lt.dejavu.auth.db.mapper.UserMapper;
import lt.dejavu.auth.helpers.Hasher;
import lt.dejavu.auth.helpers.JsonTokenCodec;
import lt.dejavu.auth.helpers.Sha256Hasher;
import lt.dejavu.auth.helpers.TokenCodec;
import lt.dejavu.auth.model.UserType;
import lt.dejavu.auth.model.token.Endpoint;
import lt.dejavu.auth.service.TokenService;
import lt.dejavu.auth.service.TokenServiceImpl;
import org.killbill.commons.jdbi.mapper.UUIDMapper;
import org.skife.jdbi.v2.DBI;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public Hasher hasher() {
        return new Sha256Hasher();
    }

    @Bean
    public TokenCodec tokenCodec(@Qualifier("defaultObjectMapper") ObjectMapper mapper) {
        return new JsonTokenCodec(mapper);
    }

    @Bean
    public TokenService tokenService(TokenCodec codec, AuthProperties props) {
        return new TokenServiceImpl(codec, authEndpointProvider(props));
    }

    @Bean
    public Map<UserType, Function<Integer, List<Endpoint>>> authEndpointProvider(AuthProperties authProperties) {
        return authProperties.getRights()
                             .entrySet()
                             .stream()
                             .collect(
                                     toMap(
                                             Map.Entry::getKey,
                                             entry -> addUserId(entry.getValue())
                                          )
                                     );
    }

    private Function<Integer, List<Endpoint>> addUserId(List<Endpoint> rights) {
        return id -> rights.stream()
                           .map(e -> addUserId(e, id))
                           .collect(toList());
    }

    private Endpoint addUserId(Endpoint e, int id) {
        Endpoint ee = new Endpoint();
        ee.setMethod(e.getMethod());
        ee.setPath(e.getPath().replace("{id}", String.valueOf(id)));
        return ee;
    }
}
