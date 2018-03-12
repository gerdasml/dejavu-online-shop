package lt.dejavu.auth.helpers;

import lt.dejavu.auth.exception.token.TokenDecodingFailedException;
import lt.dejavu.auth.model.token.SignedToken;

public interface SignedTokenCodec {
    String encode(SignedToken token);
    SignedToken decode(String rawToken) throws TokenDecodingFailedException;
}
