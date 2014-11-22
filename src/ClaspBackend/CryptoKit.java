package ClaspBackend;


import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.List;

public class CryptoKit {

    private static final int ITERATIONS = 5000;
    private static final int KEYLENGTH = 128;

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

        return secret;
    }

    public static SecretKey getKey(String phraseInput, String salt) {

        return makeKey(phraseInput, salt);
    }

    public static String getHash(String phraseInput, String salt) {

        return DatatypeConverter.printBase64Binary(makeKey(phraseInput, salt).getEncoded());
    }

    // TO DO
    public static List<Account> encryptAccounts(List<Account> accounts, SecretKey secret) {

        for (Account currentAccount : accounts) {

            currentAccount.accountName = encryptText(currentAccount.accountName, secret);

            currentAccount.userName = encryptText(currentAccount.userName, secret);

            currentAccount.password = encryptText(currentAccount.password, secret);
        }

        return accounts;
    }

    public static NewAccount encryptNewAccount(NewAccount account, SecretKey secret) {

        account.accountName = encryptText(account.accountName, secret);

        account.userName = encryptText(account.userName, secret);

        account.password = encryptText(account.password, secret);

        return account;
    }

    // TO DO
    public static List<Account> decryptAccounts(List<Account> accounts, SecretKey secret) {

        for (Account currentAccount : accounts) {
            currentAccount.accountName = decryptCipherText(currentAccount.accountName, secret);
            currentAccount.userName = decryptCipherText(currentAccount.userName, secret);
            currentAccount.password = decryptCipherText(currentAccount.password, secret);
        }

        return accounts;
    }

    public static String encryptText(String text, SecretKey key) {

        // TO DO
        byte[] cipherText = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            System.out.println("TEXT:" + text);

            cipherText = cipher.doFinal(text.getBytes("UTF-8"));

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
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return cipherText.toString();
    }

    public static String decryptCipherText(String cipherText, SecretKey secret) {

        byte[] text = null;

        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secret);

            System.err.println("CIPHERTEXT:" + cipherText);
            text = cipher.doFinal(DatatypeConverter.parseBase64Binary(cipherText));
            System.err.println("TEXTDECRYPTED:" + new String(text, "UTF-8"));

            return new String(text, "UTF-8");
        } catch (InvalidKeyException e) {
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
