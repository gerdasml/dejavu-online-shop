package lt.dejavu.order.dto;

import lombok.Getter;
import lombok.Setter;
import lt.dejavu.product.dto.ProductDto;

@Getter
@Setter
public class OrderItemDto {
    private int amount;
    private ProductDto product;
}
