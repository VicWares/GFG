package org.example;// Java Program to Illustrate Working of SwingWorker Class
/*****************************************************************************************
 * DL4J Example: version 220131A
 *****************************************************************************************/
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;
public class GFG extends ApplicationFrame
{
    private final String title = "JFreeNN";
    // Create a dataset for the line chart
    static XYSeries series = new XYSeries("Real-time data");
    static XYSeriesCollection dataset = new XYSeriesCollection(series);
    public GFG()
    {
        super("JFreeNN");
    }
    public void setDataset(XYSeriesCollection dataset)
    {
        GFG.dataset = dataset;
    }
    // Create the chart
    JFreeChart chart = ChartFactory.createScatterPlot("Scatter Plot", "X-axis", "Y-axis", dataset, PlotOrientation.VERTICAL, true, true, false);
    ChartPanel chartPanel = new ChartPanel(chart);
    private JFrame mainFrame;
    private static int epoch = 0;
    private static double accuracy = 0.0;
    private static Dimension newPoint;
    public void jFreeNN()
    {
        mainFrame = new JFrame("JFreeNNN");
        mainFrame.setSize(1500, 1500);
        mainFrame.add(chartPanel);
        mainFrame.pack();
        mainFrame.setVisible(true);
        // Add a new point to the chart
        JButton btn = new JButton("Start JFreeNN");
        btn.setBounds(40, 100, 100, 60);
        btn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("GFG53 Button clicked, thread started");
                startThread();
            }
        });
        chartPanel.add(btn);
        mainFrame.setVisible(true);
        addPoint();
    }

    private void addPoint()
    {
        {
            series.add(this.epoch, this.accuracy);
            chartPanel.repaint();
        }
    }
    private void startThread()
    {
        SwingWorker sw1 = new SwingWorker()
        {
            @Override
            protected String doInBackground() throws Exception//************************> Defining what SwingWorker thread will do here
            {
                LearningDL4J learningDL4J = new LearningDL4J();
                learningDL4J.loadData();
                return "Leaving SwingWorker thread";
            }
            @Override
            protected void done()// this method is called when the background thread finishes execution
            {
                try {
                    String statusMsg = (String) get();
                    System.out.println("\nInside done function, status => " + statusMsg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
        // Executes the swing worker on worker thread
        sw1.execute();
    }
    public static void main(String[] args)
    {
        new GFG().jFreeNN();
    }
    public static void setEpoch(int epoch)
    {
        GFG.epoch = epoch;
    }
    public static void setAccuracy(double accuracy)
    {
        GFG.accuracy = accuracy;
        series.add(epoch, accuracy);
    }
}
