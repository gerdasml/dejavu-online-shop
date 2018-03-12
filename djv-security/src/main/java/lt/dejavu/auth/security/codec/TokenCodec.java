package lt.dejavu.auth.security.codec;

import lt.dejavu.auth.security.exception.TokenDecodingFailedException;
import lt.dejavu.auth.security.exception.TokenEncodingFailedException;
import lt.dejavu.auth.security.model.Token;

public interface TokenCodec {
    String encode(Token token) throws TokenEncodingFailedException;

    Token decode(String tokenStr) throws TokenDecodingFailedException;
}
