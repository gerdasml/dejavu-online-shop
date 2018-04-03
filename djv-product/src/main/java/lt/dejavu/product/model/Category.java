package lt.dejavu.product.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="Category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "iconName")
    private String iconName;

    @Column(name = "displayName")
    private String displayName;

    @OneToOne
    @JoinColumn(name = "parentCategory")
    private Category parentCategory;
}
