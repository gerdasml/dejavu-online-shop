package lt.dejavu.product.dto.discount;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lt.dejavu.product.dto.CategoryDto;
import lt.dejavu.product.model.DiscountTarget;

@Getter
@Setter
@EqualsAndHashCode
public class CategoryDiscountDto extends DiscountDto {
    private CategoryDto target;

    public CategoryDiscountDto() {
        super.setTargetType(DiscountTarget.CATEGORY);
    }
}
