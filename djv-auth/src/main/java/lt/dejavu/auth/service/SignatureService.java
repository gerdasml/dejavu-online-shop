package lt.dejavu.auth.service;

import lt.dejavu.auth.exception.token.SigningFailedException;

public interface SignatureService {
    String sign(String payload) throws SigningFailedException;
    int getSignatureLength();
}
