package lt.dejavu.auth.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import lt.dejavu.auth.model.UserType;
import lt.dejavu.auth.model.token.Endpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "auth")
@Getter
@Setter
public class AuthProperties {
    private String secret;
    private Map<UserType, List<Endpoint>> rights;
}
