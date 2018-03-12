package lt.dejavu.auth.service;

import lt.dejavu.auth.exception.token.SigningFailedException;
import lt.dejavu.auth.exception.token.TokenEncodingFailedException;
import lt.dejavu.auth.model.User;
import lt.dejavu.auth.model.rest.LoginResponse;

public interface AuthService {
    void register(User info);
    LoginResponse login(String email, String pass) throws TokenEncodingFailedException, SigningFailedException;
//    void logout(UUID token);
}
