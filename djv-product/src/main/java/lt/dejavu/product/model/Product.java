package lt.dejavu.product.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="${tables.product.name}")
public class Product {

    @Id
    @Column(name = "${tables.product.columns.id}")
    private Long id;

    @Column(name = "${tables.product.columns.name}")
    private String name;

    @Column(name = "${tables.product.columns.description}")
    private String description;

    @Column(name = "${tables.product.columns.price}")
    private BigDecimal price;

    @Column(name = "${tables.product.columns.creationDate}")
    private LocalDateTime creationDate;

    @Column(name = "${tables.product.columns.category}")
    private Category category;
}
