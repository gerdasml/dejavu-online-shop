package lt.dejavu.product.model.rest.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CreateProductRequest {
    private String name;
    private String displayName;
    private String description;
    private BigDecimal price;
    private LocalDateTime creationDate;
    private Long categoryId;
}
