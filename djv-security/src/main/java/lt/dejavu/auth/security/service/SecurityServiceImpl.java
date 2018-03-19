package lt.dejavu.auth.security.service;

import lt.dejavu.auth.exception.AccessDeniedException;
import lt.dejavu.auth.exception.ApiSecurityException;
import lt.dejavu.auth.model.Endpoint;
import lt.dejavu.auth.model.User;
import lt.dejavu.auth.security.codec.AuthHeaderCodec;
import lt.dejavu.auth.security.codec.SignedTokenCodec;
import lt.dejavu.auth.security.codec.TokenCodec;
import lt.dejavu.auth.security.exception.BadTokenSignatureException;
import lt.dejavu.auth.security.model.SignedToken;
import lt.dejavu.auth.security.model.Token;
import lt.dejavu.auth.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.List;

@Service
public class SecurityServiceImpl implements SecurityService {
    private final static int TOKEN_DURATION_IN_MINUTES = 60;

    private final TokenCodec tokenCodec;
    private final SignatureService signatureService;
    private final AccessibilityService accessibilityService;
    private final SignedTokenCodec signedTokenCodec;
    private final AuthHeaderCodec authHeaderCodec;

    @Autowired
    public SecurityServiceImpl(AccessibilityService accessibilityService, TokenCodec tokenCodec, SignatureService signatureService, SignedTokenCodec signedTokenCodec, AuthHeaderCodec authHeaderCodec) {
        this.accessibilityService = accessibilityService;
        this.tokenCodec = tokenCodec;
        this.signatureService = signatureService;
        this.signedTokenCodec = signedTokenCodec;
        this.authHeaderCodec = authHeaderCodec;
    }

    @Override
    public String generateToken(User user) throws ApiSecurityException {
        Token token = buildToken(user);

        String encodedPayload = new String(Base64.getEncoder().encode(tokenCodec.encode(token).getBytes()));
        String signature = signatureService.sign(encodedPayload);

        SignedToken signedToken = new SignedToken();
        signedToken.setPayload(encodedPayload);
        signedToken.setSignature(signature);

        return signedTokenCodec.encode(signedToken);
    }

    @Override
    public void authorize(String authHeader, Endpoint endpoint) throws ApiSecurityException {
        Token token = extractTokenFromHeader(authHeader);
        boolean isAuthorized = token.getEndpoints().stream().anyMatch(e -> endpointsMatch(endpoint, e));
        if (!isAuthorized) {
            throw new AccessDeniedException("You are not authorized to access this endpoint");
        }
    }

    private Token extractTokenFromHeader(String authHeader) throws ApiSecurityException {
        String rawSignedToken = authHeaderCodec.decode(authHeader);
        SignedToken signedToken = signedTokenCodec.decode(rawSignedToken);
        if (!signatureService.sign(signedToken.getPayload()).equals(signedToken.getSignature())) {
            throw new BadTokenSignatureException("The token's signature is invalid");
        }
        String rawToken = new String(Base64.getDecoder().decode(signedToken.getPayload().getBytes()));
        Token token = tokenCodec.decode(rawToken);
        if (token.getExpiration().isBefore(Instant.now())) {
            throw new AccessDeniedException("The token is expired");
        }
        return token;
    }

    private boolean endpointsMatch(Endpoint real, Endpoint pattern) {
        return real.getMethod() == pattern.getMethod() && real.getPath().matches(pattern.getPath());
    }

    private Token buildToken(User user) {
        Token token = new Token();
        token.setUserId(user.getId());
        token.setExpiration(Instant.now().plus(Duration.ofMinutes(TOKEN_DURATION_IN_MINUTES)));
        List<Endpoint> endpoints = accessibilityService.getAccessibleEndpoints(user);
        token.setEndpoints(endpoints);
        return token;
    }
}