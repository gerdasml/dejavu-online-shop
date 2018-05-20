package lt.dejavu.payment;

import lt.dejavu.payment.model.Card;
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

    @PostMapping("/validate")
    public List<ValidationError> validate(@RequestBody Card card) {
        return paymentService.validate(card);
    }
}
