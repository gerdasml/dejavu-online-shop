package lt.dejavu.auth.security.service;

import lt.dejavu.auth.exception.AccessDeniedException;
import lt.dejavu.auth.model.User;
import lt.dejavu.auth.security.exception.BadTokenSignatureException;
import lt.dejavu.auth.security.exception.SigningFailedException;
import lt.dejavu.auth.security.exception.TokenDecodingFailedException;
import lt.dejavu.auth.security.exception.TokenEncodingFailedException;
import lt.dejavu.auth.security.model.Endpoint;

public interface TokenService {
    String generateToken(User user) throws TokenEncodingFailedException, SigningFailedException;

    void authorize(String authHeader, Endpoint endpoint) throws AccessDeniedException, TokenDecodingFailedException, SigningFailedException, BadTokenSignatureException;
}
