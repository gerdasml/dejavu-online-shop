package lt.dejavu.auth.service;

import lt.dejavu.auth.exception.UserAlreadyExistsException;
import lt.dejavu.auth.helpers.Hasher;
import lt.dejavu.auth.model.User;
import lt.dejavu.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final Hasher hasher;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, Hasher hasher) {
        this.userRepository = userRepository;
        this.hasher = hasher;
    }

    @Override
    public void register(User info) {
        String passHash = hasher.hash(info.getPassword());
        int id = userRepository.getUserId(info.getEmail(), passHash);
        if (id != 0) {
            throw new UserAlreadyExistsException("A user with the specified credentials already exists");
        }
        info.setPassword(passHash);
        userRepository.addUser(info);
    }

    @Override
    public UUID login(String email, String pass) {
        String passHash = hasher.hash(pass);
        int userId = userRepository.getUserId(email, passHash);
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
