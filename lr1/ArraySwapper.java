package lr1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class ArraySwapper {
    public static int k=0;
    public static void reordering() {
        int N = 300;
        int[] array =  IntStream.range(0, N).toArray();
        shuffleArray(array);

        System.out.println("Исходный массив:");
        System.out.println(Arrays.toString(Arrays.copyOf(array, 300)));

        // Переупорядочим по одному из распределений
        reorderByDistribution(array, DistributionType.GEOMETRIC);
        System.out.println("\nПосле геометрического распределения:");
        System.out.println(Arrays.toString(Arrays.copyOf(array, 300)));

        reorderByDistribution(array, DistributionType.BINOMIAL);
        System.out.println("\nПосле биномиального распределения:");
        System.out.println(Arrays.toString(Arrays.copyOf(array, 300)));

        reorderByDistribution(array, DistributionType.WEDGE);
        System.out.println("\nПосле клиновидного распределения:");
        System.out.println(Arrays.toString(Arrays.copyOf(array, 300)));



        //эксперимент
        int queryCount = 100000;
        for (DistributionType dist : DistributionType.values()) {

            int[] reordered = IntStream.range(0, N).toArray();
            reorderByDistribution(reordered, dist);

            double[] probabilities = new double[N];
            switch (dist) {
                case GEOMETRIC -> {
                    double p = 0.02;
                    for (int i = 0; i < N; i++) {
                        probabilities[i] = Math.pow(1 - p, i) * p;
                    }
                }
                case BINOMIAL -> {
                    int n = N - 1;
                    double p = 0.5;
                    for (int i = 0; i < N; i++) {
                        probabilities[i] = logBinomialProbability(n, i, p);
                    }
                }
                case WEDGE -> {
                    for (int i = 0; i < N; i++) {
                        probabilities[i] = N - i;
                    }
                }
            }
            normalize(probabilities);

            int[] queries = new int[queryCount];
            double[] cdf = new double[N];
            cdf[0] = probabilities[0];
            for (int i = 1; i < N; i++) {
                cdf[i] = cdf[i - 1] + probabilities[i];
            }

            Random rand = new Random();
            for (int i = 0; i < queryCount; i++) {
                double r = rand.nextDouble();
                int idx = Arrays.binarySearch(cdf, r);
                if (idx < 0) idx = -idx - 1;
                queries[i] = reordered[Math.min(idx, N - 1)];
            }

            long totalComparisons = 0;
            for (int q : queries) {
                int comparisons = 0;
                for (int val : reordered) {
                    comparisons++;
                    if (val == q) break;
                }
                totalComparisons += comparisons;
            }

            double averageComparisons = (double) totalComparisons / queryCount;
            System.out.printf("\n[%s] Среднее число сравнений: %.2f%n", dist.name(), averageComparisons);
        }



    }

    enum DistributionType {
        GEOMETRIC, BINOMIAL, WEDGE
    }

    public static void reorderByDistribution(int[] array, DistributionType type) {
        int N = array.length;
        double[] probabilities = new double[N];

        switch (type) {
            case GEOMETRIC -> {
                double p = 0.02; // параметр геометрического распределения
                for (int i = 0; i < N; i++) {
                    probabilities[i] = Math.pow(1 - p, i) * p;
                }
            }
            case BINOMIAL -> {
                int n = N - 1;
                double p = 0.5;
                for (int i = 0; i < N; i++) {
                    probabilities[i] = logBinomialProbability(n, i, p);
                }
            }
            case WEDGE -> {
                for (int i = 0; i < N; i++) {
                    probabilities[i] = N - i;
                }
            }
        }

        normalize(probabilities);
        if(k!=3) {
            k++;
            Vizualizer.showChart(probabilities, type.name());
        }
        List<Integer> reordered = weightedShuffle(array, probabilities);
        for (int i = 0; i < N; i++) {
            array[i] = reordered.get(i);
        }
    }

    public static void shuffleArray(int[] array) {
        Random rand = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
        }
    }

    public static double logBinomialProbability(int n, int k, double p) {
        double logC = 0.0;
        for (int i = 1; i <= k; i++) {
            logC += Math.log(n - i + 1) - Math.log(i);
        }
        return Math.exp(logC + k * Math.log(p) + (n - k) * Math.log(1 - p));
    }


    public static void normalize(double[] probs) {
        double sum = Arrays.stream(probs).sum();
        for (int i = 0; i < probs.length; i++) {
            probs[i] /= sum;
        }
    }

    public static List<Integer> weightedShuffle(int[] array, double[] probabilities) {
        List<Integer> source = new ArrayList<>();
        for (int value : array) source.add(value);

        List<Integer> result = new ArrayList<>();
        Random rand = new Random();
        double[] probs = Arrays.copyOf(probabilities, probabilities.length);

        for (int i = 0; i < array.length; i++) {
            int index = weightedRandomIndex(probs, rand);
            result.add(source.remove(index));

            // Удалим вероятность и нормализуем
            probs = removeAndNormalize(probs, index);
        }

        return result;
    }

    public static int weightedRandomIndex(double[] weights, Random rand) {
        double r = rand.nextDouble();
        double cumulative = 0;
        for (int i = 0; i < weights.length; i++) {
            cumulative += weights[i];
            if (r <= cumulative) return i;
        }
        return weights.length - 1;
    }

    public static double[] removeAndNormalize(double[] arr, int index) {
        double[] newArr = new double[arr.length - 1];
        double sum = 0;
        for (int i = 0, j = 0; i < arr.length; i++) {
            if (i != index) {
                newArr[j++] = arr[i];
                sum += arr[i];
            }
        }
        for (int i = 0; i < newArr.length; i++) {
            newArr[i] /= sum;
        }
        return newArr;
    }


    public static double[] getDistribution(int N, DistributionType type) {
        double[] probabilities = new double[N];

        switch (type) {
            case GEOMETRIC -> {
                double p = 0.02;
                for (int i = 0; i < N; i++) {
                    probabilities[i] = Math.pow(1 - p, i) * p;
                }
            }
            case BINOMIAL -> {
                int n = N - 1;
                double p = 0.5;
                for (int i = 0; i < N; i++) {
                    probabilities[i] = logBinomialProbability(n, i, p);
                }
            }
            case WEDGE -> {
                for (int i = 0; i < N; i++) {
                    probabilities[i] = N - i;
                }
            }
        }

        normalize(probabilities);
        return probabilities;
    }

}