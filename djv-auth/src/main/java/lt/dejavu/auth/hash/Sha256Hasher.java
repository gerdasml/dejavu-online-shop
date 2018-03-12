package lt.dejavu.auth.hash;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class Sha256Hasher implements Hasher {
    @Override
    public String hash(String input) {
        return DigestUtils.sha256Hex(input);
    }
}
