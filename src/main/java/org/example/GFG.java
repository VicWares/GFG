package org.example;// Java Program to Illustrate Working of SwingWorker Class

// Importing required classes
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Dimension2D;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
public class GFG
{
    private static JLabel statusLabel;
    private static JFrame mainFrame;
    private static int FEATURES_COUNT = 4;
    private static int CLASSES_COUNT = 3;
    private static String version = "230127";
    private static Object eval;
    private int x = 100;
    private int y = 100;
    private double accuracy;
    private ArrayList<Dimension> graphPointsList = new ArrayList<>();
    private Dimension2D oldPoint = new Dimension(0,0);
    private Dimension2D newPoint = new Dimension(0,0);;
    private int i;
    static LearningDL4J learningDL4J = new LearningDL4J();
    public static void swingWorkerSample()
    {
        LearningDL4J learningDL4J = new LearningDL4J();
        mainFrame = new JFrame("Swing Worker");
        mainFrame.setSize(400, 400);
        mainFrame.setLayout(new GridLayout(2, 1));
        mainFrame.addWindowListener(new WindowAdapter() {
            // Method
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        statusLabel = new JLabel("Not Completed", JLabel.CENTER);
        mainFrame.add(statusLabel);
        JButton btn = new JButton("Start LearningDL");
        btn.setPreferredSize(new Dimension(5, 5));
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println(
                        "Button clicked, thread started");
                startThread();
            }
        });
        mainFrame.add(btn);
        mainFrame.setVisible(true);
    }
    private static void startThread()
    {
        SwingWorker sw1 = new SwingWorker() {
            @Override
            protected String doInBackground() throws Exception//************************> Defining what thread will do here
            {
                // Defining what thread will do here
//                for (int i = 10; i >= 0; i--) {
//                    Thread.sleep(100);
//                    System.out.println("Value in thread : "
//                            + i);
//                    publish(i);
//                }
                LearningDL4J learningDL4J = new LearningDL4J();
                learningDL4J.loadData();
                String res = "Finished Execution";
                return res;
            }

            // Method
            @Override protected void process(List chunks)
            {
                // define what the event dispatch thread
                // will do with the intermediate results
                // received while the thread is executing
                int val = (int) chunks.get(chunks.size() - 1);
                statusLabel.setText(String.valueOf(val));
            }

            // Method
            @Override protected void done()
            {
                // this method is called when the background
                // thread finishes execution
                try {
                    String statusMsg = (String) get();
                    System.out.println(
                            "Inside done function");
                    statusLabel.setText(statusMsg);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };

        // Executes the swingworker on worker thread
        sw1.execute();
    }
    // Main driver method
    public static void main(String[] args)
    {
        swingWorkerSample();
    }
}
