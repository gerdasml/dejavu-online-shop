package lt.dejavu.product.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="${tables.category.name}")
class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "${tables.category.columns.id}")
    @Column(name = "id")
    private int id;

    //@Column(name = "${tables.category.columns.name}")
    private String name;

    private String iconName;

    private String displayName;

    @OneToOne
    //@JoinColumn(name = "${tables.category.columns.parentCategory}")
    @JoinColumn(name = "parentCategory")
    private Category parentCategory;
}
