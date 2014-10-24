package ClaspBackend;


import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;
import java.util.List;

public class CryptoKit {

    private static final int ITERATIONS = 5000;
    private static final int KEYLENGTH = 128;
    private static byte[] iv;
    public static String textText = new String("testing");

    private static SecretKey makeKey(String phraseInput, String salt) {

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
        //String keyString = Integer.toString(secret.hashCode());
        return secret;
    }

    public static SecretKey getKey(String phraseInput, String salt) {

        return makeKey(phraseInput, salt);
    }

    public static SecretKey getHash(String phraseInput, String salt) {

        return makeKey(phraseInput, salt);
    }

    // TO DO
    public static List<Account> encryptAccounts(List<Account> accounts, SecretKey secret) {

//        for (Account currentAccount : accounts) {
//            currentAccount.accountName = encryptText(currentAccount.accountName, secret);
//           currentAccount.userName = encryptText(currentAccount.userName, secret);
//            currentAccount.password = encryptText(currentAccount.password, secret);
//       }
        //textText = "testingGeasd;fkashjephauidva;dkjfasd";
        //textText = encryptText(textText, secret);
        return accounts;
    }

    // TO DO
    public static List<Account> decryptAccounts(List<Account> accounts, SecretKey secret) {

//        for (Account currentAccount : accounts) {
//            currentAccount.accountName = decryptCipherText(currentAccount.accountName, secret);
//            currentAccount.userName = decryptCipherText(currentAccount.userName, secret);
//            currentAccount.password = decryptCipherText(currentAccount.password, secret);
//        }

        //textText=decryptCipherText(textText, secret);


        return accounts;
    }

    public static String encryptText(String text, SecretKey key) {

        // TO DO
        byte[] cipherText = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            AlgorithmParameters params = cipher.getParameters();
            iv = params.getParameterSpec(IvParameterSpec.class).getIV();

            System.out.println("TEXT:" + text);

            cipherText = cipher.doFinal(text.getBytes());

            System.out.println("CIPHERTEXT:" + DatatypeConverter.printBase64Binary(cipherText));

            return DatatypeConverter.printBase64Binary(cipherText);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidParameterSpecException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return cipherText.toString();
    }

    public static String decryptCipherText(String cipherText, SecretKey secret) {

        byte[] text = null;

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));

            System.err.println("CIPHERTEXT:" + cipherText);
            text = cipher.doFinal(DatatypeConverter.parseBase64Binary(cipherText));
            System.err.println("TEXTDECRYPTED:" + new String(text, "UTF-8"));

            return new String(text, "UTF-8");
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return new String(text);
    }
}
