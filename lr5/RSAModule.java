package lr5;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class RSAModule {
    private static final int MAX_ENCRYPT_BLOCK = 53; // максимум для 512 битного ключа
    private static final int MAX_DECRYPT_BLOCK = 64; // размер зашифрованного блока = ключ / 8

    public static KeyPair generateKeyPair(int keySize) throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(keySize);
        return keyGen.generateKeyPair();
    }

    public static byte[] encrypt(byte[] data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        List<Byte> encryptedBytes = new ArrayList<>();
        for (int i = 0; i < data.length; i += MAX_ENCRYPT_BLOCK) {
            int blockSize = Math.min(MAX_ENCRYPT_BLOCK, data.length - i);
            byte[] encryptedBlock = cipher.doFinal(data, i, blockSize);
            for (byte b : encryptedBlock) {
                encryptedBytes.add(b);
            }
        }

        byte[] result = new byte[encryptedBytes.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = encryptedBytes.get(i);
        }
        return result;
    }

    public static byte[] decrypt(byte[] data, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        List<Byte> decryptedBytes = new ArrayList<>();
        for (int i = 0; i < data.length; i += MAX_DECRYPT_BLOCK) {
            int blockSize = Math.min(MAX_DECRYPT_BLOCK, data.length - i);
            byte[] decryptedBlock = cipher.doFinal(data, i, blockSize);
            for (byte b : decryptedBlock) {
                decryptedBytes.add(b);
            }
        }

        byte[] result = new byte[decryptedBytes.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = decryptedBytes.get(i);
        }
        return result;
    }
}