package lt.dejavu.auth;

import lt.dejavu.auth.exception.ApiSecurityException;
import lt.dejavu.auth.exception.UserNotFoundException;
import lt.dejavu.auth.model.rest.LoginRequest;
import lt.dejavu.auth.model.rest.LoginResponse;
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

    @RequestMapping(
            path = "/login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public LoginResponse login(@RequestBody LoginRequest request) throws ApiSecurityException, UserNotFoundException {
        return authService.login(request.getEmail(), request.getPassword());
    }

//    @RequestMapping(
//            path = "/logout",
//            method = RequestMethod.POST
//    )
//    public void logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String request) {
//        UUID token = AuthHelper.extractTokenFromHeader(request);
//        authService.logout(token);
//    }

    // TODO: find out what should be passed to registration / how it should be done
    /*public void register(???) {

    }*/
}
