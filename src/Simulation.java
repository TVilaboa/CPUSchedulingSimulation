

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Tom�s on 15/05/2017.
 */
public class Simulation {

    public static void main(String[] args) {
        ConcurrentLinkedQueue<Pair<Resource, Integer>> p1Queue = new ConcurrentLinkedQueue<>();
        p1Queue.add(new Pair<>(Resource.CPU, 100));
        ConcurrentLinkedQueue<Pair<Resource, Integer>> p2Queue = new ConcurrentLinkedQueue<>();
        p2Queue.add(new Pair<>(Resource.CPU, 150));
        ConcurrentLinkedQueue<Pair<Resource, Integer>> p3Queue = new ConcurrentLinkedQueue<>();
        p3Queue.add(new Pair<>(Resource.CPU, 10));
        List<Process> processes = new ArrayList<>();
        processes.add(new Process(1, 1, 100, p1Queue));
        processes.add(new Process(5, 2, 120, p2Queue));
        processes.add(new Process(5, 3, 130, p3Queue));
        int scale = 1;


        DoSchedule(processes, scale, new FIFOScheduler("FifoScheduler"));
        DoSchedule(processes, scale, new ShortestJobFirst("ShortestJobFirstScheduler"));
    }

    private static void DoSchedule(List<Process> processes, int scale, SchedulingAlgorithm scheduler) {
        System.out.println("Algorithm : " + scheduler.getName());
        int ms = processes.stream().min(Comparator.comparingInt(Process::getArrivalTime)).get().getArrivalTime();
        for (Pair<Integer, Process> process : scheduler.Schedule(processes).getResult()) {
            int prevMs = ms;
            ms += process.getKey() * scale;
            System.out.println("ms " + prevMs + " - " + ms + " : " + process.getValue().getID());
        }
    }
}
