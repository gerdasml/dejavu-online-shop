package lt.dejavu.auth.service;

import lt.dejavu.auth.exception.ApiSecurityException;
import lt.dejavu.auth.model.Endpoint;
import lt.dejavu.auth.dto.UserDto;

import javax.servlet.http.HttpServletRequest;

public interface SecurityService {
    String generateToken(UserDto userDto) throws ApiSecurityException;

    long authorizeEndpoint(String authHeader, Endpoint endpoint) throws ApiSecurityException;

    long authorize(String authHeader, HttpServletRequest request) throws ApiSecurityException;
}
