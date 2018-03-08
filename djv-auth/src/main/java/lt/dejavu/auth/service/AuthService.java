package lt.dejavu.auth.service;

import lt.dejavu.auth.model.User;

import java.util.UUID;

public interface AuthService {
    void register(User info);
    UUID login(String email, String pass);
    void logout(UUID token);
}
