package lt.dejavu.auth.model.rest.mapper;

import lt.dejavu.auth.model.User;
import lt.dejavu.auth.model.UserType;
import lt.dejavu.auth.model.rest.RegistrationRequest;
import org.springframework.stereotype.Component;

@Component
public class RegistrationRequestMapper {
    public User mapToUser(RegistrationRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setType(UserType.REGULAR);
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setBanned(false);

        return user;
    }
}
