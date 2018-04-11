package lt.dejavu.storage.image.strategy;

import lt.dejavu.storage.image.model.ImageFormat;

public interface ImageStorageStrategy {
    void saveFile(byte[] contents, int id, ImageFormat format);

    byte[] getFile(int id);
}
