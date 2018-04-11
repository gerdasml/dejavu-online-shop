package lt.dejavu.storage.image.strategy;

public interface StorageStrategy {
    void saveFile(byte[] contents, long id, String extension);

    byte[] getFile(long id);
}
