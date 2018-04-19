package lt.dejavu.payment.validation.validator;

import lt.dejavu.payment.model.Payment;
import org.junit.Test;

public class AmountValidatorTest extends SingleValidatorTestBase {

    public AmountValidatorTest() {
        super(new AmountValidator());
    }

    @Override
    protected String location() {
        return "amount";
    }

    @Test
    public void testValidateAmount_negativeNumber() {
        test(createPayment(-10), 1);
    }

    @Test
    public void testValidateAmount_zero() {
        test(createPayment(0), 1);
    }

    @Test
    public void testValidateAmount_positiveNumber() {
        test(createPayment(10), 0);
    }

    private Payment createPayment(int amount) {
        Payment p = new Payment();
        p.setAmount(amount);
        return p;
    }
}
