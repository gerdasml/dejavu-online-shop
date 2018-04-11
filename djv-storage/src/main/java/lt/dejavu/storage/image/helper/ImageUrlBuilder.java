package lt.dejavu.storage.image.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ImageUrlBuilder {
    @Value("${rest.basePath}/image")
    private String basePath = "";

    public String buildUrl(long id) {
        return basePath + "/" + id;
    }
}
