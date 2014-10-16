package ClaspBackend;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * Created by TJ on 10/15/2014.
 */
public class AuthEngine {

    public void makeKey(String masterPass, String username){

        SecretKeyFactory factory = null;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        KeySpec spec = new PBEKeySpec(masterPass.toCharArray(), username.getBytes(), 65536, 256);
        SecretKey tmp = null;
        try {
            tmp = factory.generateSecret(spec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
        String keyString = secret.getEncoded().toString();
        System.out.println(keyString);
    }
}
