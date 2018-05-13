package lt.dejavu.product.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "product_property")
public class ProductProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private Category category;

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ProductProperty))
            return false;
        ProductProperty other = (ProductProperty) obj;
        return Objects.equals(name, other.name) && other.getCategory() != null && Objects.equals(category.getId(), other.getCategory().getId());
    }

    @Override
    public int hashCode() {
        int nameHash = 1;
        int categoryIdHash = 1;
        if (name!= null) {
            nameHash = name.hashCode();
        }
        if (category != null && category.getId() != null) {
            categoryIdHash = category.getId().hashCode();
        }
        return nameHash | categoryIdHash;
    }
}
