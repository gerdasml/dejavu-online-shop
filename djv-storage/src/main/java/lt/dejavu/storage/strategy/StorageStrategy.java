package lt.dejavu.storage.strategy;

public interface StorageStrategy {
    void saveFile(byte[] contents, int id, String extension);

    byte[] getFile(int id);
}
