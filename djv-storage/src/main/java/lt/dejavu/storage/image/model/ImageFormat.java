package lt.dejavu.storage.image.model;

public enum ImageFormat {
    PNG("png"),
    JPG("jpg"),
    UNKNOWN("");

    private final String extension;

    ImageFormat(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    public static ImageFormat resolve(String s) {
        try {
            return ImageFormat.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
