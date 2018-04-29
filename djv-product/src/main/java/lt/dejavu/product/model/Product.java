package lt.dejavu.product.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "creationDate")
    private LocalDateTime creationDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "category")
    private Category category;

    @Column(name = "identifier")
    private String identifier;

    @Column(name = "mainImageUrl")
    private String mainImageUrl;

    @ElementCollection(fetch = FetchType.EAGER )
    @CollectionTable(
            name= "additional_image_url",
            joinColumns = @JoinColumn(name = "productID")
    )
    @Column(name = "imageUrl")
    private Set<String> additionalImagesUrls;

    @Embedded
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name="product_property",
            joinColumns=@JoinColumn(name="productId")
    )
    private Set<ProductProperty> properties;
}
