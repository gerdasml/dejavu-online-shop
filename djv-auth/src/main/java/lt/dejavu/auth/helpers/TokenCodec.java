package lt.dejavu.auth.helpers;

import lt.dejavu.auth.exception.token.TokenDecodingFailedException;
import lt.dejavu.auth.exception.token.TokenEncodingFailedException;
import lt.dejavu.auth.model.token.Token;

public interface TokenCodec {
    String encode(Token token) throws TokenEncodingFailedException;
    Token decode (String tokenStr) throws TokenDecodingFailedException;
}
