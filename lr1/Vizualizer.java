package lr1;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.DefaultCategoryDataset;

public class Vizualizer extends ApplicationFrame {

    public Vizualizer(String title, double[] probabilities, String distName) {
        super(title);
        JFreeChart barChart = ChartFactory.createBarChart(
                distName,
                "Индекс",
                "Вероятность",
                createDataset(probabilities)
        );

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1000, 600));
        setContentPane(chartPanel);
    }

    private DefaultCategoryDataset createDataset(double[] probabilities) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < probabilities.length; i += probabilities.length/300) {
            dataset.addValue(probabilities[i], "P(X=Ki)", String.valueOf(i));
        }
        return dataset;
    }

    public static void showChart(double[] probabilities, String distName) {
        Vizualizer chart = new Vizualizer("Распределение: " + distName, probabilities, distName);
        chart.pack();
        chart.setVisible(true);
    }
}