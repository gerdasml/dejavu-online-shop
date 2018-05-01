package lt.dejavu.auth.service;

import lt.dejavu.auth.dto.UserDto;
import lt.dejavu.auth.mapper.UserMapper;
import lt.dejavu.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    @Override
    public UserDto getUser(long userId) {
        return userMapper.map(userRepository.getUserById(userId));
    }

    @Override
    public List<UserDto> getUsers() {
        return userRepository.getAllUsers()
                             .stream()
                             .map(userMapper::map)
                             .collect(toList());
    }

    @Override
    public void setBan(long userId, boolean isBanned) {
        userRepository.setBanned(userId, isBanned);
    }

    @Override
    public void updateUser(long userId, UserDto newInfo) {
        userRepository.updateUserInfo(userId, userMapper.map(newInfo));
    }
}
