package lr4;

import java.io.IOException;

public class Task42 {
    public static void tasking42() throws IOException {
        int[] ns = {7, 8, 12};
        String filename = "hash.txt";
        System.out.println();

        for (int n : ns) {
            System.out.println("n = " + n + " (HASHSIZE = " + (1 << n) + ")");
            MacroProcessor processor = new MacroProcessor(n);
            String output = processor.processFile(filename);
            System.out.println("\nОбработанный текст:");
            System.out.println(output);
            System.out.println("Коллизии: " + processor.getCollisionCount());
            System.out.println("=====================================\n");
        }
    }
}