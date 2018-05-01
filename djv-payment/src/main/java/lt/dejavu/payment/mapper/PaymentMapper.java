package lt.dejavu.payment.mapper;

import lt.dejavu.payment.dto.PaymentDTO;
import lt.dejavu.payment.model.Card;
import lt.dejavu.payment.model.Expiration;
import lt.dejavu.payment.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    public PaymentDTO map(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setAmount(payment.getAmount());
        paymentDTO.setCvv(payment.getCard().getCvv());
        paymentDTO.setExpirationMonth(payment.getCard().getExpiration().getMonth());
        paymentDTO.setExpirationYear(payment.getCard().getExpiration().getYear());
        paymentDTO.setHolder(payment.getCard().getHolder());
        paymentDTO.setNumber(payment.getCard().getNumber());

        return paymentDTO;
    }

    public Payment map(PaymentDTO paymentDTO) {
        Expiration expiration = new Expiration();
        expiration.setMonth(paymentDTO.getExpirationMonth());
        expiration.setYear(paymentDTO.getExpirationYear());

        Card card = new Card();
        card.setCvv(paymentDTO.getCvv());
        card.setHolder(paymentDTO.getHolder());
        card.setNumber(paymentDTO.getNumber());
        card.setExpiration(expiration);

        Payment payment = new Payment();
        payment.setAmount(paymentDTO.getAmount());
        payment.setCard(card);

        return payment;
    }
}
