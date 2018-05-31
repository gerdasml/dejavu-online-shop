package lt.dejavu.order.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.dejavu.product.dto.ProductDto;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class OrderItemDto {
    private int amount;
    private ProductDto product;
    private BigDecimal total;
}
