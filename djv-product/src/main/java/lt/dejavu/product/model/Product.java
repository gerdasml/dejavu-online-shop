package lt.dejavu.product.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
@ToString
public class Product extends AbstractProduct {

    @Column(name="skuCode")
    private String skuCode;

    @Column(name = "minimalPrice")
    private BigDecimal minimalPrice;

    @Column(name = "creationDate")
    private LocalDateTime creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category")
    private Category category;

    @Column(name = "identifier")
    private String identifier;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "additional_image_url",
            joinColumns = @JoinColumn(name = "productId")
    )
    @Column(name = "imageUrl")
    private Set<String> additionalImagesUrls = new LinkedHashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<ProductProperty> properties = new LinkedHashSet<>();

    public Product() {
        super();
    }

    public Product(AbstractProduct p) {
        super(p);
    }

    public Product(AbstractProduct p, Long id) {
        super(p);
        setId(id);
    }
}
