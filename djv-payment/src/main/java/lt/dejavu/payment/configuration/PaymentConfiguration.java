package lt.dejavu.payment.configuration;

import lt.dejavu.payment.configuration.properties.PaymentProperties;
import lt.dejavu.payment.interceptor.LoggingRequestInterceptor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties({PaymentProperties.class})
public class PaymentConfiguration {
    @Bean
    public RestTemplate restTemplate(PaymentProperties paymentProperties) {
        return new RestTemplateBuilder()
                .basicAuthorization(
                        paymentProperties.getCredentials().getUsername(),
                        paymentProperties.getCredentials().getPassword())
                .rootUri(paymentProperties.getPaymentServiceAddress())
                .interceptors(new LoggingRequestInterceptor())
                .requestFactory(new BufferingClientHttpRequestFactory(new HttpComponentsClientHttpRequestFactory()))
                .build();
    }
}
