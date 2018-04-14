package lt.dejavu.auth.security.codec;

import lt.dejavu.auth.exception.AccessDeniedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class AuthHeaderCodecImpl implements AuthHeaderCodec {
    private final static Logger log = LogManager.getLogger(AuthHeaderCodecImpl.class);
    private final static String HEADER_PREFIX = "Bearer ";

    @Override
    public String encode(String payload) {
        return HEADER_PREFIX + payload;
    }

    @Override
    public String decode(String header) throws AccessDeniedException {
        if (!header.startsWith(HEADER_PREFIX)) {
            log.info("Missing `Bearer` prefix in authorization header");
            throw new AccessDeniedException("Incorrect Authorization header format");
        }
        return header.substring(HEADER_PREFIX.length());
    }
}
