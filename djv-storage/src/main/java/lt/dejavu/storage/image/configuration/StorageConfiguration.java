package lt.dejavu.storage.image.configuration;

import lt.dejavu.storage.image.configuration.properties.StorageProperties;
import lt.dejavu.storage.image.model.ImageFormat;
import lt.dejavu.storage.image.strategy.FileSystemStorageStrategy;
import lt.dejavu.storage.image.strategy.StorageStrategy;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableConfigurationProperties({StorageProperties.class})
public class StorageConfiguration {
    @Bean
    public StorageStrategy storageStrategy(StorageProperties properties) {
        return new FileSystemStorageStrategy(properties.getBasePath());
    }

    @Bean
    public List<ImageFormat> allowedImageFormats() {
        return Arrays.asList(ImageFormat.values());
    }
}
