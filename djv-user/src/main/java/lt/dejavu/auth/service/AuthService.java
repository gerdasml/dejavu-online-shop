package lt.dejavu.auth.service;

import lt.dejavu.auth.exception.ApiSecurityException;
import lt.dejavu.auth.exception.UserNotFoundException;
import lt.dejavu.auth.dto.UserDto;
import lt.dejavu.auth.model.rest.LoginResponse;

public interface AuthService {
    void register(UserDto info);

    LoginResponse login(String email, String pass) throws ApiSecurityException, UserNotFoundException;

    void updatePassword(long userId, String newPassword);
}
