package lt.dejavu.auth.security.codec;

import lt.dejavu.auth.security.exception.TokenDecodingFailedException;
import lt.dejavu.auth.security.model.SignedToken;

public interface SignedTokenCodec {
    String encode(SignedToken token);

    SignedToken decode(String rawToken) throws TokenDecodingFailedException;
}
