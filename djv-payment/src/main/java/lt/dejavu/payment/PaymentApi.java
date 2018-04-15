package lt.dejavu.payment;

import lt.dejavu.payment.model.Payment;
import lt.dejavu.payment.service.PaymentService;
import lt.dejavu.payment.validation.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${rest.payment}")
public class PaymentApi {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/pay")
    public String pay(@RequestBody Payment payment) {
        paymentService.pay(payment);
        return "ok";
    }

    @PostMapping("/validate")
    public List<ValidationError> validate(@RequestBody Payment payment) {
        return paymentService.validate(payment);
    }
}
