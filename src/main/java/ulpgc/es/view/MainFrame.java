package ulpgc.es.view;

import ulpgc.es.model.Histogram;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    JFreeChartHistogramDisplay display;

    public MainFrame() throws HeadlessException {
        this.setTitle("Histogram");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.display = new JFreeChartHistogramDisplay();
        add(display);
    }

    public void displayHistogram(Histogram histogram) {
        display.display(histogram);
    }
}
