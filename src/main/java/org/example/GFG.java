package org.example;// Java Program to Illustrate Working of SwingWorker Class
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.ExecutionException;
public class GFG extends ApplicationFrame
{
    // Create a dataset for the line chart
    static XYSeries series = new XYSeries("Real-time data");
    static XYSeriesCollection dataset = new XYSeriesCollection(series);
    public GFG(String title)
    {
        super(title);
    }
    public static void setDataset(XYSeriesCollection dataset)
    {
        GFG.dataset = dataset;
    }
    // Create the chart
    static JFreeChart chart = ChartFactory.createXYLineChart("JFreeNN", "Epoch", "Error", dataset, PlotOrientation.VERTICAL, true, true, false);
    static ChartPanel chartPanel = new ChartPanel(chart);
    private static JFrame mainFrame;
    public static void jFreeNN()
    {
        mainFrame = new JFrame("JFreeNNN");
        mainFrame.setSize(1500, 1500);
        mainFrame.add(chartPanel);
        mainFrame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        JButton btn = new JButton("Start JFreeNN");
        btn.setBounds(40,100,100,60);
        btn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Button clicked, thread started");
                startThread();
            }
        });
        chartPanel.add(btn);
        mainFrame.setVisible(true);
    }
    private static void startThread()
    {
        SwingWorker sw1 = new SwingWorker()
        {
            @Override
            protected String doInBackground() throws Exception//************************> Defining what SwingWorker thread will do here
            {
                LearningDL4J learningDL4J = new LearningDL4J();
                learningDL4J.loadData();
                String res = "Finished Execution";
                return res;
            }
            @Override
            protected void done()// this method is called when the background thread finishes execution
            {
                try {
                    String statusMsg = (String) get();
                    System.out.println("Inside done function");
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
        jFreeNN();
    }
}
