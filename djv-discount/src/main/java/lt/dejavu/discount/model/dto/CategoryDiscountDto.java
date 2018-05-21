package lt.dejavu.discount.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lt.dejavu.discount.model.DiscountTarget;
import lt.dejavu.product.dto.CategoryDto;

@Getter
@Setter
@EqualsAndHashCode
public class CategoryDiscountDto extends DiscountDto {
    private CategoryDto target;

    public CategoryDiscountDto() {
        super.setTargetType(DiscountTarget.CATEGORY);
    }
}
