package lr1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class LineSearcher {
    private String filePath;

    public LineSearcher() {
        String filePath = "C:\\programming\\java\\TextGenerating";
        this.filePath = this.findLatestModifiedFile(filePath);
    }

    public LineSearcher(String filePath) {
        this.filePath = filePath;
    }

    public void search(String searchQuery) {
        List<String> lines = readFile(this.filePath);
        if (lines.contains(searchQuery)) {
            System.out.println("Найдено: " + searchQuery);
            lines.remove(searchQuery);
            lines.add(0, searchQuery);
            writeFile(this.filePath, lines);
        } else {
            System.out.println("Не найдено.");
        }

    }

    private static List<String> readFile(String path) {
        List<String> lines = new ArrayList();

        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            while((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }

        return lines;
    }

    private static void writeFile(String path, List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for(String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Ошибка записи файла: " + e.getMessage());
        }

    }

    private String findLatestModifiedFile(String directoryPath) {
        File directory = new File(directoryPath);
        File[] txtFiles = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
        if (txtFiles != null && txtFiles.length != 0) {
            File latestFile = (File)Arrays.stream(txtFiles).max(Comparator.comparingLong(File::lastModified)).orElse((File) null);
            return latestFile.getAbsolutePath();
        } else {
            return null;
        }
    }
}

