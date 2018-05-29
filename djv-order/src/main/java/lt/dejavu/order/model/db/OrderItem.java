package lt.dejavu.order.model.db;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lt.dejavu.product.model.AbstractProduct;
import lt.dejavu.product.model.Product;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "orderItem")
@EqualsAndHashCode
public class OrderItem extends AbstractProduct {
    @Column(name = "productId")
    private Long productId;
    @Column(name = "amount")
    private Integer amount;
    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;

    public OrderItem() {
        super();
    }

    public OrderItem(AbstractProduct p) {
        super(p);
        productId = p.getId();
    }
}
