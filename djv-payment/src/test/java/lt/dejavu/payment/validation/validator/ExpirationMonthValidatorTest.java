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
        test(createPayment(), 1);
    }

    @Test
    public void testValidateExpirationMonth_negativeNumber() {
        test(createPayment(-1), 1);
    }

    @Test
    public void testValidateExpirationMonth_zero() {
        test(createPayment(0), 1);
    }

    @Test
    public void testValidateExpirationMonth_greaterThan12() {
        test(createPayment(13), 1);
    }

    @Test
    public void testValidateExpirationMonth_valid() {
        test(createPayment(9), 0);
    }

    private Payment createPayment(int month) {
        Expiration exp = new Expiration();
        exp.setMonth(month);

        Card card = new Card();
        card.setExpiration(exp);

        Payment p = new Payment();
        p.setCard(card);

        return p;
    }
}
