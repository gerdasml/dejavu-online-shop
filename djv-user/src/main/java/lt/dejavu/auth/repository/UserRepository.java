package lt.dejavu.auth.repository;

import lt.dejavu.auth.model.User;

import java.util.List;

public interface UserRepository {
    User getUserById(int id);

    int getUserId(String email, String password);

    List<User> getAllUsers();

    void setBanned(int id, boolean isBanned);

    void updateUserInfo(int id, User info);

    int addUser(User user);
}
