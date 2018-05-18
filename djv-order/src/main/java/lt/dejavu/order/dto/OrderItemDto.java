package lt.dejavu.order.dto;

import lombok.Getter;
import lombok.Setter;
import lt.dejavu.product.response.ProductResponse;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemDto {
    private int amount;
    private ProductResponse product;
    private BigDecimal total;
}
