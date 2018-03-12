package lt.dejavu.auth.security.configuration;

import lt.dejavu.auth.security.configuration.properties.SecurityProperties;
import lt.dejavu.auth.security.service.AccessibilityService;
import lt.dejavu.auth.security.service.AccessibilityServiceImpl;
import lt.dejavu.auth.security.service.SignatureService;
import lt.dejavu.auth.security.service.SignatureServiceImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({SecurityProperties.class})
public class SecurityConfiguration {
    @Bean
    public SignatureService signatureService(SecurityProperties properties) {
        return new SignatureServiceImpl(properties.getSecret());
    }

    @Bean
    public AccessibilityService accessibilityService(SecurityProperties properties) {
        return new AccessibilityServiceImpl(properties);
    }
}
