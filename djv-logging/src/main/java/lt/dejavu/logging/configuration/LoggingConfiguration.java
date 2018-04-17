package lt.dejavu.logging.configuration;

import lt.dejavu.logging.RequestLoggingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

@Configuration
public class RequestLoggingFilterConfiguration {
    @Bean
    AbstractRequestLoggingFilter loggingFilter() {
        return new RequestLoggingFilter();
    }
}
