package lt.dejavu.payment.validation.validator;

import lt.dejavu.payment.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractValidator<T> implements Validator<T> {
    protected ValidationError error(String message) {
        return new ValidationError(location(), message);
    }

    protected abstract String location();
}
