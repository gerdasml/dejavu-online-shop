package lt.dejavu.cart.model.db;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.dejavu.auth.model.db.User;
import lt.dejavu.order.model.db.OrderItem;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "cart")
@ToString
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "cartItem",
            joinColumns = @JoinColumn(name = "cartId"),
            inverseJoinColumns = @JoinColumn(name = "itemId")
    )
    private List<OrderItem> items;
}
