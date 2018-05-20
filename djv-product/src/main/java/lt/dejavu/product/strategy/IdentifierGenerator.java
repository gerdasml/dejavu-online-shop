package lt.dejavu.product.strategy;

public interface IdentifierGenerator<T> {
    String generateIdentifier(T t);
}
