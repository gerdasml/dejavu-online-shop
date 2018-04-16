package lt.dejavu.payment.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
@Getter
@Setter
public class CredentialsProperties {
    private String username;
    private String password;
}
