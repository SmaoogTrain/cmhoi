package lr4;

import java.io.IOException;

public class Task42 {
    public static void tasking42() throws IOException {
        int[] ns = {7, 8, 12}; // Возможные значения n
        String filename = "C:\\programming\\java\\BTrees\\hash.txt";

        for (int n : ns) {
            System.out.println("n = " + n + " (HASHSIZE = " + (1 << n) + ")");
            MacroProcessor processor = new MacroProcessor(n);
            String output = processor.processFile(filename);
            System.out.println("--- Processed Text ---");
            System.out.println(output);
            System.out.println("Collisions: " + processor.getCollisionCount());
            System.out.println("=====================================\n");
        }
    }
}