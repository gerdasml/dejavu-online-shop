package lt.dejavu.storage.image.model;

import java.util.Arrays;

public enum ImageFormat {
    PNG("png"),
    JPG("jpg", "jpeg"),
    UNKNOWN("");

    private final String[] extensions;

    ImageFormat(String... extensions) {
        this.extensions = extensions;
    }

    public static ImageFormat resolve(String s) {
        return Arrays.stream(ImageFormat.values())
                     .filter(f -> Arrays.stream(f.extensions)
                                        .anyMatch(e -> e.toLowerCase().equals(s.toLowerCase())))
                     .findFirst()
                     .orElse(UNKNOWN);
    }

    public String getExtension() {
        return extensions[0];
    }
}
