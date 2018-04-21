package lt.dejavu.payment.validation.validator;

import lt.dejavu.payment.model.Payment;
import lt.dejavu.payment.validation.ValidationError;
import org.junit.Assert;

import java.util.List;

public abstract class SingleValidatorTestBase {
    private final Validator<Payment> validator;

    protected SingleValidatorTestBase(Validator<Payment> validator) {
        this.validator = validator;
    }

    protected Payment createPayment() {
        return new Payment();
    }

    protected void test(Payment p, int expectedCount) {
        List<ValidationError> result = validator.validate(p);

        Assert.assertEquals(expectedCount, result.size());
        result.forEach(x -> Assert.assertEquals(location(), x.getLocation()));
    }

    protected abstract String location();
}
