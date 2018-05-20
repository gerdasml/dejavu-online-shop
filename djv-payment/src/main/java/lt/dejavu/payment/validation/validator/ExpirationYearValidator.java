package lt.dejavu.payment.validation.validator;

import lt.dejavu.payment.model.Card;
import lt.dejavu.payment.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class ExpirationYearValidator extends AbstractValidator<Card> {
    private final static int MINIMUM_YEAR = 1970;

    @Override
    public List<ValidationError> validate(Card obj) {
        List<ValidationError> errors = new ArrayList<>();
        if (obj == null || obj.getExpiration() == null) {
            errors.add(error("No expiration year supplied"));
            return errors;
        }
        int expYear = obj.getExpiration().getYear();
        if (expYear < MINIMUM_YEAR) {
            errors.add(error("Expiration year is too far in the past"));
        }

        return errors;
    }

    @Override
    protected String location() {
        return "expiration.year";
    }
}
