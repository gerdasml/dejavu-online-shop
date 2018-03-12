package lt.dejavu.auth.service;

import lt.dejavu.auth.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User getUser(int userId);
    List<User> getUsers();
    void setBan(int userId, boolean isBanned);
//    void updateUser(User newInfo);
}
