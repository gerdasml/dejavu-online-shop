package lt.dejavu.auth.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private UserType type;
}
