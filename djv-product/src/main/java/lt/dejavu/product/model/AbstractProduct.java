package lt.dejavu.product.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractProduct {

    @Id
    @TableGenerator(
            name = "ProductGen",
            table = "productIdGen",
            pkColumnName = "genName",
            valueColumnName = "genValue",
            pkColumnValue = "productGen",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ProductGen")
    @Column(name = "id")
    protected Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "mainImageUrl")
    private String mainImageUrl;

    protected AbstractProduct() {}

    protected AbstractProduct(AbstractProduct p) {
        name = p.name;
        description = p.description;
        price = p.price;
        mainImageUrl = p.mainImageUrl;
    }
}
