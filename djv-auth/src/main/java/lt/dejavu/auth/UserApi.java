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
@RequestMapping("${rest.basePath}/user")
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
    public List<User> getAllUsers(HttpServletRequest request, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) throws SigningFailedException, TokenDecodingFailedException, BadTokenSignatureException {
        authorize(authHeader, request);
        return userService.getUsers();
    }

    @RequestMapping(
            path = "/{userId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public User getUser(HttpServletRequest request, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @PathVariable("userId") int userId) throws TokenEncodingFailedException, SigningFailedException, InvalidTokenException, BadTokenSignatureException, TokenDecodingFailedException {
        authorize(authHeader, request);
        return userService.getUser(userId);
    }

    @RequestMapping(
            path = "/{userId}/ban",
            method = RequestMethod.POST
    )
    public void banUser(HttpServletRequest request, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @PathVariable("userId") int userId, @RequestParam("banned") boolean banned) throws SigningFailedException, TokenDecodingFailedException, BadTokenSignatureException {
        authorize(authHeader, request);
        userService.setBan(userId, banned);
    }

    /*@RequestMapping (
            path = "/",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void updateUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, REQUEST) {
        UUID token = AuthHelper.extractTokenFromHeader(authHeader);
    }*/

    private Endpoint buildEndpoint(HttpServletRequest request) {
        Endpoint endpoint = new Endpoint();
        endpoint.setMethod(RequestMethod.valueOf(request.getMethod()));
        endpoint.setPath(request.getRequestURI());
        return endpoint;
    }

    private void authorize(String authHeader, HttpServletRequest request) throws SigningFailedException, TokenDecodingFailedException, BadTokenSignatureException {
        tokenService.authorize(authHeader, buildEndpoint(request));
    }
}
