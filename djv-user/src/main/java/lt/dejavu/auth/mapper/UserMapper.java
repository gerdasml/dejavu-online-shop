package lt.dejavu.auth.mapper;

import lt.dejavu.auth.dto.UserDto;
import lt.dejavu.auth.model.db.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto map(User user) {
        if (user == null) return null;
        UserDto dto = new UserDto();
        dto.setBanned(user.isBanned());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setId(user.getId());
        dto.setLastName(user.getLastName());
        dto.setPassword(user.getPassword());
        dto.setType(user.getType());
        dto.setAddress(user.getAddress());
        dto.setPhone(user.getPhone());

        return dto;
    }

    public User map(UserDto dto) {
        if (dto == null) return null;
        return User.builder()
                   .banned(dto.isBanned())
                   .email(dto.getEmail())
                   .firstName(dto.getFirstName())
                   .lastName(dto.getLastName())
                   .id(dto.getId())
                   .password(dto.getPassword())
                   .type(dto.getType())
                   .address(dto.getAddress())
                   .phone(dto.getPhone())
                   .build();
    }
}
