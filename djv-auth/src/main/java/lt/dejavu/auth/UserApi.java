package lt.dejavu.auth;

import lt.dejavu.auth.exception.token.*;
import lt.dejavu.auth.helpers.AuthHelper;
import lt.dejavu.auth.model.User;
import lt.dejavu.auth.model.UserType;
import lt.dejavu.auth.model.token.Endpoint;
import lt.dejavu.auth.model.token.SignedToken;
import lt.dejavu.auth.model.token.Token;
import lt.dejavu.auth.service.TokenService;
import lt.dejavu.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${rest.basePath}/auth/user")
public class UserApi {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @RequestMapping(
            path = "/",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<User> getAllUsers(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        UUID token = AuthHelper.extractTokenFromHeader(authHeader);
        return userService.getUsers(token);
    }

    @RequestMapping(
            path = "/{userId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public User getUser(HttpServletRequest req, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @PathVariable("userId") int userId) throws TokenEncodingFailedException, SigningFailedException, InvalidTokenException, BadTokenSignatureException, TokenDecodingFailedException {
        /*UUID token = AuthHelper.extractTokenFromHeader(authHeader);
        User u = userService.getUser(userId, token);
        SignedToken signedToken = tokenService.generateToken(u);
        return userService.getUser(userId, token);*/
        User u1 = new User();
        u1.setType(UserType.REGULAR);
        u1.setId(1);

        User u2 = new User();
        u2.setType(UserType.ADMIN);
        u2.setId(2);

        Endpoint endpoint = new Endpoint();
        endpoint.setMethod(RequestMethod.valueOf(req.getMethod()));
        endpoint.setPath(req.getRequestURI());

        tokenService.authorize(authHeader, endpoint);

        return null;

    }

    @RequestMapping(
            path = "/{userId}/ban",
            method = RequestMethod.POST
    )
    public void banUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @PathVariable("userId") int userId, @RequestParam("banned") boolean banned) {
        UUID token = AuthHelper.extractTokenFromHeader(authHeader);
        userService.setBan(token, userId, banned);
    }

    /*@RequestMapping (
            path = "/",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void updateUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, REQUEST) {
        UUID token = AuthHelper.extractTokenFromHeader(authHeader);
    }*/
}
