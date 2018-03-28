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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${rest.basePath}/auth")
public class AuthApi {
    @Autowired
    private AuthService authService;

    @Autowired
    private RegistrationRequestMapper registrationRequestMapper;

    @RequestMapping(
            path = "/login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public LoginResponse login(@RequestBody LoginRequest request) throws ApiSecurityException, UserNotFoundException {
        return authService.login(request.getEmail(), request.getPassword());
    }

    @RequestMapping(
            path = "/register",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    // TODO: Discuss what this method should return. Currently it returns nothing on success and throws an exception on failure.
    public void register(@RequestBody RegistrationRequest request) {
        User user = registrationRequestMapper.mapToUser(request);
        authService.register(user);
    }
}
