package lt.dejavu.auth.helpers;

import org.apache.commons.codec.digest.DigestUtils;

public class Sha256Hasher implements Hasher {
    @Override
    public String hash(String input) {
        return DigestUtils.sha256Hex(input);
    }
}
