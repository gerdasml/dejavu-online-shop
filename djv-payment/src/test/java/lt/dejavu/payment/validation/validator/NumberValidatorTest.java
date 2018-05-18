package lt.dejavu.payment.validation.validator;

import lt.dejavu.payment.model.Card;
import lt.dejavu.payment.model.Payment;
import org.junit.Test;

public class NumberValidatorTest extends SingleValidatorTestBase {

    public NumberValidatorTest() {
        super(new NumberValidator());
    }

    @Override
    protected String location() {
        return "card.number";
    }

    @Test
    public void testValidateNumber_noCardInfo() {
        test(createCard(), 1);
    }

    @Test
    public void testValidateNumber_tooShort() {
        test(createCard("18"), 1);
    }

    @Test
    public void testValidateNumber_tooLong() {
        test(createCard("12345678901234569"), 1);
    }

    @Test
    public void testValidateNumber_containsLetters() {
        test(createCard("123456789012345z"), 1);
    }

    @Test
    public void testValidateNumber_containsSpecialSymbols() {
        test(createCard("123456789012345?"), 1);
    }

    @Test
    public void testValidateNumber_violatesLuhnAlgorithm() {
        test(createCard("4111111111111112"), 1);
    }

    @Test
    public void testValidateNumber_tooShortAndViolatesLuhn() {
        test(createCard("19"), 2);
    }

    @Test
    public void testValidateNumber_tooShortAndContainsLetters() {
        test(createCard("12a"), 2);
    }

    @Test
    public void testValidateNumber_tooLongAndContainsLetters() {
        test(createCard("1234567890123456a"), 2);
    }

    @Test
    public void testValidateNumber_valid() {
        test(createCard("4111111111111111"), 0);
    }

    private Card createCard(String number) {
        Card card = new Card();
        card.setNumber(number);

        return card;
    }
}
