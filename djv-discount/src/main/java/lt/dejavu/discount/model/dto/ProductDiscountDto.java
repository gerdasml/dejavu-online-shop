package lt.dejavu.discount.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lt.dejavu.discount.model.DiscountTarget;
import lt.dejavu.product.dto.ProductDto;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
public class ProductDiscountDto extends DiscountDto {
    private ProductDto target;
    private BigDecimal finalPrice;

    public ProductDiscountDto() {
        super.setTargetType(DiscountTarget.PRODUCT);
    }
}
