package lt.dejavu.product.dto.discount;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.dejavu.product.model.DiscountTarget;
import lt.dejavu.product.model.DiscountType;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class DiscountDto {
    private Long id;
    private DiscountType type;
    private DiscountTarget targetType = DiscountTarget.EVERYTHING;
    private Long targetId;
    private BigDecimal value;
    private Instant activeFrom;
    private Instant activeTo;
}
