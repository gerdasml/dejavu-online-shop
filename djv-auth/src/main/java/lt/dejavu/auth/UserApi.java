package lt.dejavu.auth;

import lt.dejavu.auth.helpers.AuthHelper;
import lt.dejavu.auth.model.User;
import lt.dejavu.auth.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${rest.basePath}/auth/user")
public class UserApi {
    private static final Logger log = Logger.getLogger(UserApi.class);
    @Autowired
    private UserService userService;

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
    public User getUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @PathVariable("userId") int userId) {
        log.info(String.format("getUser with header `%s`", authHeader));
        UUID token = AuthHelper.extractTokenFromHeader(authHeader);
        return userService.getUser(userId, token);
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
