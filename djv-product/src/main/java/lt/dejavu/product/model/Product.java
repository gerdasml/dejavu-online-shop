package lt.dejavu.product.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@Table(name="Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "${tables.product.columns.id}")
    @Column(name = "id")
    private Long id;

    //@Column(name = "${tables.product.columns.name}")
    @Column(name = "name")
    private String name;

    //@Column(name = "${tables.product.columns.description}")
    @Column(name = "description")
    private String description;

    //@Column(name = "${tables.product.columns.price}")
    @Column(name = "price")
    private BigDecimal price;

    //@Column(name = "${tables.product.columns.creationDate}")
    @Column(name = "creationDate")
    private LocalDateTime creationDate;


    @ManyToOne
    //@JoinColumn(name = "${tables.product.columns.category}")
    @JoinColumn(name = "category")
    private Category category;
}
