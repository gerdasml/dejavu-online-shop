package lt.dejavu.auth.helpers;

import lt.dejavu.auth.exception.token.TokenDecodingFailedException;
import lt.dejavu.auth.model.token.SignedToken;
import org.springframework.stereotype.Component;

@Component
public class SignedTokenCodecImpl implements SignedTokenCodec {
    @Override
    public String encode(SignedToken token) {
        return token.getPayload() + "." + token.getSignature();
    }

    @Override
    public SignedToken decode(String rawToken) throws TokenDecodingFailedException {
        String[] parts = rawToken.split(".");
        if(parts.length != 2) {
            throw new TokenDecodingFailedException("Failed to decode signed token");
        }

        SignedToken signedToken = new SignedToken();
        signedToken.setPayload(parts[0]);
        signedToken.setSignature(parts[1]);
        return signedToken;
    }
}
