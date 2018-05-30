package lt.dejavu.auth.security.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.dejavu.auth.model.Endpoint;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@ToString
public class Token {
    private long userId;
    private Instant expiration;
    private List<Endpoint> endpoints;
}
