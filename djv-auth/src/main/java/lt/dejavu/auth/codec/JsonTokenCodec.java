package lt.dejavu.auth.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.dejavu.auth.exception.token.TokenDecodingFailedException;
import lt.dejavu.auth.exception.token.TokenEncodingFailedException;
import lt.dejavu.auth.model.token.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class JsonTokenCodec implements TokenCodec {
    private final ObjectMapper mapper;

    @Autowired
    public JsonTokenCodec(@Qualifier("defaultObjectMapper") ObjectMapper mapper) {
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
    public Token decode(String tokenStr) throws TokenDecodingFailedException {
        try {
            return mapper.readValue(tokenStr, Token.class);
        } catch (Exception e) {
            throw new TokenDecodingFailedException("Failed to decode token", e);
        }
    }
}
