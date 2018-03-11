package lt.dejavu.auth.service;

import lt.dejavu.auth.exception.token.SigningFailedException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class SignatureServiceImpl implements SignatureService {
    private final static String hashingAlgorithm = "HmacSHA256";
    private final String secret;

    public SignatureServiceImpl(String secret) {
        this.secret = secret;
    }

    @Override
    public String sign(String payload) throws SigningFailedException{
        try {
            Mac hasher = Mac.getInstance(hashingAlgorithm);
            hasher.init(new SecretKeySpec(secret.getBytes(), hashingAlgorithm));
            byte[] hash = hasher.doFinal(payload.getBytes());
            return DatatypeConverter.printBase64Binary(hash);
        } catch(Exception e) {
            throw new SigningFailedException("Failed to generate signature", e);
        }
    }

    @Override
    public int getSignatureLength() {
        return 44;
    }
}
