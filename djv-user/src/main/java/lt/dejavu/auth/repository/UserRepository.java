package lt.dejavu.auth.repository;

import lt.dejavu.auth.model.db.User;

import java.util.List;

public interface UserRepository {
    User getUserById(long id);

    Long getUserId(String email, String password);

    List<User> getAllUsers();

    void setBanned(User user, boolean isBanned);

    void updateUserInfo(User oldUser, User newUser);

    Long addUser(User user);
}
