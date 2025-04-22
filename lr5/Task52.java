package lr5;

import java.security.KeyPair;
import java.util.Arrays;
import java.util.Random;

import static lr5.RSASignatureModule.*;

public class Task52 {
    public static void tasking52(){
        try {
            // 1. Генерация ключей
            KeyPair keyPair = generateRSAKeys();

            // 2. Сообщение длиной не менее 16 байт
            byte[] message = new byte[32];
            new Random().nextBytes(message);
            System.out.println("Сообщение: " + Arrays.toString(message));

            // 3. Подпись
            byte[] signature = signMessage(message, keyPair.getPrivate());
            System.out.println("Цифровая подпись: " + Arrays.toString(signature));

            // 4. Проверка подписи
            boolean isValid = verifySignature(message, signature, keyPair.getPublic());
            System.out.println(isValid ? "Подпись подтверждена" : "Подпись недействительна");

            // 5. Попытка подмены
            message[0]++; // немного меняем сообщение
            boolean isStillValid = verifySignature(message, signature, keyPair.getPublic());
            System.out.println(isStillValid ? "Подпись всё ещё проходит!?" : "Подпись отклонена при подмене данных");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}