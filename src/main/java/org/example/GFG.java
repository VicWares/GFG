package org.example;// Java Program to Illustrate Working of SwingWorker Class
/*****************************************************************************************
 * DL4J Example: version 220131
 *****************************************************************************************/
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
    JFreeChart chart = ChartFactory.createXYLineChart("JFreeNN", "Epoch", "Error", dataset, PlotOrientation.VERTICAL, true, true, false);
    ChartPanel chartPanel = new ChartPanel(chart);
    private JFrame mainFrame;
    public void jFreeNN()
    {
        Timer ticker = new Timer(100, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("\nGFG41 Ticker ticked");
                System.out.println("GFG42 Epoch: " + LearningDL4J.getEpoch() + " Accuracy: " + LearningDL4J.getAccuracy());
                series.add(LearningDL4J.getEpoch(), LearningDL4J.getAccuracy());
            }
        });
        ticker.start();
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
}
