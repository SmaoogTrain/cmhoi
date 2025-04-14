package lr2;

public class Task24 {
    public static void tasking24(){
        int n = 100;
        double[] p = new double[n];
        double c = 2.0 / (n * (n + 1));

        for (int i = 0; i < n; i++) {
            p[i] = (n - i) * c;
        }

        double total = 0.0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double numerator = p[i] * p[j];
                double denominator = p[i] + p[j];
                total += numerator / denominator;
            }
        }

        double Cn = 1 + 2 * total;

        System.out.printf("Среднее количество сравнений Cn при клиновидном распределении и n = %d: %.6f%n", n, Cn);
    }
}