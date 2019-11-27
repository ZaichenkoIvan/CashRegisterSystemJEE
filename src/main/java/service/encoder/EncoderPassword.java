package main.java.service.encoder;

import main.java.exception.EncodePasswordRuntimeException;
import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncoderPassword {
    private static final Logger LOGGER = Logger.getLogger(EncoderPassword.class);

    public String encode(String oldPassword) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] passBytes = oldPassword.getBytes();
            messageDigest.reset();
            byte[] digested = messageDigest.digest(passBytes);
            StringBuilder newPassword = new StringBuilder();
            for (byte byt : digested) {
                newPassword.append(Integer.toHexString(0xff & byt));
            }
            return newPassword.toString();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.warn("Encode password exception", e);
            throw new EncodePasswordRuntimeException("Encode password exception", e);
        }
    }
}
