package lt.dejavu.order.model.db;

import lombok.*;
import lt.dejavu.auth.model.db.User;
import lt.dejavu.order.model.OrderStatus;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchase_order")
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "reviewShown")
    private Boolean reviewShown;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items;

    @Column(name = "creationDate")
    private Timestamp creationDate;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToOne
    @JoinColumn(name = "shippingInformationId")
    private ShippingInformation shippingInformation;

    @OneToOne
    @JoinColumn(name = "reviewId")
    private Review review;

    @Version
    @Column(name = "lastModified")
    private Timestamp lastModified;
}
