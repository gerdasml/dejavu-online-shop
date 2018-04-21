package lt.dejavu.auth.model.rest.mapper;

import lt.dejavu.auth.dto.UserDto;
import lt.dejavu.auth.model.UserType;
import lt.dejavu.auth.model.rest.RegistrationRequest;
import org.springframework.stereotype.Component;

@Component
public class RegistrationRequestMapper {
    public UserDto mapToUser(RegistrationRequest request) {
        UserDto userDto = new UserDto();
        userDto.setEmail(request.getEmail());
        userDto.setType(UserType.REGULAR);
        userDto.setPassword(request.getPassword());
        userDto.setFirstName(request.getFirstName());
        userDto.setLastName(request.getLastName());
        userDto.setBanned(false);

        return userDto;
    }
}
