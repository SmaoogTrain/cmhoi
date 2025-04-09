package lr1;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static lr1.ArraySwapper.reordering;
import static lr1.TaskInformation.task_info;

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

                    int one = 0;
                    String path;
                    File file;
                    do {
                        path = "C:\\programming\\java\\TextGenerating\\txt" + one + ".txt";
                        file = new File(path);
                        one++;
                    }while (file.exists());
                    System.out.println("Текстовый файл №" + one + " создан");
                    System.out.println("Заполнение:");
                    TextGenerator tg1 = new TextGenerator(path);
                    tg1.generate();
                    System.in.read();
                    break;

                case 2:
                    System.out.println("Введите название файла, в котором будет производиться поиск: ");
                    String searchFile = scanner.nextLine();
                    System.out.println("Введите строку для поиска: ");
                    String searchQuery = scanner.nextLine();
                    LineSearcher ls2;

                    if(searchFile.isEmpty()){
                        ls2 = new LineSearcher();
                    } else{
                        ls2 = new LineSearcher(searchFile);
                    }
                    ls2.search(searchQuery);
                    System.in.read();
                    break;

                case 3:
                    reordering();
                    System.in.read();
                    break;

                default:
                    System.out.println("Ошибка: несуществующий номер задания");

            }
        }
        scanner.close();
    }
}