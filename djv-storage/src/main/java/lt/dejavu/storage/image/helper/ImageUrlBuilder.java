package lt.dejavu.storage.image.helper;

import org.springframework.stereotype.Component;

@Component
public class ImageUrlBuilder {
    private static final String BASE_PATH = "/image";

    public String buildUrl(long id) {
        return BASE_PATH + "/" + id;
    }
}
