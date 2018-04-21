package lt.dejavu.auth.mapper;

import lt.dejavu.auth.dto.UserDto;
import lt.dejavu.auth.model.db.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto map(User user) {
        if(user == null) return null;
        UserDto dto = new UserDto();
        dto.setBanned(user.isBanned());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setId(user.getId());
        dto.setLastName(user.getLastName());
        dto.setPassword(user.getPassword());
        dto.setType(user.getType());

        return dto;
    }

    public User map(UserDto dto) {
        if (dto == null) return null;
        User user = new User();
        user.setBanned(dto.isBanned());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setId(dto.getId());
        user.setPassword(dto.getPassword());
        user.setType(dto.getType());

        return user;
    }
}
