package lr4;


import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Task41 {
    public static void tasking41() throws IOException {
        AVLTreeVisualizer tree = new AVLTreeVisualizer();

        BufferedReader reader = new BufferedReader(new FileReader("numbertree.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            for (String s : line.trim().split("\\s+")) {
                if (!s.isEmpty()) {
                    tree.insert(Integer.parseInt(s));
                }
            }
        }

        JFrame frame = new JFrame("Сбалансированное дерево");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(tree);
        frame.setVisible(true);
        System.out.println("Результат работы алгоритма А приведен графически в отдельном окне.");
    }
}