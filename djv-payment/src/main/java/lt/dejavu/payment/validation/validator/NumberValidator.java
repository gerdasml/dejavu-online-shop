package lt.dejavu.payment.validation.validator;

import lt.dejavu.payment.model.Card;
import lt.dejavu.payment.model.Payment;
import lt.dejavu.payment.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class NumberValidator extends AbstractValidator<Card> {
    private static final int NUMBER_LENGTH = 16;
    private static final Pattern DIGIT_PATTERN = Pattern.compile("[0-9]*");

    @Override
    public List<ValidationError> validate(Card obj) {
        List<ValidationError> errors = new ArrayList<>();
        if (obj == null || obj.getNumber() == null) {
            errors.add(error("No card number supplied"));
            return errors;
        }
        String cardNumber = obj.getNumber();
        if (cardNumber.length() != NUMBER_LENGTH) {
            errors.add(error("The card number must consist of 16 digits"));
        }
        if (!DIGIT_PATTERN.matcher(cardNumber).matches()) {
            errors.add(error("The card number must consist of digits"));
        } else if (!satisfiesLuhnAlgorithm(cardNumber)) {
            errors.add(error("Invalid card number"));
        }

        return errors;
    }

    @Override
    protected String location() {
        return "card.number";
    }

    private boolean satisfiesLuhnAlgorithm(String number) {
        List<Integer> reversed = new StringBuilder(number)
                .reverse()
                .toString()
                .chars()
                .map(x -> x - (int) '0')
                .boxed()
                .collect(toList());

        int result = IntStream.range(0, reversed.size())
                              .map(i -> (i % 2 == 0 ? 1 : 2) * reversed.get(i))
                              .map(x -> x > 9 ? x - 9 : x)
                              .reduce(0, (a, b) -> (a + b) % 10);

        return result == 0;
    }
}
