package lr5;

import java.security.KeyPair;
import java.util.Arrays;
import java.util.Random;

import static lr5.RSAModule.*;

public class Task51 {
    private static final int KEY_SIZE = 512;
    public static void tasking51(){
        try {
            KeyPair keyPair = generateKeyPair(KEY_SIZE);

            // Генерация тестовых данных
            byte[] originalData = new byte[200];
            new Random().nextBytes(originalData);
            System.out.println();
            System.out.println("Оригинальные данные (" + originalData.length + " байт): " + Arrays.toString(originalData));

            // Шифрование
            byte[] encryptedData = encrypt(originalData, keyPair.getPublic());
            System.out.println("Зашифрованные данные (" + encryptedData.length + " байт): " + Arrays.toString(encryptedData));

            // Расшифровка
            byte[] decryptedData = decrypt(encryptedData, keyPair.getPrivate());
            System.out.println("Расшифрованные данные: " + Arrays.toString(decryptedData));

            // Проверка
            System.out.println(Arrays.equals(originalData, decryptedData)
                    ? "Успешное шифрование и расшифровка"
                    : "Ошибка при расшифровке");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}