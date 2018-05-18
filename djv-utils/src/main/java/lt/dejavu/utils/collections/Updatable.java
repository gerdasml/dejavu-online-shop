package lt.dejavu.utils.collections;

public interface Updatable<T> {
    boolean canBeUpdated(T other);

    void update(T other);
}
