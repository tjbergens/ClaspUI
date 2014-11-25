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
import java.util.Arrays;
import java.util.List;

// Class of static methods containing routines to encrypt/decrypt pieces of data.
class CryptoKit {

    // Set number of iteration to perform the PBKDF2 algorithm.
    private static final int ITERATIONS = 5000;

    // Set 128-bit key encyption.
    private static final int KEYLENGTH = 128;

    // Generate an encryption key given a phrase input and salt.
    private static SecretKey makeKey(String phraseInput, String salt) {

        SecretKeyFactory factory = null;
        try {
            // Define the key generation algorithm being used
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // Define the properties of the key generation algorithm
        KeySpec spec = new PBEKeySpec(phraseInput.toCharArray(), salt.getBytes(), ITERATIONS, KEYLENGTH);
        SecretKey tmp = null;
        try {
            tmp = factory.generateSecret(spec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        // Byte array of generated key is returned.
        return new SecretKeySpec(tmp.getEncoded(), "AES");
    }

    // Generates and returns a key given the phrase input and salt.
    public static SecretKey getKey(String phraseInput, String salt) {

        return makeKey(phraseInput, salt);
    }

    // Generates a password hash given the phrase input and salt
    public static String getHash(String phraseInput, String salt) {

        // Encode our password hash in base64 so we send it over an API call without issue.
        return DatatypeConverter.printBase64Binary(makeKey(phraseInput, salt).getEncoded());
    }

    // Encrypt all members of a given Account object, given the key needed to encrypt it.
    public static Account encryptAccount(Account account, SecretKey secret) {

        account.accountName = encryptText(account.accountName, secret);

        account.userName = encryptText(account.userName, secret);

        account.password = encryptText(account.password, secret);

        return account;
    }

    // Given a NewAccount object, encrypt the contents
    public static NewAccount encryptNewAccount(NewAccount account, SecretKey secret) {

        account.accountName = encryptText(account.accountName, secret);

        account.userName = encryptText(account.userName, secret);

        account.password = encryptText(account.password, secret);

        return account;
    }

    // Decrypt the accounts that are serialized from the the API calls to retrieve all accounts.
    public static List<Account> decryptAccounts(List<Account> accounts, SecretKey secret) {

        for (Account currentAccount : accounts) {
            currentAccount.accountName = decryptCipherText(currentAccount.accountName, secret);
            currentAccount.userName = decryptCipherText(currentAccount.userName, secret);
            currentAccount.password = decryptCipherText(currentAccount.password, secret);
        }

        return accounts;
    }

    // Given a piece of text and encryption key, encrypt the text into ciphertext
    private static String encryptText(String text, SecretKey key) {

        // null to avoid exceptions
        byte[] cipherText = null;
        try {

            // Define the encryption cipher for encrypting text
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            // Logging
            System.err.println("TEXT:" + text);

            cipherText = cipher.doFinal(text.getBytes("UTF-8"));

            // Logging
            System.err.println("CIPHERTEXT:" + DatatypeConverter.printBase64Binary(cipherText));

            // Return ciphertext base64 encoded to make web service calls without character encoding issues.
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
        // Should NEVER return here
        return Arrays.toString(cipherText);
    }

    // Given a string of ciphertext and a key, decrypt the ciphertext using the key into plaintext
    private static String decryptCipherText(String cipherText, SecretKey secret) {

        // null to avoid exceptions
        byte[] text = null;

        try {

            // Define cipher suite used for encrypted ciphertext
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secret);

            // Logging
            System.err.println("CIPHERTEXT:" + cipherText);

            // Decrypt ciphertext, after base64 decoding, into plaintext
            text = cipher.doFinal(DatatypeConverter.parseBase64Binary(cipherText));
            System.err.println("TEXTDECRYPTED:" + new String(text, "UTF-8"));

            // Return the byte array as a utf-8 encoded string.
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

        // Should NEVER return this
        return new String(text);
    }
}
