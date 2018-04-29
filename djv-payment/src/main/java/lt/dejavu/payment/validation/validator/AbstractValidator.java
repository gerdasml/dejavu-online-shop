package lt.dejavu.payment.validation.validator;

import lt.dejavu.payment.validation.ValidationError;

public abstract class AbstractValidator<T> implements Validator<T> {
    protected ValidationError error(String message) {
        return new ValidationError(location(), message);
    }

    protected abstract String location();
}
