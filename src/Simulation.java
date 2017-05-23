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
        int prevProcessId = 0;
        int totalTime = processes.stream().min(Comparator.comparingInt(Process::getArrivalTime)).get().getArrivalTime()
                + processes.stream().mapToInt(p -> p.getResources().stream().mapToInt(MyPair::getValue).sum()).sum();
        for (MyPair<Integer, Process> process : scheduler.Schedule(processes).getResult().stream().filter(p -> p.getValue().getID() != 0).collect(Collectors.toList())) {

            int prevMs = ms;
            if (first) {
                resultSeries.getData().add(new XYChart.Data<>(prevMs, "P " + process.getValue().getID()));
                first = false;
            }
            ms = process.getKey() * scale;
            System.out.println("ms " + prevMs + " - " + ms + " : " + process.getValue().getID());
            if (process.getValue().getID() != prevProcessId || process.getKey() >= totalTime * 0.98) {
                resultSeries.getData().add(new XYChart.Data<>(ms, "P " + process.getValue().getID()));
                prevProcessId = process.getValue().getID();
            }
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
                new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("CPUSchedulingSimulation");




        Scene scene = new Scene(lineChart, 800, 600);
       /* EventHandler<MouseEvent> onMouseEnteredSeriesListener =
                (MouseEvent event) -> {
                    ((Node) (event.getSource())).setVisible(!((Node) (event.getSource())).isVisible());
                };*/
        series.forEach(s -> {

            lineChart.getData().add(s);
            // s.getNode().setOnMouseClicked(onMouseEnteredSeriesListener);
        });

        stage.setScene(scene);
        stage.show();
    }
}
