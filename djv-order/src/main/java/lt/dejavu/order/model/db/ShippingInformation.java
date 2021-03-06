package lt.dejavu.order.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.dejavu.auth.model.db.Address;

import javax.persistence.*;

@Entity
@Table(name = "shippingInformation")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ShippingInformation {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "recipientFirstName")
    private String recipientFirstName;

    @Column(name = "recipientLastName ")
    private String recipientLastName ;

    @OneToOne
    @JoinColumn(name = "addressId")
    private Address shippingAddress;
}
