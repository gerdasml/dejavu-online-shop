package lt.dejavu.auth.service;

import lt.dejavu.auth.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User getUser(int userId, UUID requesterToken);
    List<User> getUsers(UUID requesterToken);
    void setBan(UUID requesterToken, int userId, boolean isBanned);
    void updateUser(UUID token, User newInfo);
}
