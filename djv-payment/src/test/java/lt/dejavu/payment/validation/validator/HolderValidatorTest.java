package lt.dejavu.payment.validation.validator;

import lt.dejavu.payment.model.Card;
import lt.dejavu.payment.model.Payment;
import org.junit.Test;

public class HolderValidatorTest extends SingleValidatorTestBase {

    public HolderValidatorTest() {
        super(new HolderValidator());
    }

    @Override
    protected String location() {
        return "card.holder";
    }

    @Test
    public void testValidateHolder_noCardInfo() {
        test(createCard(), 1);
    }

    @Test
    public void testValidateHolder_shortInfo() {
        test(createCard("X"), 1);
    }

    @Test
    public void testValidateHolder_longInfo() {
        test(createCard("LONGLONGLONGLONGLONGLONGLONGLONGL"), 1);
    }

    @Test
    public void testValidateHolder_validInfo() {
        test(createCard("Valid Person"), 0);
    }

    private Card createCard(String holder) {
        Card card = new Card();
        card.setHolder(holder);

        return card;
    }
}
