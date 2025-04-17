package lr4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MacroProcessor {
    private final MacroHashTable hashTable;

    public MacroProcessor(int n) {
        this.hashTable = new MacroHashTable(n);
    }

    public String processFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        StringBuilder originalText = new StringBuilder();
        String line;

        boolean parsingDefines = true;

        while ((line = reader.readLine()) != null) {
            if (parsingDefines && line.startsWith("#define")) {
                String[] parts = line.split("\\s+", 3);
                if (parts.length == 3) {
                    String name = parts[1];
                    String value = parts[2];
                    hashTable.put(name, value);
                }
            } else {
                parsingDefines = false;
                originalText.append(line).append("\n");
            }
        }
        reader.close();

        return replaceMacros(originalText.toString());
    }

    private String replaceMacros(String text) {
        for (String macro : hashTable.keySet()) {
            String value = hashTable.get(macro);
            text = text.replaceAll("\\b" + Pattern.quote(macro) + "\\b", Matcher.quoteReplacement(value));
        }
        return text;
    }

    public int getCollisionCount() {
        return hashTable.getCollisionCount();
    }
}