package lt.dejavu.auth.service;

import lt.dejavu.auth.model.Endpoint;
import lt.dejavu.auth.model.User;
import lt.dejavu.auth.exception.ApiSecurityException;

public interface SecurityService {
    String generateToken(User user) throws ApiSecurityException;

    void authorize(String authHeader, Endpoint endpoint) throws ApiSecurityException;
}
