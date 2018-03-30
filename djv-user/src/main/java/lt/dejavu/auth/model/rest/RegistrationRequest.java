package lt.dejavu.auth.model.rest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    // TODO: add more registration fields?
}
