package lr5;


import java.io.IOException;
import java.util.Scanner;

import static Result.Result.resulting;
import static lr5.Task51.tasking51;
import static lr5.Task52.tasking52;
import static lr5.TaskInformation.task_info;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println();
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
                    tasking51();
                    resulting();
                    break;
                case 2:
                    System.out.println("\nРешение задания 2:");
                    tasking52();
                    resulting();
                    break;
                default:
                    System.out.println("Ошибка: несуществующий номер задания");
            }
        }
        scanner.close();

    }
}