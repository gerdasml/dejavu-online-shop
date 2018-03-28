package lt.dejavu.auth.service;

import lt.dejavu.auth.exception.ApiSecurityException;
import lt.dejavu.auth.exception.UserNotFoundException;
import lt.dejavu.auth.model.User;
import lt.dejavu.auth.model.rest.LoginResponse;

public interface AuthService {
    void register(User info);

    LoginResponse login(String email, String pass) throws ApiSecurityException, UserNotFoundException;
}
