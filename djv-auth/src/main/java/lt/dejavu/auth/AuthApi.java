package lt.dejavu.auth;

import lt.dejavu.auth.db.dao.UserDAO;
import lt.dejavu.auth.db.dao.UserTokenDAO;
import lt.dejavu.auth.model.User;
import lt.dejavu.auth.model.rest.LoginRequest;
import lt.dejavu.auth.service.AuthService;
import lt.dejavu.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("${rest.basePath}/auth")
public class AuthApi {
    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @RequestMapping(
            path="/login",
            method= RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public UUID login(@RequestBody LoginRequest request) {
        return authService.login(request.getEmail(), request.getPassword());
    }

    @RequestMapping (
            path = "/logout",
            method = RequestMethod.POST
    )
    public void logout(@RequestHeader("Authorization") String request) {
        UUID token = extractToken(request);
        authService.logout(token);
    }

    /*public void register(???) {

    }*/

    private UUID extractToken(String header) {
        if(!header.startsWith("Bearer ")) {
            // TODO: custom exception
            throw new RuntimeException("Access denied");
        }
        String token = header.substring(7);
        try {
            return UUID.fromString(token);
        } catch(Exception e) {
            //TODO: custom exception
            throw new RuntimeException("Access denied");
        }
    }
}
