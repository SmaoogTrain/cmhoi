package lr3;

import java.io.IOException;
import java.util.Scanner;

import static lr3.Task31.tasking31;
import static lr3.Task32.tasking32;
import static lr3.Task33.tasking33;
import static lr3.TaskInformation.task_info;


public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            task_info();
            System.out.println("Введите номер задания:");
            int task_number = 0;
            task_number = scanner.nextInt();
            scanner.nextLine();

            if (task_number == 0) {
                System.out.println("Завершение работы.");
                break;
            }
            switch (task_number) {
                case 1:
                    System.out.println("\nРешение задания 1:");
                    tasking31();
                    System.out.print("\nНажмите Enter, чтобы продолжить...");
                    System.in.read();
                    break;
                case 2:
                    System.out.println("\nРешение задания 2:");
                    tasking32();
                    System.out.print("\nНажмите Enter, чтобы продолжить...");
                    System.in.read();
                    break;
                case 3:
                    System.out.println("\nРешение задания 3:");
                    tasking33();
                    System.out.print("\nНажмите Enter, чтобы продолжить...");
                    System.in.read();
                    break;
                default:
                    System.out.println("Ошибка: несуществующий номер задания");
            }
        }

    }
}
