package lt.dejavu.auth.service;

import lt.dejavu.auth.exception.AccessDeniedException;
import lt.dejavu.auth.exception.token.BadTokenSignatureException;
import lt.dejavu.auth.exception.token.TokenEncodingFailedException;
import lt.dejavu.auth.model.User;
import lt.dejavu.auth.model.token.Endpoint;
import lt.dejavu.auth.model.token.SignedToken;
import lt.dejavu.auth.model.token.Token;

public interface TokenService {
    SignedToken generateToken(User user) throws TokenEncodingFailedException;
    Token extractPayload(SignedToken signedToken) throws BadTokenSignatureException;
    void authorize(Token token, Endpoint endpoint) throws AccessDeniedException;
}
