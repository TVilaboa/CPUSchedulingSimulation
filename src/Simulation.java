

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

/**
 * Created by Tom�s on 15/05/2017.
 */
public class Simulation {

    public static void main(String[] args) {

        int scale = 1;


        DoSchedule(getProcesses(), scale, new FIFOScheduler("FifoScheduler"));
        DoSchedule(getProcesses(), scale, new ShortestJobFirst("ShortestJobFirstScheduler"));
        DoSchedule(getProcesses(), scale, new ShortestTimeRemainingScheduler("ShortestTimeRemainingScheduler"));

        DoSchedule(getProcesses(), scale, new RoundRobinScheduler("RoundRobinScheduler").setQuantumScale(5));
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
        processes.add(new Process(5, 2, 120, p2Queue));
        processes.add(new Process(5, 3, 130, p3Queue));
        return processes;
    }

    private static void DoSchedule(List<Process> processes, int scale, SchedulingAlgorithm scheduler) {
        System.out.println("Algorithm : " + scheduler.getName());
        int ms = processes.stream().min(Comparator.comparingInt(Process::getArrivalTime)).get().getArrivalTime();
        for (MyPair<Integer, Process> process : scheduler.Schedule(processes).getResult().stream().filter(p -> p.getValue().getID() != 0).collect(Collectors.toList())) {
            int prevMs = ms;
            ms = process.getKey() * scale;
            System.out.println("ms " + prevMs + " - " + ms + " : " + process.getValue().getID());
        }
    }
}
