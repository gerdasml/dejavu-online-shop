package lt.dejavu.auth.service;

import lt.dejavu.auth.codec.Hasher;
import lt.dejavu.auth.dto.UserDto;
import lt.dejavu.auth.exception.ApiSecurityException;
import lt.dejavu.auth.exception.IncorrectPasswordException;
import lt.dejavu.auth.exception.UserAlreadyExistsException;
import lt.dejavu.auth.exception.UserNotFoundException;
import lt.dejavu.auth.mapper.UserMapper;
import lt.dejavu.auth.model.db.User;
import lt.dejavu.auth.model.rest.LoginResponse;
import lt.dejavu.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final SecurityService securityService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final Hasher hasher;

    @Autowired
    public AuthServiceImpl(SecurityService securityService, UserRepository userRepository, UserMapper userMapper, Hasher hasher) {
        this.securityService = securityService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.hasher = hasher;
    }

    @Override
    public void register(UserDto info) {
        String passHash = hasher.hash(info.getPassword());
        Long id = userRepository.getUserId(info.getEmail(), passHash);
        if (id != null) {
            throw new UserAlreadyExistsException("A user with the specified credentials already exists");
        }
        info.setPassword(passHash);
        userRepository.addUser(userMapper.map(info));
    }

    @Override
    public LoginResponse login(String email, String pass) throws ApiSecurityException, UserNotFoundException {
        String passHash = hasher.hash(pass);
        Long userId = userRepository.getUserId(email, passHash);
        if (userId == null) {
            // User not found
            throw new UserNotFoundException("User with the specified credentials was not found");
        }
        UserDto userDto = userMapper.map(userRepository.getUserById(userId));

        LoginResponse response = new LoginResponse();
        if (userDto.isBanned()) {
            response.setBanned(true);
        } else {
            response.setToken(securityService.generateToken(userDto));
        }
        return response;
    }

    @Override
    public void updatePassword(long userId, String currentPassword, String newPassword) {
        String currentPassHash = hasher.hash(currentPassword);
        String newPassHash = hasher.hash(newPassword);
        User user = userRepository.getUserById(userId);

        if (!user.getPassword().equals(currentPassHash)) {
            throw new IncorrectPasswordException();
        }

        User newUser = user.toBuilder().password(newPassHash).build();
        userRepository.updateUserInfo(user, newUser, true);
    }
}
