package lt.dejavu.auth.service;

import lt.dejavu.auth.model.User;
import lt.dejavu.auth.model.rest.LoginResponse;
import lt.dejavu.auth.exception.SecurityException;

public interface AuthService {
    void register(User info);

    LoginResponse login(String email, String pass) throws SecurityException;
//    void logout(UUID token);
}
