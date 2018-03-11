package lt.dejavu.auth.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lt.dejavu.auth.exception.token.TokenDecodignFailedException;
import lt.dejavu.auth.exception.token.TokenEncodingFailedException;
import lt.dejavu.auth.model.token.Token;

import java.io.IOException;

public interface TokenCodec {
    String encode(Token token) throws TokenEncodingFailedException;
    Token decode (String tokenStr) throws TokenDecodignFailedException;
}
