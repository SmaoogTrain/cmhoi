package lr2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Task21 {

    static class Entry {
        double probability;
        int index;

        Entry(double probability, int index) {
            this.probability = probability;
            this.index = index;
        }
    }


    public static void tasking21(){
        int n = 1000;

        double[] probabilities = generateProbabilities(n);

        Entry[] bestEntries = new Entry[n];
        for (int i = 0; i < n; i++) {
            bestEntries[i] = new Entry(probabilities[i], i);
        }
        Arrays.sort(bestEntries, (a, b) -> Double.compare(b.probability, a.probability));
        int[] bestOrder = Arrays.stream(bestEntries).mapToInt(e -> e.index).toArray();
        double bestAvg = averageComparisons(probabilities, bestOrder);


        Entry[] worstEntries = new Entry[n];
        for (int i = 0; i < n; i++) {
            worstEntries[i] = new Entry(probabilities[i], i);
        }
        Arrays.sort(worstEntries, Comparator.comparingDouble(e -> e.probability));
        int[] worstOrder = Arrays.stream(worstEntries).mapToInt(e -> e.index).toArray();
        double worstAvg = averageComparisons(probabilities, worstOrder);

        double sum = bestAvg + worstAvg;
        double expectedSum = n + 1;

        System.out.printf("Среднее количество сравнений (наилучшее): %.3f%n", bestAvg);
        System.out.printf("Среднее количество сравнений (наихудшее): %.3f%n", worstAvg);
        System.out.printf("Сумма: %.3f, Ожидаемая сумма (n + 1): %d%n", sum, n + 1);

        if (Math.abs(sum - expectedSum) < 1e-9) {
            System.out.println("Простое соотношение подтверждено: best + worst = n + 1");
        } else {
            System.out.println("Соотношение не подтверждено");
        }
    }

    public static double[] generateProbabilities(int n) {
        double[] raw = new double[n];
        Random rand = new Random();
        double total = 0;
        for (int i = 0; i < n; i++) {
            raw[i] = rand.nextDouble();
            total += raw[i];
        }

        for (int i = 0; i < n; i++) {
            raw[i] /= total;
        }
        return raw;
    }

    public static double averageComparisons(double[] probs, int[] order) {
        double sum = 0;
        for (int i = 0; i < order.length; i++) {
            sum += (i + 1) * probs[order[i]];
        }
        return sum;
    }
}
