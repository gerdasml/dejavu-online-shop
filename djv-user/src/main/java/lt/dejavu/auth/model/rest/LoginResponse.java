package lt.dejavu.auth.model.rest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String token;
    private Boolean banned;
}
