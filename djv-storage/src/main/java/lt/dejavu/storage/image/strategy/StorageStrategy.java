package lt.dejavu.storage.image.strategy;

import lt.dejavu.storage.image.exception.FileNotFoundException;

import java.io.IOException;

public interface StorageStrategy {
    void saveFile(byte[] contents, long id, String extension) throws IOException;

    byte[] getFile(long id) throws IOException, FileNotFoundException;
}
