package lt.dejavu.storage.configuration;

import lt.dejavu.storage.configuration.properties.StorageProperties;
import lt.dejavu.storage.model.ImageFormat;
import lt.dejavu.storage.strategy.FileSystemImageStorageStrategy;
import lt.dejavu.storage.strategy.ImageStorageStrategy;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableConfigurationProperties({StorageProperties.class})
public class StorageConfiguration {
    @Bean
    ImageStorageStrategy storageStrategy(StorageProperties properties) {
        return new FileSystemImageStorageStrategy(properties.getBasePath());
    }

    @Bean
    List<ImageFormat> allowedImageFormats() {
        return Arrays.asList(ImageFormat.PNG, ImageFormat.JPG);
    }
}
