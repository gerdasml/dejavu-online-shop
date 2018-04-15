package lt.dejavu.payment.service;

import lt.dejavu.payment.model.Payment;
import lt.dejavu.payment.validation.ValidationError;

import java.util.List;

public interface PaymentService {
    void pay(Payment payment);
    List<ValidationError> validate(Payment payment);
}
