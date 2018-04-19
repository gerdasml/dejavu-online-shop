package lt.dejavu.payment.validation.validator;

import lt.dejavu.payment.model.Card;
import lt.dejavu.payment.model.Payment;
import org.junit.Test;

public class CardHolderValidatorTest extends SingleValidatorTestBase {

    public CardHolderValidatorTest() {
        super(new CardHolderValidator());
    }

    @Override
    protected String location() {
        return "card.holder";
    }

    @Test
    public void testValidateHolder_noCardInfo() {
        test(createPayment(), 1);
    }

    @Test
    public void testValidateHolder_shortInfo() {
        test(createPayment("X"), 1);
    }

    @Test
    public void testValidateHolder_longInfo() {
        test(createPayment("LONGLONGLONGLONGLONGLONGLONGLONGL"), 1);
    }

    @Test
    public void testValidateHolder_validInfo() {
        test(createPayment("Valid Person"), 0);
    }

    private Payment createPayment(String holder) {
        Card card = new Card();
        card.setHolder(holder);
        Payment p = new Payment();
        p.setCard(card);

        return p;
    }
}
