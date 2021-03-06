package lt.dejavu.discount.model.db;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.dejavu.product.model.Product;

import javax.persistence.*;

@Table(name = "productDiscount")
@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString(exclude={"target"})
public class ProductDiscount extends Discount {
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product target;
}
