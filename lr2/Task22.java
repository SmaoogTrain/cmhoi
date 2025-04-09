package lr2;

import java.util.*;
import java.util.function.Supplier;

public class Task22 {

    static final int N = 5000;
    static final int QUERY_COUNT = 100000;
    static final int BINOMIAL_N = 10;
    static final double P = 0.3;

    public static void tasking22(){
        simulate("Геометрическое", Task22::geometricDistribution);
        simulate("Биномиальное", Task22::binomialDistribution);
        simulate("Равномерное", Task22::uniformDistribution);
    }

    static void simulate(String name, Supplier<double[]> distributionGenerator) {
        System.out.println("=== " + name + " Распределение ===");

        double[] probabilities = distributionGenerator.get();
        int[] tape = generateTape(probabilities);

        // Подсчёт средней стоимости (среднего числа сравнений)
        double unorderedCost = averageSearchCost(tape, probabilities, false);
        double orderedCost = averageSearchCost(tape, probabilities, true);

        System.out.printf("Неупорядоченная средняя стоимость: %.3f%n", unorderedCost);
        System.out.printf("Упорядоченная средняя стоимость:   %.3f%n%n", orderedCost);
    }

    // Генерация записей на ленте согласно распределению вероятностей
    static int[] generateTape(double[] probabilities) {
        int[] tape = new int[N];
        for (int i = 0; i < N; i++) {
            tape[i] = i;
        }
        return tape;
    }

    // Подсчёт средней стоимости поиска
    static double averageSearchCost(int[] tape, double[] probabilities, boolean ordered) {
        Integer[] indices = new Integer[N];
        for (int i = 0; i < N; i++) indices[i] = i;

        if (ordered) {

            Arrays.sort(indices, (a, b) -> Double.compare(probabilities[b], probabilities[a]));
        }

        double costSum = 0.0;

        for (int q = 0; q < QUERY_COUNT; q++) {
            int key = sample(probabilities);
            for (int i = 0; i < N; i++) {
                if (tape[indices[i]] == key) {
                    costSum += (i + 1); // Сравнение на i+1 позиции
                    break;
                }
            }
        }

        return costSum / QUERY_COUNT;
    }

    // Случайный выбор ключа по вероятностному распределению
    static int sample(double[] probabilities) {
        double r = Math.random();
        double cumulative = 0;
        for (int i = 0; i < probabilities.length; i++) {
            cumulative += probabilities[i];
            if (r <= cumulative) return i;
        }
        return probabilities.length - 1; // fallback
    }

    // Генерация равномерного распределения
    static double[] uniformDistribution() {
        double[] probs = new double[N];
        Arrays.fill(probs, 1.0 / N);
        return probs;
    }

    // Генерация геометрического распределения
    static double[] geometricDistribution() {
        double[] probs = new double[N];
        double sum = 0;
        for (int i = 0; i < N; i++) {
            probs[i] = P * Math.pow(1 - P, i);
            sum += probs[i];
        }
        normalize(probs, sum);
        return probs;
    }

    // Генерация биномиального распределения
    static double[] binomialDistribution() {
        double[] probs = new double[N];
        for (int i = 0; i < N; i++) {
            int k = (i * BINOMIAL_N) / N;
            probs[i] = binomialPMF(BINOMIAL_N, k, P);
        }
        normalize(probs, Arrays.stream(probs).sum());
        return probs;
    }

    // Функция плотности биномиального распределения
    static double binomialPMF(int n, int k, double p) {
        return binomialCoeff(n, k) * Math.pow(p, k) * Math.pow(1 - p, n - k);
    }

    // Вычисление биномиального коэффициента
    static long binomialCoeff(int n, int k) {
        if (k == 0 || k == n) return 1;
        long res = 1;
        for (int i = 1; i <= k; i++) {
            res = res * (n - i + 1) / i;
        }
        return res;
    }

    static void normalize(double[] array, double sum) {
        for (int i = 0; i < array.length; i++) {
            array[i] /= sum;
        }
    }
}