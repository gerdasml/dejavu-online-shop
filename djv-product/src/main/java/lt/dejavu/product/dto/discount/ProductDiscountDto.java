package lt.dejavu.product.dto.discount;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lt.dejavu.product.dto.ProductDto;
import lt.dejavu.product.model.DiscountTarget;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
