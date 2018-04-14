package lt.dejavu.product.view;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProductView {

    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private LocalDateTime creationDate;
    private CategoryView category;
    private Long categoryId;
}
