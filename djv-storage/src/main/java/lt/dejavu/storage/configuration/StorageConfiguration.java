package lt.dejavu.storage.configuration;

import lt.dejavu.storage.configuration.properties.StorageProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({StorageProperties.class})
public class StorageConfiguration {
    // TODO: beans
}
