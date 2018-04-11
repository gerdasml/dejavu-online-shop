package lt.dejavu.storage.strategy;

import lt.dejavu.storage.model.ImageFormat;

public interface StorageStrategy {
    void saveFile(byte[] contents, int id, ImageFormat format);

    byte[] getFile(int id);
}
