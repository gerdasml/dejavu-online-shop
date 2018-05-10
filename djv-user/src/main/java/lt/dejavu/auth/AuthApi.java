package lt.dejavu.auth;

import lt.dejavu.auth.dto.UserDto;
import lt.dejavu.auth.exception.ApiSecurityException;
import lt.dejavu.auth.exception.UserNotFoundException;
import lt.dejavu.auth.model.rest.ChangePasswordRequest;
import lt.dejavu.auth.model.rest.LoginRequest;
import lt.dejavu.auth.model.rest.LoginResponse;
import lt.dejavu.auth.model.rest.RegistrationRequest;
import lt.dejavu.auth.model.rest.mapper.RegistrationRequestMapper;
import lt.dejavu.auth.service.AuthService;
import lt.dejavu.auth.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("${rest.auth}")
public class AuthApi {
    @Autowired
    private AuthService authService;

    @Autowired
    private SecurityService securityService;

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
    public void register(@RequestBody RegistrationRequest request) {
        UserDto userDto = registrationRequestMapper.mapToUser(request);
        authService.register(userDto);
    }

    @PostMapping(path = "/changePassword")
    public void changePassword(HttpServletRequest request,
                               @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
                               @RequestBody ChangePasswordRequest passwordRequest) throws ApiSecurityException {
        long userId = securityService.authorize(authHeader, request);
        authService.updatePassword(userId, passwordRequest.getCurrentPassword(), passwordRequest.getNewPassword());
    }
}
