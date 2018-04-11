package lt.dejavu.storage.strategy;

public interface StorageStrategy {
    int saveFile(byte[] contents);

    byte[] getFile(int id);
}
