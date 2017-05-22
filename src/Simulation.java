

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

/**
 * Created by Tom�s on 15/05/2017.
 */
public class Simulation extends Application {

    private static List<XYChart.Series<Number, String>> series = new ArrayList<>();

    public static void main(String[] args) {

        int scale = 1;


        DoSchedule(getProcesses(), scale, new FIFOScheduler("FifoScheduler"));
        DoSchedule(getProcesses(), scale, new ShortestJobFirst("ShortestJobFirstScheduler"));
        DoSchedule(getProcesses(), scale, new ShortestTimeRemainingScheduler("ShortestTimeRemainingScheduler"));

        DoSchedule(getProcesses(), scale, new RoundRobinScheduler("RoundRobinScheduler").setQuantumScale(5));
        DoSchedule(getProcesses(), scale, new PriorityScheduler("PriorityScheduler"));
        launch(args);
    }

    private static List<Process> getProcesses() {
        ConcurrentLinkedQueue<MyPair<Resource, Integer>> p1Queue = new ConcurrentLinkedQueue<>();
        p1Queue.add(new MyPair<>(Resource.CPU, 100));
        ConcurrentLinkedQueue<MyPair<Resource, Integer>> p2Queue = new ConcurrentLinkedQueue<>();
        p2Queue.add(new MyPair<>(Resource.CPU, 150));
        ConcurrentLinkedQueue<MyPair<Resource, Integer>> p3Queue = new ConcurrentLinkedQueue<>();
        p3Queue.add(new MyPair<>(Resource.CPU, 10));
        List<Process> processes = new ArrayList<>();
        processes.add(new Process(1, 1, 100, p1Queue));
        processes.add(new Process(50, 2, 120, p2Queue));
        processes.add(new Process(9999, 3, 130, p3Queue));
        return processes;
    }

    private static void DoSchedule(List<Process> processes, int scale, SchedulingAlgorithm scheduler) {
        System.out.println("Algorithm : " + scheduler.getName());
        int ms = processes.stream().min(Comparator.comparingInt(Process::getArrivalTime)).get().getArrivalTime();
        XYChart.Series<Number, String> resultSeries = new XYChart.Series<>();
        resultSeries.setName(scheduler.getName());
        boolean first = true;
        for (MyPair<Integer, Process> process : scheduler.Schedule(processes).getResult().stream().filter(p -> p.getValue().getID() != 0).collect(Collectors.toList())) {

            int prevMs = ms;
            if (first) {
                resultSeries.getData().add(new XYChart.Data<Number, String>(prevMs, "P " + process.getValue().getID()));
                first = false;
            }
            ms = process.getKey() * scale;
            System.out.println("ms " + prevMs + " - " + ms + " : " + process.getValue().getID());
            resultSeries.getData().add(new XYChart.Data<Number, String>(ms, "P " + process.getValue().getID()));
        }
        series.add(resultSeries);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("CPUSchedulingSimulation");
        final CategoryAxis yAxis = new CategoryAxis();
        final NumberAxis xAxis = new NumberAxis();
        yAxis.setLabel("Process");
        xAxis.setLabel("ms");
        final LineChart<Number, String> lineChart =
                new LineChart<Number, String>(xAxis, yAxis);

        lineChart.setTitle("CPUSchedulingSimulation");

        /*XYChart.Series series1 = new XYChart.Series();
        series1.setName("Portfolio 1");

        series1.getData().add(new XYChart.Data("Jan", 23));
        series1.getData().add(new XYChart.Data("Feb", 14));
        series1.getData().add(new XYChart.Data("Mar", 15));
        series1.getData().add(new XYChart.Data("Apr", 24));
        series1.getData().add(new XYChart.Data("May", 34));
        series1.getData().add(new XYChart.Data("Jun", 36));
        series1.getData().add(new XYChart.Data("Jul", 22));
        series1.getData().add(new XYChart.Data("Aug", 45));
        series1.getData().add(new XYChart.Data("Sep", 43));
        series1.getData().add(new XYChart.Data("Oct", 17));
        series1.getData().add(new XYChart.Data("Nov", 29));
        series1.getData().add(new XYChart.Data("Dec", 25));*/


        Scene scene = new Scene(lineChart, 800, 600);
        series.forEach(s -> lineChart.getData().add(s));

        stage.setScene(scene);
        stage.show();
    }
}
