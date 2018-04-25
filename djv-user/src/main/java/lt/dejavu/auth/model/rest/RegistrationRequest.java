package lt.dejavu.auth.model.rest;

import lombok.Getter;
import lombok.Setter;
import lt.dejavu.auth.model.db.Address;

@Getter
@Setter
public class RegistrationRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Address address;
    private String phone;
}
