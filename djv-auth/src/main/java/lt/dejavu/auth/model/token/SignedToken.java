package lt.dejavu.auth.model.token;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignedToken {
    private String signature;
    private String payload;

    @Override
    public String toString() {
        return signature + payload;
    }
}
