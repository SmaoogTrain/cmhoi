package lr4;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class MacroHashTable {
    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final int hashSize;
    private final LinkedList<Entry>[] table;
    private int collisionCount = 0;

    public MacroHashTable(int n) {
        this.hashSize = 1 << n;
        this.table = new LinkedList[hashSize];
        for (int i = 0; i < hashSize; i++) {
            table[i] = new LinkedList<>();
        }
    }

    public void put(String key, String value) {
        int index = hash(key);
        for (Entry entry : table[index]) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }
        if (!table[index].isEmpty()) {
            collisionCount++;
        }
        table[index].add(new Entry(key, value));
    }

    public String get(String key) {
        int index = hash(key);
        for (Entry entry : table[index]) {
            if (entry.key.equals(key)) return entry.value;
        }
        return null;
    }

    public int getCollisionCount() {
        return collisionCount;
    }

    private int hash(String key) {
        long num = 0;
        for (int i = 0; i < key.length(); i++) {
            num = num * 62 + BASE62.indexOf(key.charAt(i));
        }
        return (int)(num % hashSize);
    }
    private static class Entry {
        String key, value;
        Entry(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    public Set<String> keySet() {
        Set<String> keys = new HashSet<>();
        for (LinkedList<Entry> bucket : table) {
            for (Entry e : bucket) {
                keys.add(e.key);
            }
        }
        return keys;
    }
}