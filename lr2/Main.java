package lr2;

import java.io.IOException;
import java.util.Scanner;

import static lr2.Task21.tasking21;
import static lr2.Task22.tasking22;
import static lr2.Task23.tasking23;
import static lr2.Task24.tasking24;
import static lr2.Task25.tasking25;
import static lr2.TaskInformation.task_info;

public class Main {

    public static void main(String[] args)  {

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
                    tasking21();
                    break;
                case 2:
                    tasking22();
                    break;
                case 3:
                    tasking23();
                    break;
                case 4:
                    tasking24();
                    break;
                case 5:
                    tasking25();
                    break;
                default:
                    System.out.println("Ошибка: несуществующий номер задания");
            }
        }

    }
}
