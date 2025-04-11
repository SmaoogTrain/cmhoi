package lr2;

public class Task23 {
    public static void tasking23(){
        int n = 5;
        double[] p = new double[n];
        double sum = 0;

        // Шаг 1: Вычисляем сумму 1/2^i
        for (int i = 0; i < n; i++) {
            sum += 1.0 / Math.pow(2, i + 1);
        }

        // Шаг 2: Нормализуем вероятности
        for (int i = 0; i < n; i++) {
            p[i] = (1.0 / Math.pow(2, i + 1)) / sum;
        }

        // Шаг 3: Вычисляем сумму по формуле (17)
        double total = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double numerator = p[i] * p[j];
                double denominator = p[i] + p[j];
                total += numerator / denominator;
            }
        }

        // Финальное значение среднего количества сравнений
        double Cn = 0.5 + total;

        System.out.printf("Среднее количество сравнений Cn при n=%d: %.6f%n", n, Cn);
    }
}
