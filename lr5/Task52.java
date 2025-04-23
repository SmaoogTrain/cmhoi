package lr5;

import java.security.KeyPair;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import static lr5.RSASignatureModule.*;

public class Task52 {
    public static void tasking52() {
        try (Scanner scanner = new Scanner(System.in)) {
            KeyPair keyPair = generateRSAKeys();
            byte[] message = new byte[32];
            new Random().nextBytes(message);
            byte[] signature = signMessage(message, keyPair.getPrivate());

            boolean running = true;
            while (running) {
                System.out.println("\nВыберите действие:");
                System.out.println("1. Показать сообщение и подпись");
                System.out.println("2. Проверить подпись");
                System.out.println("3. Изменить 1 байт в сообщении");
                System.out.println("0. Выход");
                System.out.print("Ваш выбор: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("Сообщение: " + Arrays.toString(message));
                        System.out.println("Подпись: " + Arrays.toString(signature));
                        break;
                    case 2:
                        if (verifySignature(message, signature, keyPair.getPublic())) {
                            System.out.println("Подпись подтверждена");
                        } else {
                            System.out.println("Подпись не прошла проверку");
                        }
                        break;
                    case 3:
                        System.out.print("Введите индекс байта (0 до " + (message.length - 1) + "): ");
                        int index = scanner.nextInt();
                        if (index >= 0 && index < message.length) {
                            message[index]++;
                            System.out.println("Байт #" + index + " изменён");
                        } else {
                            System.out.println("Неверный индекс");
                        }
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        System.out.println("Неверный выбор!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}