package lt.dejavu.product.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@EqualsAndHashCode(exclude = {"properties", "parentCategory"})
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "identifier")
    private String identifier;

    @Column(name = "iconName")
    private String iconName;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentCategory")
    private Category parentCategory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<CategoryProperty> properties = new LinkedHashSet<>();
}
