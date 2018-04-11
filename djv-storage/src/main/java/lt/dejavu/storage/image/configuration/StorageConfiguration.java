package lt.dejavu.storage.image.configuration;

import lt.dejavu.storage.image.configuration.properties.StorageProperties;
import lt.dejavu.storage.image.model.ImageFormat;
import lt.dejavu.storage.image.strategy.FileSystemImageStorageStrategy;
import lt.dejavu.storage.image.strategy.ImageStorageStrategy;
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
