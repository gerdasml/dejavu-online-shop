package lt.dejavu.payment.service;

import lt.dejavu.payment.dto.PaymentDTO;
import lt.dejavu.payment.mapper.PaymentMapper;
import lt.dejavu.payment.model.Payment;
import lt.dejavu.payment.validation.ValidationError;
import lt.dejavu.payment.validation.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    private static final String API_PATH = "/v1/payment";

    private final PaymentMapper paymentMapper;
    private final RestTemplate restTemplate;
    private final Validator<Payment> validator;

    @Autowired
    public PaymentServiceImpl(PaymentMapper paymentMapper, RestTemplate restTemplate, Validator<Payment> validator) {
        this.paymentMapper = paymentMapper;
        this.restTemplate = restTemplate;
        this.validator = validator;
    }

    @Override
    public void pay(Payment payment) {
        PaymentDTO paymentDTO = paymentMapper.map(payment);
        ResponseEntity<PaymentDTO> response =
                restTemplate.postForEntity(API_PATH, paymentDTO, PaymentDTO.class);
    }

    @Override
    public List<ValidationError> validate(Payment payment) {
        return validator.validate(payment);
    }
}
