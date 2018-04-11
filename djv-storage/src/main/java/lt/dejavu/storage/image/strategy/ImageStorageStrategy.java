package lt.dejavu.storage.image.strategy;

public interface ImageStorageStrategy {
    void saveFile(byte[] contents, long id, String extension);

    byte[] getFile(long id);
}
