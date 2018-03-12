package lt.dejavu.auth.codec;

import lt.dejavu.auth.exception.token.TokenDecodingFailedException;
import lt.dejavu.auth.model.token.SignedToken;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class SignedTokenCodecImpl implements SignedTokenCodec {
    private final static String SEPARATOR = ".";
    @Override
    public String encode(SignedToken token) {
        return token.getPayload() + SEPARATOR + token.getSignature();
    }

    @Override
    public SignedToken decode(String rawToken) throws TokenDecodingFailedException {
        String[] parts = rawToken.split(Pattern.quote(SEPARATOR));
        if(parts.length != 2) {
            throw new TokenDecodingFailedException("Failed to decode signed token");
        }

        SignedToken signedToken = new SignedToken();
        signedToken.setPayload(parts[0]);
        signedToken.setSignature(parts[1]);
        return signedToken;
    }
}
