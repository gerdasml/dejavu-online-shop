package lt.dejavu.auth.security.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignedToken {
    private String signature;
    private String payload;
}
