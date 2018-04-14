package lt.dejavu.storage.image.exception;

public abstract class StorageException extends Exception {
    public StorageException(String message) {
        super(message);
    }
}
