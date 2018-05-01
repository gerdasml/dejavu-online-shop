package lt.dejavu.auth.service;

import lt.dejavu.auth.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto getUser(long userId);

    List<UserDto> getUsers();

    void setBan(long userId, boolean isBanned);
    void updateUser(long userId, UserDto newInfo);
}
