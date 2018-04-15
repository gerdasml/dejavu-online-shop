package lt.dejavu.payment;

import lt.dejavu.payment.exception.PaymentException;
import lt.dejavu.payment.model.Payment;
import lt.dejavu.payment.service.PaymentService;
import lt.dejavu.payment.validation.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${rest.payment}")
public class PaymentApi {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/pay")
    public void pay(@RequestBody Payment payment) throws PaymentException {
        paymentService.pay(payment);
    }

    @PostMapping("/validate")
    public List<ValidationError> validate(@RequestBody Payment payment) {
        return paymentService.validate(payment);
    }
}
