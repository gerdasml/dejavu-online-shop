package lt.dejavu.payment.validation.validator;

import lt.dejavu.payment.model.Expiration;
import lt.dejavu.payment.model.Payment;
import org.junit.Test;

public class ExpirationYearValidatorTest extends SingleValidatorTestBase {

    public ExpirationYearValidatorTest() {
        super(new ExpirationYearValidator());
    }

    @Override
    protected String location() {
        return "expiration.year";
    }

    @Test
    public void testValidateExpirationYear_noExpirationGiven() {
        test(createPayment(), 1);
    }

    @Test
    public void testValidateExpirationYear_tooFarInThePast() {
        test(createPayment(1969), 1);
    }

    @Test
    public void testValidateExpirationYear_valid() {
        test(createPayment(2018), 0);
    }

    private Payment createPayment(int year) {
        Expiration exp = new Expiration();
        exp.setYear(year);
        Payment p = new Payment();
        p.setExpiration(exp);

        return p;
    }
}
