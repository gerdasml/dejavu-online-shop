package lt.dejavu.product.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="${tables.category.name}")
class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "${tables.category.id}")
    private int id;

    @Column(name = "${tables.category.name}")
    private String name;

    @Column(name = "${tables.category.parentCategory}")
    private Category parentCategory;
}
