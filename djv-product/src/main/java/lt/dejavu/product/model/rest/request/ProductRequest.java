package lt.dejavu.product.model.rest.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ProductRequest {
    private String name;
    private String identifier;
    private String description;
    private BigDecimal price;
    private String mainImageUrl;
    private Set<String> additionalImagesUrls;
    private LocalDateTime creationDate;
    private Long categoryId;
    private List<ProductPropertyRequest> properties;
}
