package lt.dejavu.payment.validation.validator;

import lt.dejavu.payment.model.Card;
import lt.dejavu.payment.model.Payment;
import lt.dejavu.payment.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CvvValidator extends AbstractValidator<Card> {
    private static final int CVV_LENGTH = 3;
    private static final Pattern DIGIT_PATTERN = Pattern.compile("[0-9]*");

    @Override
    public List<ValidationError> validate(Card obj) {
        List<ValidationError> errors = new ArrayList<>();
        if (obj == null || obj.getCvv() == null) {
            errors.add(error("No cvv supplied"));
            return errors;
        }
        String cvv = obj.getCvv();
        if (cvv.length() != CVV_LENGTH) {
            errors.add(error(String.format("The cvv must consist of %d digits", CVV_LENGTH)));
        }
        if (!DIGIT_PATTERN.matcher(cvv).matches()) {
            errors.add(error("The cvv must consists of digits only"));
        }

        return errors;
    }

    @Override
    protected String location() {
        return "card.cvv";
    }
}
