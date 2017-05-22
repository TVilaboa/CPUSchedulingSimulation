import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Tomás on 21/05/2017.
 */
public class RoundRobinScheduler extends SchedulingAlgorithm {

    private int quantumScale = 1;

    RoundRobinScheduler(String name) {
        super(name);
    }

    public int getQuantumScale() {
        return quantumScale;
    }

    RoundRobinScheduler setQuantumScale(int quantumScale) {
        this.quantumScale = quantumScale;
        return this;

    }

    @Override
    public SimulationResult Schedule(List<Process> processList) {
        List<MyPair<Integer, Process>> result = new ArrayList<>();
        Deque<Process> running = new ArrayDeque<>();
        Process empty = new Process(0, 0, 99999, new ConcurrentLinkedQueue<>());
        int totalTime = processList.stream().min(Comparator.comparingInt(Process::getArrivalTime)).get().getArrivalTime()
                + processList.stream().mapToInt(p -> p.getResources().stream().mapToInt(MyPair::getValue).sum()).sum();
        processList.sort(Comparator.comparingInt(p -> p.getResources().stream().mapToInt(MyPair::getValue).sum()));
        for (int quantum = 0; quantum < totalTime; quantum += quantumScale) {
            // Add new arrivals to the list of running processes.

            for (Process process : processList) {
                if (quantum == process.getArrivalTime()) {
                    running.addLast(process);
                }
            }

            if (running.isEmpty())
                result.add(new MyPair<>(quantum + quantumScale, empty)); // Nothing to run
            else {
                // Select the process now at the head.
                Process selectedProcess = running.getFirst();

                // Decrement the running process's burst time. If it's done, remove
                // it from the running list.
                MyPair<Resource, Integer> resource = selectedProcess.getResources().peek();
                resource.setValue(resource.getValue() - quantumScale);
                if (resource.getValue() <= 0) {
                    selectedProcess.getResources().remove();
                }
                if (selectedProcess.getResources().isEmpty()) {
                    running.removeFirst();
                }


                // Record the run for this quantum.
                result.add(new MyPair<>(quantum + quantumScale, selectedProcess));

                // Rotate the previous head to the tail of the list for next quantum.
                if (running.size() > 1)
                    running.addLast(running.removeFirst());
            }
        }
        return new SimulationResult(result);
    }
}
