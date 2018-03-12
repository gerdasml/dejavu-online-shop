package lt.dejavu.auth.service;

import lt.dejavu.auth.exception.AccessDeniedException;
import lt.dejavu.auth.exception.token.*;
import lt.dejavu.auth.helpers.AuthHeaderCodec;
import lt.dejavu.auth.helpers.SignedTokenCodec;
import lt.dejavu.auth.helpers.TokenCodec;
import lt.dejavu.auth.model.User;
import lt.dejavu.auth.model.UserType;
import lt.dejavu.auth.model.token.Endpoint;
import lt.dejavu.auth.model.token.SignedToken;
import lt.dejavu.auth.model.token.Token;

import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class TokenServiceImpl implements TokenService {
    private final static int TOKEN_DURATION_IN_MINUTES = 60;

    private final TokenCodec tokenCodec;
    private final SignatureService signatureService;
    private final Map<UserType, Function<Integer, List<Endpoint>>> endpointProvider;
    private final SignedTokenCodec signedTokenCodec;
    private final AuthHeaderCodec authHeaderCodec;

    public TokenServiceImpl(TokenCodec tokenCodec, SignatureService signatureService, SignedTokenCodec signedTokenCodec, AuthHeaderCodec authHeaderCodec, Map<UserType, Function<Integer, List<Endpoint>>> endpointProvider) {
        this.tokenCodec = tokenCodec;
        this.signatureService = signatureService;
        this.signedTokenCodec = signedTokenCodec;
        this.authHeaderCodec = authHeaderCodec;
        this.endpointProvider = endpointProvider;
    }

    @Override
    public SignedToken generateToken(User user) throws TokenEncodingFailedException, SigningFailedException {
        Token token = buildToken(user);

        String encodedPayload = new String(Base64.getEncoder().encode(tokenCodec.encode(token).getBytes()));
        String signature = signatureService.sign(encodedPayload);

        SignedToken signedToken = new SignedToken();
        signedToken.setPayload(encodedPayload);
        signedToken.setSignature(signature);
        return signedToken;
    }

    @Override
    public Token extractPayload(SignedToken signedToken) throws BadTokenSignatureException, SigningFailedException, TokenDecodingFailedException {
        String raw = signedToken.getPayload();
        if (!signatureService.sign(raw).equals(signedToken.getSignature())) {
            throw new BadTokenSignatureException("The token's signature is invalid");
        }
        String encoded = new String(Base64.getDecoder().decode(raw.getBytes()));
        return tokenCodec.decode(encoded);
    }

    @Override
    public void authorize(String authHeader, Endpoint endpoint) throws AccessDeniedException, TokenDecodingFailedException, SigningFailedException, BadTokenSignatureException {
        String rawToken = authHeaderCodec.decode(authHeader);
        SignedToken signedToken = signedTokenCodec.decode(rawToken);
        if (!signatureService.sign(signedToken.getPayload()).equals(signedToken.getSignature())) {
            throw new BadTokenSignatureException("The token's signature is invalid");
        }
        Token token = tokenCodec.decode(signedToken.getPayload());
        if(token.getExpiration().isBefore(Instant.now())) {
            throw new AccessDeniedException("The token is expired");
        }
        boolean isAuthorized = token.getEndpoints().stream().anyMatch(e -> endpointsMatch(endpoint, e));
        if(!isAuthorized) {
            throw new AccessDeniedException("You are not authorized to access this endpoint");
        }
    }

    @Override
    public SignedToken fromString(String raw) throws InvalidTokenException {
        int signatureLength = signatureService.getSignatureLength();
        if(raw.length() < signatureLength) {
            throw new InvalidTokenException("Bad token format");
        }

        String signaturePart = raw.substring(0, signatureLength);
        String payloadPart = raw.substring(signatureLength);

        SignedToken signedToken = new SignedToken();
        signedToken.setSignature(signaturePart);
        signedToken.setPayload(payloadPart);
        return signedToken;
    }

    private boolean endpointsMatch(Endpoint real, Endpoint pattern) {
        return real.getMethod() == pattern.getMethod() && real.getPath().matches(pattern.getPath());
    }

    private Token buildToken(User user) {
        Token token = new Token();
        token.setUserId(user.getId());
        token.setExpiration(Instant.now().plus(Duration.ofMinutes(TOKEN_DURATION_IN_MINUTES)));
        List<Endpoint> endpoints = endpointProvider.get(user.getType()).apply(user.getId());
        token.setEndpoints(endpoints);
        return token;
    }
}
