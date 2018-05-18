package lt.dejavu.payment.validation.validator;

import lt.dejavu.payment.model.Card;
import lt.dejavu.payment.model.Expiration;
import lt.dejavu.payment.model.Payment;
import org.junit.Test;

public class ExpirationMonthValidatorTest extends SingleValidatorTestBase {

    public ExpirationMonthValidatorTest() {
        super(new ExpirationMonthValidator());
    }

    @Override
    protected String location() {
        return "expiration.month";
    }

    @Test
    public void testValidateExpirationMonth_noExpirationGiven() {
        test(createCard(), 1);
    }

    @Test
    public void testValidateExpirationMonth_negativeNumber() {
        test(createCard(-1), 1);
    }

    @Test
    public void testValidateExpirationMonth_zero() {
        test(createCard(0), 1);
    }

    @Test
    public void testValidateExpirationMonth_greaterThan12() {
        test(createCard(13), 1);
    }

    @Test
    public void testValidateExpirationMonth_valid() {
        test(createCard(9), 0);
    }

    private Card createCard(int month) {
        Expiration exp = new Expiration();
        exp.setMonth(month);

        Card card = new Card();
        card.setExpiration(exp);

        return card;
    }
}
