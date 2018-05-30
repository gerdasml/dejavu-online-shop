package lt.dejavu.auth.model.db;

import lombok.*;
import lt.dejavu.auth.model.UserType;

import javax.persistence.*;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private UserType type;

    @Column(name = "banned")
    private boolean banned;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "phone")
    private String phone;
}

