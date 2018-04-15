package lt.dejavu.payment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentDTO {
    private int id;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    private int amount;

    private String number;

    private String holder;

    @JsonProperty("exp_year")
    private int expirationYear;

    @JsonProperty("exp_month")
    private int expirationMonth;

    private String cvv;
}
