package lt.dejavu.discount.model.db;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lt.dejavu.discount.model.DiscountType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "discount")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private DiscountType type;

    @Column(name = "value")
    private BigDecimal value;

    @Column(name = "activeFrom")
    private Timestamp activeFrom;

    @Column(name = "activeTo")
    private Timestamp activeTo;
}
