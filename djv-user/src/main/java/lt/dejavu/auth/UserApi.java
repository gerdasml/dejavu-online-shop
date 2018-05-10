package lt.dejavu.auth;

import lt.dejavu.auth.exception.ApiSecurityException;
import lt.dejavu.auth.model.Endpoint;
import lt.dejavu.auth.dto.UserDto;
import lt.dejavu.auth.service.SecurityService;
import lt.dejavu.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("${rest.user}")
public class UserApi {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @GetMapping(
            path = "/",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<UserDto> getAllUsers(HttpServletRequest request, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) throws ApiSecurityException {
        securityService.authorize(authHeader, request);
        return userService.getUsers();
    }

    @GetMapping(
            path = "/{userId}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public UserDto getUser(HttpServletRequest request, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @PathVariable("userId") int userId) throws ApiSecurityException {
        securityService.authorize(authHeader, request);
        return userService.getUser(userId);
    }

    @GetMapping(
            path = "/profile",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public UserDto getUserProfile(HttpServletRequest request, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) throws ApiSecurityException {
        long userId = securityService.authorize(authHeader, request);
        return userService.getUser(userId);
    }

    @PostMapping(path = "/{userId}/ban")
    public void banUser(HttpServletRequest request, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @PathVariable("userId") int userId, @RequestParam("banned") boolean banned) throws ApiSecurityException {
        securityService.authorize(authHeader, request);
        userService.setBan(userId, banned);
    }

    @PostMapping (
            path = "/",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void updateUser(HttpServletRequest request,
                           @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                           @RequestBody UserDto userInfo) throws ApiSecurityException {
        long userId = securityService.authorize(authHeader, request);
        userService.updateUser(userId, userInfo);
    }
}
