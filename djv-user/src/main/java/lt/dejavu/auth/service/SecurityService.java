package lt.dejavu.auth.service;

import lt.dejavu.auth.exception.ApiSecurityException;
import lt.dejavu.auth.model.Endpoint;
import lt.dejavu.auth.dto.UserDto;

public interface SecurityService {
    String generateToken(UserDto userDto) throws ApiSecurityException;

    void authorize(String authHeader, Endpoint endpoint) throws ApiSecurityException;
}
