package lt.dejavu.auth.service;

import lt.dejavu.auth.model.User;
import lt.dejavu.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User getUser(int userId) {
        return userRepository.getUserById(userId);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public void setBan(int userId, boolean isBanned) {
        userRepository.setBanned(userId, isBanned);
    }

    /*@Override
    public void updateUser(User newInfo) {
        // TODO: investigate this question
        // How to know which fields to update and which to leave as-is?
        // One possibility: send in the previous info as well as the new one
        // This way we could construct a new User object by combining the two versions
        User user = userRepository.getUserByToken(token);
        userRepository.updateUserInfo(user.getId(), newInfo);
    }*/
}
