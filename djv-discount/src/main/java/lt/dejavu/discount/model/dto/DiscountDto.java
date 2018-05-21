package lt.dejavu.discount.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lt.dejavu.discount.model.DiscountTarget;
import lt.dejavu.discount.model.DiscountType;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@EqualsAndHashCode
public class DiscountDto {
    private Long id;
    private DiscountType type;
    private DiscountTarget targetType = DiscountTarget.EVERYTHING;
    private BigDecimal value;
    private Instant activeFrom;
    private Instant activeTo;
}
