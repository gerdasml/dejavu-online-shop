package lt.dejavu.discount.model.mapper;

import lt.dejavu.discount.model.db.CategoryDiscount;
import lt.dejavu.discount.model.db.Discount;
import lt.dejavu.discount.model.db.ProductDiscount;
import lt.dejavu.product.dto.discount.CategoryDiscountDto;
import lt.dejavu.product.dto.discount.DiscountDto;
import lt.dejavu.product.dto.discount.ProductDiscountDto;
import lt.dejavu.product.dto.mapper.CategoryDtoMapper;
import lt.dejavu.product.dto.mapper.ProductDtoMapper;
import lt.dejavu.product.model.Category;
import lt.dejavu.product.model.Product;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class DiscountMapper {
    private final ProductDtoMapper productMapper;
    private final CategoryDtoMapper categoryMapper;

    public DiscountMapper(ProductDtoMapper productMapper, CategoryDtoMapper categoryMapper) {
        this.productMapper = productMapper;
        this.categoryMapper = categoryMapper;
    }

    public Discount mapToDiscount(DiscountDto dto) {
        Discount discount;
        switch (dto.getTargetType()) {
            case PRODUCT:
                discount = new ProductDiscount();
                Product product = new Product();
                product.setId(dto.getTargetId());
                ((ProductDiscount) discount).setTarget(product);
                break;
            case CATEGORY:
                discount = new CategoryDiscount();
                Category category = new Category();
                category.setId(dto.getTargetId());
                ((CategoryDiscount) discount).setTarget(category);
                break;
            default:
                discount = new Discount();
                break;
        }

        discount.setActiveTo(Timestamp.from(dto.getActiveTo()));
        discount.setActiveFrom(Timestamp.from(dto.getActiveFrom()));
        discount.setValue(dto.getValue());
        discount.setType(dto.getType());

        return discount;
    }

    public DiscountDto mapToDto(Discount discount) {
        DiscountDto dto;
        if (discount instanceof ProductDiscount) {
            dto = new ProductDiscountDto();
            ((ProductDiscountDto) dto).setTarget(productMapper.mapToDto(((ProductDiscount) discount).getTarget()));
        } else if (discount instanceof  CategoryDiscount) {
            dto = new CategoryDiscountDto();
            ((CategoryDiscountDto) dto).setTarget(categoryMapper.map(((CategoryDiscount) discount).getTarget()));
        } else {
            dto = new DiscountDto();
        }
        dto.setActiveFrom(discount.getActiveFrom().toInstant());
        dto.setActiveTo(discount.getActiveTo().toInstant());
        dto.setId(discount.getId());
        dto.setType(discount.getType());
        dto.setValue(discount.getValue());

        return dto;
    }

    public List<DiscountDto> mapToDto(List<Discount> discounts) {
        return discounts.stream().map(this::mapToDto).collect(toList());
    }
}
