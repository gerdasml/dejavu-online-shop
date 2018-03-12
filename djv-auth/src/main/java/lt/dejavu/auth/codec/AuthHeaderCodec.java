package lt.dejavu.auth.codec;

import lt.dejavu.auth.exception.AccessDeniedException;
import lt.dejavu.auth.model.token.SignedToken;

public interface AuthHeaderCodec {
    String encode (String payload);
    String decode (String header) throws AccessDeniedException;
}
