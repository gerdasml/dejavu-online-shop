package lt.dejavu.auth.service;

import lt.dejavu.auth.exception.UserAlreadyExistsException;
import lt.dejavu.auth.model.User;
import lt.dejavu.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(User info) {
        int id = userRepository.getUserId(info.getEmail(), info.getPassword());
        if (id != 0) {
            throw new UserAlreadyExistsException("A user with the specified credentials already exists");
        }
        userRepository.addUser(info);
    }

    @Override
    public UUID login(String email, String pass) {
        int userId = userRepository.getUserId(email, pass);
        if (userId == 0) {
            // User not found
            // TODO: throw 404
            throw new ResourceNotFoundException();
        }
        UUID token = userRepository.getToken(userId);
        if (token == null) {
            // If token is not yet set for this user, set it
            token = UUID.randomUUID();
            userRepository.addToken(userId, token);
        }
        return token;
    }

    @Override
    public void logout(UUID token) {
        User user = userRepository.getUserByToken(token);
        userRepository.updateToken(user.getId(), UUID.randomUUID());
    }
}
