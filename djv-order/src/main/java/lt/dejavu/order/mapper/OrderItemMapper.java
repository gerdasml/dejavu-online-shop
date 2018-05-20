package lt.dejavu.order.mapper;

import lt.dejavu.order.dto.OrderItemDto;
import lt.dejavu.order.model.db.OrderItem;
import lt.dejavu.product.response.mapper.ProductResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class OrderItemMapper {
    private final ProductResponseMapper productResponseMapper;

    @Autowired
    public OrderItemMapper(ProductResponseMapper productResponseMapper) {
        this.productResponseMapper = productResponseMapper;
    }

    public OrderItemDto map(OrderItem item) {
        OrderItemDto dto = new OrderItemDto();
        dto.setAmount(item.getAmount());
        dto.setProduct(productResponseMapper.map(item.getProduct()));
        dto.setTotal(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getAmount())));

        return dto;
    }

    public List<OrderItemDto> map(List<OrderItem> items) {
        return items.stream()
                    .map(this::map)
                    .collect(toList());
    }
}
