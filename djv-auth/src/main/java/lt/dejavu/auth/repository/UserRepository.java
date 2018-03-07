package lt.dejavu.auth.repository;

import lt.dejavu.auth.model.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
    User getUserByToken(UUID token);
    User getUserById(int id);
    UUID getToken(int id);
    int getUserId(String email, String password);
    List<User> getAllUsers();

    void setBanned(int id, boolean isBanned);
    void updateToken(int id, UUID token);
    void updateUserInfo(int id, User info);

    void addToken(int id, UUID token);
    int addUser(User user);
}
