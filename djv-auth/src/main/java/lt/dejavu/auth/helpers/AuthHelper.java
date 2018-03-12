package lt.dejavu.auth.helpers;

import lt.dejavu.auth.exception.AccessDeniedException;
import org.apache.log4j.Logger;

import java.util.UUID;

public class AuthHelper {
    private final static Logger log = Logger.getLogger(AuthHelper.class);

    public static UUID extractTokenFromHeader(String header) {
        if(!header.startsWith("Bearer ")) {
            log.warn(String.format("Received request with incorrect authorization header format: `%s`", header));
            throw new AccessDeniedException("Incorrect Authorization header format");
        }
        String token = header.substring(7);
        try {
            return UUID.fromString(token);
        } catch(Exception e) {
            log.warn(String.format("Received request with incorrect token format: `%s`", header));
            throw new AccessDeniedException("Incorrect access token format");
        }
    }
}
