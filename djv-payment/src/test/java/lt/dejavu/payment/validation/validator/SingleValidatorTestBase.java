package lt.dejavu.payment.validation.validator;

import lt.dejavu.payment.model.Card;
import lt.dejavu.payment.model.Payment;
import lt.dejavu.payment.validation.ValidationError;
import org.junit.Assert;

import java.util.List;

public abstract class SingleValidatorTestBase {
    private final Validator<Card> validator;

    protected SingleValidatorTestBase(Validator<Card> validator) {
        this.validator = validator;
    }

    protected Card createCard() {
        return new Card();
    }

    protected void test(Card p, int expectedCount) {
        List<ValidationError> result = validator.validate(p);

        Assert.assertEquals(expectedCount, result.size());
        result.forEach(x -> Assert.assertEquals(location(), x.getLocation()));
    }

    protected abstract String location();
}
