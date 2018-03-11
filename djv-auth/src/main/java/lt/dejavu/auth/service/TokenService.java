package lt.dejavu.auth.service;

import lt.dejavu.auth.exception.AccessDeniedException;
import lt.dejavu.auth.exception.token.*;
import lt.dejavu.auth.model.User;
import lt.dejavu.auth.model.token.Endpoint;
import lt.dejavu.auth.model.token.SignedToken;
import lt.dejavu.auth.model.token.Token;

public interface TokenService {
    SignedToken generateToken(User user) throws TokenEncodingFailedException, SigningFailedException;
    Token extractPayload(SignedToken signedToken) throws BadTokenSignatureException, SigningFailedException, TokenDecodignFailedException;
    void authorize(Token token, Endpoint endpoint) throws AccessDeniedException;
    SignedToken fromString(String raw) throws InvalidTokenException;
}
