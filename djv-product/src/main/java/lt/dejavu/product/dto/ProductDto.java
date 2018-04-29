package lt.dejavu.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProductDto {

    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private LocalDateTime creationDate;
    private Long categoryId;
    // imageUrl
    // productProperties
}
