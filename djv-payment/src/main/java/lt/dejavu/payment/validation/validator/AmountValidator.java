package lt.dejavu.payment.validation.validator;

import lt.dejavu.payment.model.Payment;
import lt.dejavu.payment.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class AmountValidator extends AbstractValidator<Payment> {
    @Override
    public List<ValidationError> validate(Payment obj) {
        List<ValidationError> errors = new ArrayList<>();
        if (obj.getAmount() <= 0) {
            errors.add(error("The amount must be a positive number"));
        }
        return errors;
    }

    @Override
    protected String location() {
        return "amount";
    }
}
