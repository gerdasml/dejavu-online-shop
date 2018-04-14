package lt.dejavu.auth;

import lt.dejavu.auth.exception.ApiSecurityException;
import lt.dejavu.auth.exception.UserNotFoundException;
import lt.dejavu.auth.model.User;
import lt.dejavu.auth.model.rest.LoginRequest;
import lt.dejavu.auth.model.rest.LoginResponse;
import lt.dejavu.auth.model.rest.RegistrationRequest;
import lt.dejavu.auth.model.rest.mapper.RegistrationRequestMapper;
import lt.dejavu.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${rest.auth}")
public class AuthApi {
    @Autowired
    private AuthService authService;

    @Autowired
    private RegistrationRequestMapper registrationRequestMapper;

    @PostMapping(
            path = "/login",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public LoginResponse login(@RequestBody LoginRequest request) throws ApiSecurityException, UserNotFoundException {
        return authService.login(request.getEmail(), request.getPassword());
    }

    @PostMapping(
            path = "/register",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    // TODO: Discuss what this method should return. Currently it returns nothing on success and throws an exception on failure.
    public void register(@RequestBody RegistrationRequest request) {
        User user = registrationRequestMapper.mapToUser(request);
        authService.register(user);
    }
}
