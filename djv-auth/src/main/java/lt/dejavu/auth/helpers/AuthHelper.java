package lt.dejavu.auth.helpers;

import lt.dejavu.auth.exception.AccessDeniedException;

import java.util.UUID;

public class AuthHelper {
    public static UUID extractTokenFromHeader(String header) {
        if(!header.startsWith("Bearer ")) {
            throw new AccessDeniedException("Incorrect Authorization header format");
        }
        String token = header.substring(7);
        try {
            return UUID.fromString(token);
        } catch(Exception e) {
            throw new AccessDeniedException("Incorrect access token format");
        }
    }
}
