package lt.dejavu.payment.validation.validator;

import lt.dejavu.payment.model.Card;
import lt.dejavu.payment.model.Payment;
import lt.dejavu.payment.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class ExpirationMonthValidator extends AbstractValidator<Card> {
    private static final int MINIMUM_MONTH = 1;
    private static final int MAXIMUM_MONTH = 12;

    @Override
    public List<ValidationError> validate(Card obj) {
        List<ValidationError> errors = new ArrayList<>();
        if (obj == null || obj.getExpiration() == null) {
            errors.add(error("No expiration month supplied"));
            return errors;
        }
        int expMonth = obj.getExpiration().getMonth();
        if (expMonth < MINIMUM_MONTH || expMonth > MAXIMUM_MONTH) {
            errors.add(error(String.format("Month must be between %d and %d, inclusive", MINIMUM_MONTH, MAXIMUM_MONTH)));
        }
        return errors;
    }

    @Override
    protected String location() {
        return "expiration.month";
    }
}
