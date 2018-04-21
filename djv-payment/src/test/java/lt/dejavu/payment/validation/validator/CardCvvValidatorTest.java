package lt.dejavu.payment.validation.validator;

import lt.dejavu.payment.model.Card;
import lt.dejavu.payment.model.Payment;
import org.junit.Test;

public class CardCvvValidatorTest extends SingleValidatorTestBase {

    public CardCvvValidatorTest() {
        super(new CardCvvValidator());
    }

    @Override
    protected String location() {
        return "card.cvv";
    }

    @Test
    public void testValidateCvv_noCardInfo() {
        test(createPayment(), 1);
    }

    @Test
    public void testValidateCvv_emptyCvv() {
        test(createPayment(""), 1);
    }

    @Test
    public void testValidateCvv_shortCvv() {
        test(createPayment("1"), 1);
    }

    @Test
    public void testValidateCvv_longCvv() {
        test(createPayment("1234"), 1);
    }

    @Test
    public void testValidateCvv_containsLetters() {
        test(createPayment("12a"), 1);
    }

    @Test
    public void testValidateCvv_shortAndContainsLetters() {
        test(createPayment("1a"), 2);
    }

    @Test
    public void testValidateCvv_validCvv() {
        test(createPayment("123"), 0);
    }

    private Payment createPayment(String cvv) {
        Card card = new Card();
        card.setCvv(cvv);
        Payment p = new Payment();
        p.setCard(card);

        return p;
    }
}
