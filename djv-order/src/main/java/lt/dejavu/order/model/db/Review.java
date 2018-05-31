package lt.dejavu.order.model.db;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "review")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "rating")
    private Short rating;

    @Column(name = "comment")
    private String comment;

    @OneToOne(mappedBy = "review")
    private Order order;
}
