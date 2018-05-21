package lt.dejavu.discount.model.db;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lt.dejavu.product.model.Category;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "categoryDiscount")
@Entity
@Getter
@Setter
@EqualsAndHashCode
public class CategoryDiscount extends Discount {
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category target;
}
