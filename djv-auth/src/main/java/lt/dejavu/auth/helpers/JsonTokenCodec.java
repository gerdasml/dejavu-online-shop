package lt.dejavu.auth.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.dejavu.auth.exception.token.TokenDecodignFailedException;
import lt.dejavu.auth.exception.token.TokenEncodingFailedException;
import lt.dejavu.auth.model.token.Token;

public class JsonTokenCodec implements TokenCodec {
    private final ObjectMapper mapper;

    public JsonTokenCodec(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public String encode(Token token) throws TokenEncodingFailedException {
        try {
            return mapper.writeValueAsString(token);
        } catch (Exception e) {
            throw new TokenEncodingFailedException("Failed to encode token", e);
        }
    }

    @Override
    public Token decode(String tokenStr) throws TokenDecodignFailedException {
        try {
            return mapper.readValue(tokenStr, Token.class);
        } catch (Exception e) {
            throw new TokenDecodignFailedException("Failed to decode token", e);
        }
    }
}
