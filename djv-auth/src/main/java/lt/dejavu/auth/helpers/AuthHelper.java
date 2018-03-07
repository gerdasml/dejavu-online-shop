package lt.dejavu.auth.helpers;

import java.util.UUID;

public class AuthHelper {
    public static UUID extractTokenFromHeader(String header) {
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
