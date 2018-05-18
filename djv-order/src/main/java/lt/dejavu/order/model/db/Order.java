package lt.dejavu.order.model.db;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lt.dejavu.auth.model.db.Address;
import lt.dejavu.auth.model.db.User;
import lt.dejavu.order.model.OrderStatus;
import lt.dejavu.order.model.ReviewStatus;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "purchase_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "reviewStatus")
    private ReviewStatus reviewStatus;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items;

    @Column(name = "creationDate")
    private Timestamp creationDate;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToOne
    @JoinTable(
            name = "order_address",
            joinColumns = @JoinColumn(name = "orderId"),
            inverseJoinColumns = @JoinColumn(name = "addressId")
    )
    private Address shippingAddress;

    @OneToOne
    @JoinColumn(name = "reviewId")
    private Review review;
}
