package lt.dejavu.payment.validation.validator;

import lt.dejavu.payment.validation.ValidationError;

import java.util.List;

public interface Validator<T> {
    List<ValidationError> validate(T obj);
}
