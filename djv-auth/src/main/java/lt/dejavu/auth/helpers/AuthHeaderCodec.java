package lt.dejavu.auth.helpers;

import lt.dejavu.auth.exception.AccessDeniedException;
import lt.dejavu.auth.model.token.SignedToken;

public interface AuthHeaderCodec {
    String encode (String payload);
    String decode (String header) throws AccessDeniedException;
}
