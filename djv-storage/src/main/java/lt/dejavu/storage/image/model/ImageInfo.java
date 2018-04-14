package lt.dejavu.storage.image.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ImageInfo {
    private String filename;
    private String extension;
    private String url;
    private Timestamp uploadDateTime;
}
