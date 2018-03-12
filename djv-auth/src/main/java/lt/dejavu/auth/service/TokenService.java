package lt.dejavu.auth.service;

import lt.dejavu.auth.model.Endpoint;
import lt.dejavu.auth.model.User;
import lt.dejavu.auth.exception.SecurityException;

public interface TokenService {
    String generateToken(User user) throws SecurityException;

    void authorize(String authHeader, Endpoint endpoint) throws SecurityException;
}
