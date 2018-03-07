package lt.dejavu.auth.service;

import lt.dejavu.auth.exception.AccessDeniedException;
import lt.dejavu.auth.model.User;
import lt.dejavu.auth.model.UserType;
import lt.dejavu.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User getUser(int userId, UUID requesterToken) {
        checkAdminOrSelf(requesterToken, userId);
        return userRepository.getUserById(userId);
    }

    @Override
    public List<User> getUsers(UUID requesterToken) {
        checkAdmin(requesterToken);
        return userRepository.getAllUsers();
    }

    @Override
    public void setBan(UUID requesterToken, int userId, boolean isBanned) {
        checkAdmin(requesterToken);
        userRepository.setBanned(userId, isBanned);
    }

    @Override
    public void updateUser(UUID token, User newInfo) {
        // TODO: investigate this question
        // How to know which fields to update and which to leave as-is?
        // One possibility: send in the previous info as well as the new one
        // This way we could construct a new User object by combining the two versions
        User user = userRepository.getUserByToken(token);
        userRepository.updateUserInfo(user.getId(), newInfo);
    }

    private void checkAdminOrSelf(UUID token, int id) {
        User requester = userRepository.getUserByToken(token);
        if (requester.getId() != id && requester.getType() != UserType.ADMIN) {
            throw new AccessDeniedException("Unauthorized access");
        }
    }

    private void checkAdmin(UUID token) {
        User requester = userRepository.getUserByToken(token);
        if (requester.getType() != UserType.ADMIN) {
            throw new AccessDeniedException("Unauthorized access");
        }
    }
}
