package lr5;

import javax.crypto.Cipher;
import java.security.*;
import java.util.Arrays;

public class RSASignatureModule {
    private static final int RSA_KEY_SIZE = 512; // 512 бит = простые числа > 32 бит
    private static final int MIN_MESSAGE_SIZE = 16;

    // Генерация пары ключей RSA
    public static KeyPair generateRSAKeys() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(RSA_KEY_SIZE);
        return keyGen.generateKeyPair();
    }

    // Хеширование сообщения с помощью SHA-256
    public static byte[] hashMessage(byte[] message) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(message);
    }

    // Создание подписи: шифруем хеш с помощью закрытого ключа
    public static byte[] signMessage(byte[] message, PrivateKey privateKey) throws Exception {
        byte[] hash = hashMessage(message);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(hash);
    }

    // Проверка подписи: расшифровываем подпись и сравниваем с хешем сообщения
    public static boolean verifySignature(byte[] message, byte[] signature, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] decryptedHash = cipher.doFinal(signature);

        byte[] actualHash = hashMessage(message);

        return Arrays.equals(decryptedHash, actualHash);
    }
}
