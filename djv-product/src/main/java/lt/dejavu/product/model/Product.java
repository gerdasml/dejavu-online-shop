package lt.dejavu.product.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class Product {
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private LocalDateTime creationDate;
    private Category category;
}
