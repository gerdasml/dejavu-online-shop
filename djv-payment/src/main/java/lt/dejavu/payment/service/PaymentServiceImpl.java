package lt.dejavu.payment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.dejavu.payment.dto.ErrorDTO;
import lt.dejavu.payment.dto.PaymentDTO;
import lt.dejavu.payment.exception.PaymentException;
import lt.dejavu.payment.mapper.PaymentMapper;
import lt.dejavu.payment.model.Payment;
import lt.dejavu.payment.validation.ValidationError;
import lt.dejavu.payment.validation.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    private static final Logger log = LogManager.getLogger(PaymentServiceImpl.class);
    private static final String API_PATH = "/v1/payment";

    private final PaymentMapper paymentMapper;
    private final RestTemplate restTemplate;
    private final Validator<Payment> validator;
    private final ObjectMapper objectMapper;

    @Autowired
    public PaymentServiceImpl(PaymentMapper paymentMapper, RestTemplate restTemplate, Validator<Payment> validator, ObjectMapper objectMapper) {
        this.paymentMapper = paymentMapper;
        this.restTemplate = restTemplate;
        this.validator = validator;
        this.objectMapper = objectMapper;
    }

    @Override
    public void pay(Payment payment) throws PaymentException {
        PaymentDTO paymentDTO = paymentMapper.map(payment);
        try {
            restTemplate.postForEntity(API_PATH, paymentDTO, String.class);
        } catch (HttpStatusCodeException e) {
            ErrorDTO error = new ErrorDTO();
            try {
                error = objectMapper.readValue(e.getResponseBodyAsString(), ErrorDTO.class);
            } catch (IOException e1) {
                log.error("Failed to read response. ", e1);
            }
            throw new PaymentException(e.getStatusCode(), error.getError());
        }
    }

    @Override
    public List<ValidationError> validate(Payment payment) {
        return validator.validate(payment);
    }
}
