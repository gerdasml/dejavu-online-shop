package lt.dejavu.auth;

import lt.dejavu.auth.helpers.AuthHelper;
import lt.dejavu.auth.model.rest.LoginRequest;
import lt.dejavu.auth.service.AuthService;
import lt.dejavu.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    public UUID login(@RequestBody LoginRequest request) {
        return authService.login(request.getEmail(), request.getPassword());
    }

    @RequestMapping(
            path = "/logout",
            method = RequestMethod.POST
    )
    public void logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String request) {
        UUID token = AuthHelper.extractTokenFromHeader(request);
        authService.logout(token);
    }

    // TODO: find out what should be passed to registration / how it should be done
    /*public void register(???) {

    }*/
}
