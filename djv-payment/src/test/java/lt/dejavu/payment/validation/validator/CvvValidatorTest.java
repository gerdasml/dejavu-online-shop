package lt.dejavu.payment.validation.validator;

import lt.dejavu.payment.model.Card;
import lt.dejavu.payment.model.Payment;
import org.junit.Test;

public class CvvValidatorTest extends SingleValidatorTestBase {

    public CvvValidatorTest() {
        super(new CvvValidator());
    }

    @Override
    protected String location() {
        return "card.cvv";
    }

    @Test
    public void testValidateCvv_noCardInfo() {
        test(createCard(), 1);
    }

    @Test
    public void testValidateCvv_emptyCvv() {
        test(createCard(""), 1);
    }

    @Test
    public void testValidateCvv_shortCvv() {
        test(createCard("1"), 1);
    }

    @Test
    public void testValidateCvv_longCvv() {
        test(createCard("1234"), 1);
    }

    @Test
    public void testValidateCvv_containsLetters() {
        test(createCard("12a"), 1);
    }

    @Test
    public void testValidateCvv_shortAndContainsLetters() {
        test(createCard("1a"), 2);
    }

    @Test
    public void testValidateCvv_validCvv() {
        test(createCard("123"), 0);
    }

    private Card createCard(String cvv) {
        Card card = new Card();
        card.setCvv(cvv);

        return card;
    }
}
