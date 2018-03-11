package lt.dejavu.auth.service;

import lt.dejavu.auth.exception.AccessDeniedException;
import lt.dejavu.auth.exception.token.BadTokenSignatureException;
import lt.dejavu.auth.exception.token.SigningFailedException;
import lt.dejavu.auth.exception.token.TokenEncodingFailedException;
import lt.dejavu.auth.helpers.TokenCodec;
import lt.dejavu.auth.model.User;
import lt.dejavu.auth.model.UserType;
import lt.dejavu.auth.model.token.Endpoint;
import lt.dejavu.auth.model.token.SignedToken;
import lt.dejavu.auth.model.token.Token;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class TokenServiceImpl implements TokenService {
    private final TokenCodec codec;
    private final SignatureService signatureService;
    private final Map<UserType, Function<Integer, List<Endpoint>>> endpointProvider;

    public TokenServiceImpl(TokenCodec codec, SignatureService signatureService, Map<UserType, Function<Integer, List<Endpoint>>> endpointProvider) {
        this.codec = codec;
        this.signatureService = signatureService;
        this.endpointProvider = endpointProvider;
    }

    @Override
    public SignedToken generateToken(User user) throws TokenEncodingFailedException, SigningFailedException {
        Token token = buildToken(user);

        String encodedPayload = new String(Base64.getEncoder().encode(codec.encode(token).getBytes()));
        String signature = signatureService.sign(encodedPayload);

        SignedToken signedToken = new SignedToken();
        signedToken.setPayload(encodedPayload);
        signedToken.setSignature(signature);
        return signedToken;
    }

    @Override
    public Token extractPayload(SignedToken signedToken) throws BadTokenSignatureException {
        return null;
    }

    @Override
    public void authorize(Token token, Endpoint endpoint) throws AccessDeniedException {

    }

    private Token buildToken(User user) {
        Token token = new Token();
        token.setUserId(user.getId());
        token.setExpiration(Instant.now());
        List<Endpoint> endpoints = endpointProvider.get(user.getType()).apply(user.getId());
        token.setEndpoints(endpoints);
        return token;
    }
}
