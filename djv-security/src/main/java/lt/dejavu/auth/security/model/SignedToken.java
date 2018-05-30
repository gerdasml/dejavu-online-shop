package lt.dejavu.auth.security.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SignedToken {
    private String signature;
    private String payload;
}
