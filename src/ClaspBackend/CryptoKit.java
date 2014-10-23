package ClaspBackend;


import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class CryptoKit {

    private static final int ITERATIONS = 5000;
    private static final int KEYLENGTH = 256;

    private static String makeKey(String phraseInput, String salt){

        SecretKeyFactory factory = null;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        KeySpec spec = new PBEKeySpec(phraseInput.toCharArray(), salt.getBytes(), ITERATIONS, KEYLENGTH);
        SecretKey tmp = null;
        try {
            tmp = factory.generateSecret(spec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
        String keyString = Integer.toString(secret.hashCode());
        return keyString;
    }

    public static String getKey(String phraseInput, String salt) {

        return makeKey(phraseInput, salt);
    }

    public static String getHash(String phraseInput, String salt) {

        return makeKey(phraseInput, salt);
    }
}
