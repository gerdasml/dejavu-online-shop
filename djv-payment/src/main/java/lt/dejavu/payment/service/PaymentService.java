package lt.dejavu.payment.service;

import lt.dejavu.payment.exception.PaymentException;
import lt.dejavu.payment.model.Card;
import lt.dejavu.payment.model.Payment;
import lt.dejavu.payment.validation.ValidationError;

import java.util.List;

public interface PaymentService {
    void pay(Payment payment) throws PaymentException;

    List<ValidationError> validate(Card card);
}
