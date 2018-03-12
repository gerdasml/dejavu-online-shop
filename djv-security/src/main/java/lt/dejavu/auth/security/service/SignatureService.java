package lt.dejavu.auth.security.service;

import lt.dejavu.auth.security.exception.SigningFailedException;

public interface SignatureService {
    String sign(String payload) throws SigningFailedException;
}
