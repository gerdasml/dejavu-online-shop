package lt.dejavu.storage.image.strategy;

import lt.dejavu.storage.image.model.ImageFormat;

public interface ImageStorageStrategy {
    void saveFile(byte[] contents, long id, String extension);

    byte[] getFile(long id);
}
