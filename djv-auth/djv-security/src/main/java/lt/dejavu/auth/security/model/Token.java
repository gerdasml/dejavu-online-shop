package lt.dejavu.auth.security.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class Token {
    private int userId;
    private Instant expiration;
    private List<Endpoint> endpoints;
}
