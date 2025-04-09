package lr1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class TextGenerator {
    private final String filePath;
    File file;
    long maxSize = 524288000L;
    Random random = new Random();

    public TextGenerator(String filePath) {
        this.filePath = filePath;
    }

    public void generate() throws IOException {
        try (FileWriter writer = new FileWriter(this.filePath)) {
            this.file = new File(this.filePath);
            this.gen_for(writer);
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл.");
            e.printStackTrace();
        }

    }

    public void gen_for(FileWriter writer) {
        try {
            while(this.file.length() < this.maxSize) {
                int i = this.random.nextInt(PhraseStorage.phrases.length);
                writer.write(PhraseStorage.phrases[i]);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл.");
            e.printStackTrace();
        }

    }
}
