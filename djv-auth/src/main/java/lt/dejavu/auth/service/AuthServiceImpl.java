package lt.dejavu.auth.service;

import lt.dejavu.auth.exception.UserAlreadyExistsException;
import lt.dejavu.auth.exception.token.SigningFailedException;
import lt.dejavu.auth.exception.token.TokenEncodingFailedException;
import lt.dejavu.auth.helpers.Hasher;
import lt.dejavu.auth.model.User;
import lt.dejavu.auth.model.rest.LoginResponse;
import lt.dejavu.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final Hasher hasher;

    @Autowired
    public AuthServiceImpl(TokenService tokenService, UserRepository userRepository, Hasher hasher) {
        this.tokenService = tokenService;
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
    public LoginResponse login(String email, String pass) throws TokenEncodingFailedException, SigningFailedException {
        String passHash = hasher.hash(pass);
        int userId = userRepository.getUserId(email, passHash);
        if (userId == 0) {
            // User not found
            // TODO: throw 404
            throw new ResourceNotFoundException();
        }
        User user = userRepository.getUserById(userId);

        LoginResponse response = new LoginResponse();
        if(user.isBanned()) {
            response.setBanned(true);
        } else {
            response.setToken(tokenService.generateToken(user));
        }
        return response;
    }

//    @Override
//    public void logout(UUID token) {
//        User user = userRepository.getUserByToken(token);
//        userRepository.updateToken(user.getId(), UUID.randomUUID());
//    }
}
