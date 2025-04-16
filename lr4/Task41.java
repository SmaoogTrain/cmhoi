package lr4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class Task41 {
    public static void tasking41() {
        AVLTree tree = new AVLTree();

        int[] keys = {30, 20, 40, 10, 25, 5, 35, 50, 1};
        for (int key : keys) {
            tree.insert(key);
            tree.printTree();
            System.out.println("----------");
        }
    }
}
