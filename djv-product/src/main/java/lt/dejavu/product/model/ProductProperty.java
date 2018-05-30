package lt.dejavu.product.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.dejavu.utils.collections.Updatable;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "product_property")
@ToString
public class ProductProperty implements Updatable<ProductProperty> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "value", nullable = false)
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_property_Id")
    private CategoryProperty categoryProperty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;

    //  value and categoryProperty.id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductProperty property = (ProductProperty) o;
        return Objects.equals(value, property.value) && categoryPropertyIdEqual(property.categoryProperty);
    }

    private boolean categoryPropertyIdEqual(CategoryProperty other) {
        //both nulls or same object
        if (categoryProperty == other)
            return true;
        return categoryProperty != null && other != null && Objects.equals(categoryProperty.getId(), other.getId());
    }

    @Override
    public int hashCode() {
        if (categoryProperty != null) {
            return Objects.hash(value, categoryProperty.getId());
        }
        return Objects.hash(value);
    }

    public boolean canBeUpdated(ProductProperty other){
        if (other == null){
            return false;
        }
        return this.categoryProperty.getId().equals(other.categoryProperty.getId());
    }

    public void update(ProductProperty productProperty) {
        if (Objects.equals(value,productProperty.getValue())) {
            return;
        }
        this.setValue(productProperty.getValue());
    }
}
