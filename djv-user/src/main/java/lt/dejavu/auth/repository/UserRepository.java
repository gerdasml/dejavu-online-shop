package lt.dejavu.auth.repository;

import lt.dejavu.auth.model.db.User;

import java.util.List;

public interface UserRepository {
    User getUserById(long id);

    long getUserId(String email, String password);

    List<User> getAllUsers();

    void setBanned(long id, boolean isBanned);

    void updateUserInfo(long id, User info);

    long addUser(User user);
}
