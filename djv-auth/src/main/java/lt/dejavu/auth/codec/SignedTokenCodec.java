package lt.dejavu.auth.codec;

import lt.dejavu.auth.exception.token.TokenDecodingFailedException;
import lt.dejavu.auth.model.token.SignedToken;

public interface SignedTokenCodec {
    String encode(SignedToken token);
    SignedToken decode(String rawToken) throws TokenDecodingFailedException;
}
