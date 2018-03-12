package lt.dejavu.auth.service;

import lt.dejavu.auth.exception.AccessDeniedException;
import lt.dejavu.auth.exception.token.*;
import lt.dejavu.auth.model.User;
import lt.dejavu.auth.model.token.Endpoint;
import lt.dejavu.auth.model.token.SignedToken;
import lt.dejavu.auth.model.token.Token;

public interface TokenService {
    String generateToken(User user) throws TokenEncodingFailedException, SigningFailedException;
    void authorize(String authHeader, Endpoint endpoint) throws AccessDeniedException, TokenDecodingFailedException, SigningFailedException, BadTokenSignatureException;
}
