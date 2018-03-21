package lt.dejavu.auth.security.codec;

import lt.dejavu.auth.exception.AccessDeniedException;

public interface AuthHeaderCodec {
    String encode(String payload);

    String decode(String header) throws AccessDeniedException;
}
