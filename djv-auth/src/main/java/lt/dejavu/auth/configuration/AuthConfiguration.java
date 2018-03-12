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
import lt.dejavu.auth.service.SignatureService;
import lt.dejavu.auth.service.SignatureServiceImpl;
import lt.dejavu.auth.service.TokenService;
import lt.dejavu.auth.service.TokenServiceImpl;
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
    public TokenService tokenService(TokenCodec codec, SignedTokenCodec signedTokenCodec, AuthHeaderCodec authHeaderCodec, SignatureService signatureService, AuthProperties props) {
        return new TokenServiceImpl(codec, signatureService, signedTokenCodec, authHeaderCodec, authEndpointProvider(props));
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
