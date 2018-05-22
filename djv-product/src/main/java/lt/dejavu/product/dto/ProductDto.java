package lt.dejavu.product.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.dejavu.product.dto.discount.ProductDiscountDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ProductDto {

    private Long id;
    private String name;
    private String identifier;
    private String description;
    private BigDecimal price;
    private String mainImageUrl;
    private Set<String> additionalImagesUrls;
    private LocalDateTime creationDate;
    private Long categoryId;
    private Set<ProductPropertyDto> properties;
    private ProductDiscountDto discount;
}
