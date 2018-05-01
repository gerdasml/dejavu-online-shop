package lt.dejavu.payment.validation.validator;

import lt.dejavu.payment.model.Payment;
import lt.dejavu.payment.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class ExpirationYearValidator extends AbstractValidator<Payment> {
    private final static int MINIMUM_YEAR = 1970;

    @Override
    public List<ValidationError> validate(Payment obj) {
        List<ValidationError> errors = new ArrayList<>();
        if (obj.getCard() == null || obj.getCard().getExpiration() == null) {
            errors.add(error("No expiration year supplied"));
            return errors;
        }
        int expYear = obj.getCard().getExpiration().getYear();
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
