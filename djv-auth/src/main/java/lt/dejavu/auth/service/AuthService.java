package lt.dejavu.auth.service;

import lt.dejavu.auth.exception.token.SigningFailedException;
import lt.dejavu.auth.exception.token.TokenEncodingFailedException;
import lt.dejavu.auth.model.User;

import java.util.UUID;

public interface AuthService {
    void register(User info);
    String login(String email, String pass) throws TokenEncodingFailedException, SigningFailedException;
//    void logout(UUID token);
}
