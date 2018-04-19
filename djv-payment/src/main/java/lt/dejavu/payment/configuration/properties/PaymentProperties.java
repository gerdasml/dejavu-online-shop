package lt.dejavu.payment.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "payment")
@Getter
@Setter
public class PaymentProperties {
    private String paymentServiceAddress;
    private CredentialsProperties credentials;
}
