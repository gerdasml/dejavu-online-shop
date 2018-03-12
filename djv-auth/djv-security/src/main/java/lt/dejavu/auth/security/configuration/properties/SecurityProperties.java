package lt.dejavu.auth.security.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import lt.dejavu.auth.model.Endpoint;
import lt.dejavu.auth.model.UserType;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "auth")
@Getter
@Setter
public class SecurityProperties {
    private String secret;
    private Map<UserType, List<Endpoint>> rights;
}
