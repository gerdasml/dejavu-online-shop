package lt.dejavu.product.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@EqualsAndHashCode(exclude = {"properties", "category"})
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="skuCode")
    private String skuCode;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "creationDate")
    private LocalDateTime creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category")
    private Category category;

    @Column(name = "identifier")
    private String identifier;

    @Column(name = "mainImageUrl")
    private String mainImageUrl;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "additional_image_url",
            joinColumns = @JoinColumn(name = "productId")
    )
    @Column(name = "imageUrl")
    private Set<String> additionalImagesUrls = new LinkedHashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<ProductProperty> properties = new LinkedHashSet<>();
}
