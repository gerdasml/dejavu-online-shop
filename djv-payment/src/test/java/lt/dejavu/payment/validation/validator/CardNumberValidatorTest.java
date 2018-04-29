package lt.dejavu.payment.validation.validator;

import lt.dejavu.payment.model.Card;
import lt.dejavu.payment.model.Payment;
import org.junit.Test;

public class CardNumberValidatorTest extends SingleValidatorTestBase {

    public CardNumberValidatorTest() {
        super(new CardNumberValidator());
    }

    @Override
    protected String location() {
        return "card.number";
    }

    @Test
    public void testValidateNumber_noCardInfo() {
        test(createPayment(), 1);
    }

    @Test
    public void testValidateNumber_tooShort() {
        test(createPayment("18"), 1);
    }

    @Test
    public void testValidateNumber_tooLong() {
        test(createPayment("12345678901234569"), 1);
    }

    @Test
    public void testValidateNumber_containsLetters() {
        test(createPayment("123456789012345z"), 1);
    }

    @Test
    public void testValidateNumber_containsSpecialSymbols() {
        test(createPayment("123456789012345?"), 1);
    }

    @Test
    public void testValidateNumber_violatesLuhnAlgorithm() {
        test(createPayment("4111111111111112"), 1);
    }

    @Test
    public void testValidateNumber_tooShortAndViolatesLuhn() {
        test(createPayment("19"), 2);
    }

    @Test
    public void testValidateNumber_tooShortAndContainsLetters() {
        test(createPayment("12a"), 2);
    }

    @Test
    public void testValidateNumber_tooLongAndContainsLetters() {
        test(createPayment("1234567890123456a"), 2);
    }

    @Test
    public void testValidateNumber_valid() {
        test(createPayment("4111111111111111"), 0);
    }

    private Payment createPayment(String number) {
        Card card = new Card();
        card.setNumber(number);
        Payment p = new Payment();
        p.setCard(card);

        return p;
    }
}
