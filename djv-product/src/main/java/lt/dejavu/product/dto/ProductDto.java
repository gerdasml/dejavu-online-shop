package lt.dejavu.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class ProductDto {

    private long id;
    private String name;
    private String identifier;
    private String description;
    private BigDecimal price;
    private String mainImageUrl;
    private Collection<String> additionalImagesUrls;
    private LocalDateTime creationDate;
    private Long categoryId;
    private List<ProductPropertyDto> properties;
}
