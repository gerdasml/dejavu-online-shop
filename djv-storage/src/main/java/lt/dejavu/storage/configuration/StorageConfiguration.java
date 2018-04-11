package lt.dejavu.storage.configuration;

import lt.dejavu.storage.configuration.properties.StorageProperties;
import lt.dejavu.storage.strategy.FileSystemStorageStrategy;
import lt.dejavu.storage.strategy.StorageStrategy;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({StorageProperties.class})
public class StorageConfiguration {
    @Bean
    StorageStrategy storageStrategy(StorageProperties properties) {
        return new FileSystemStorageStrategy(properties.getBasePath());
    }
}
