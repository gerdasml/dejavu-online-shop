package lt.dejavu.order.dto;

import lombok.Getter;
import lombok.Setter;
import lt.dejavu.auth.dto.UserDto;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderSummaryDto {
    private UserDto user;
    private int orderCount;
    private BigDecimal totalSpending;
    private BigDecimal averageSpending;
}
