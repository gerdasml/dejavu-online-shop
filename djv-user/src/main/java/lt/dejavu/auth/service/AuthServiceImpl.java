package lt.dejavu.auth.service;

import lt.dejavu.auth.codec.Hasher;
import lt.dejavu.auth.exception.SecurityException;
import lt.dejavu.auth.exception.UserAlreadyExistsException;
import lt.dejavu.auth.model.User;
import lt.dejavu.auth.model.rest.LoginResponse;
import lt.dejavu.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final SecurityService securityService;
    private final UserRepository userRepository;
    private final Hasher hasher;

    @Autowired
    public AuthServiceImpl(SecurityService securityService, UserRepository userRepository, Hasher hasher) {
        this.securityService = securityService;
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
    public LoginResponse login(String email, String pass) throws SecurityException {
        String passHash = hasher.hash(pass);
        int userId = userRepository.getUserId(email, passHash);
        if (userId == 0) {
            // User not found
            // TODO: throw 404
            throw new ResourceNotFoundException();
        }
        User user = userRepository.getUserById(userId);

        LoginResponse response = new LoginResponse();
        if (user.isBanned()) {
            response.setBanned(true);
        } else {
            response.setToken(securityService.generateToken(user));
        }
        return response;
    }

//    @Override
//    public void logout(UUID token) {
//        User user = userRepository.getUserByToken(token);
//        userRepository.updateToken(user.getId(), UUID.randomUUID());
//    }
}
