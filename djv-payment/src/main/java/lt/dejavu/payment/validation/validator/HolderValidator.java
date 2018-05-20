package lt.dejavu.payment.validation.validator;

import lt.dejavu.payment.model.Card;
import lt.dejavu.payment.model.Payment;
import lt.dejavu.payment.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class HolderValidator extends AbstractValidator<Card> {
    private static final int MINIMUM_LENGTH = 2;
    private static final int MAXIMUM_LENGTH = 32;

    @Override
    public List<ValidationError> validate(Card obj) {
        List<ValidationError> errors = new ArrayList<>();
        if (obj == null || obj.getHolder() == null) {
            errors.add(error("No holder information supplied"));
            return errors;
        }
        String holderInfo = obj.getHolder();
        if (holderInfo.length() < MINIMUM_LENGTH || holderInfo.length() > MAXIMUM_LENGTH) {
            errors.add(error(String.format("Holder information must be between %d and %d symbols, inclusive", MINIMUM_LENGTH, MAXIMUM_LENGTH)));
        }
        return errors;
    }

    @Override
    protected String location() {
        return "card.holder";
    }
}
