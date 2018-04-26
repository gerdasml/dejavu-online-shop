package lt.dejavu.order.mapper;

import lt.dejavu.order.dto.OrderItemDto;
import lt.dejavu.order.model.db.OrderItem;
import lt.dejavu.product.dto.mapper.ProductDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class OrderItemMapper {
    private final ProductDtoMapper productDtoMapper;

    @Autowired
    public OrderItemMapper(ProductDtoMapper productDtoMapper) {
        this.productDtoMapper = productDtoMapper;
    }

    public OrderItemDto map(OrderItem item) {
        OrderItemDto dto = new OrderItemDto();
        dto.setAmount(item.getAmount());
        dto.setProduct(productDtoMapper.map(item.getProduct()));

        return dto;
    }

    public List<OrderItemDto> map(List<OrderItem> items) {
        return items.stream()
                    .map(this::map)
                    .collect(toList());
    }
}
