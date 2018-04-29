package lt.dejavu.payment.validation.validator;

import lt.dejavu.payment.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class CompoundValidator<T> implements Validator<T> {
    private List<Validator<T>> validators;

    public CompoundValidator() {
        this.validators = new ArrayList<>();
    }

    public CompoundValidator(List<Validator<T>> validators) {
        this.validators = validators;
        if (validators == null) {
            this.validators = new ArrayList<>();
        }
    }

    public CompoundValidator<T> addValidator(Validator<T> validator) {
        List<Validator<T>> newValidators = Stream.concat(validators.stream(), Stream.of(validator)).collect(toList());
        return new CompoundValidator<>(newValidators);
    }

    public List<ValidationError> validate(T obj) {
        return validators.stream()
                         .flatMap(x -> x.validate(obj).stream())
                         .collect(toList());
    }
}
