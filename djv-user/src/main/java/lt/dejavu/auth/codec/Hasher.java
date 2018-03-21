package lt.dejavu.auth.codec;

public interface Hasher {
    String hash(String input);
}
