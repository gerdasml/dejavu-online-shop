package lt.dejavu.storage.model;

import lombok.Getter;
import org.springframework.http.MediaType;

@Getter
public enum ImageFormat {
    PNG("png", MediaType.IMAGE_PNG_VALUE),
    JPG("jpg", MediaType.IMAGE_JPEG_VALUE),
    UNKNOWN("", "");

    private String extension;
    private String mediaType;

    ImageFormat(String extension, String mediaType) {
        this.extension = extension;
        this.mediaType = mediaType;
    }

    public static ImageFormat resolve(String s) {
        try {
            return ImageFormat.valueOf(s);
        } catch(IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
