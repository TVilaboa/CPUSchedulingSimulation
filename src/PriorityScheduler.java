import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Tom�s on 15/05/2017.
 */
public class PriorityScheduler extends SchedulingAlgorithm {
    PriorityScheduler(String name) {
        super(name);
    }

    @Override
    public SimulationResult Schedule(List<Process> processList) {
        List<MyPair<Integer, Process>> result = new ArrayList<>();
        List<Process> running = new ArrayList<>();
        Process empty = new Process(0, 0, 99999, new ConcurrentLinkedQueue<>());
        int totalTime = processList.stream().min(Comparator.comparingInt(Process::getArrivalTime)).get().getArrivalTime()
                + processList.stream().mapToInt(p -> p.getResources().stream().mapToInt(MyPair::getValue).sum()).sum();
        processList.sort(Comparator.comparingInt(p -> p.getResources().stream().mapToInt(MyPair::getValue).sum()));
        ConcurrentLinkedQueue<MyPair<Resource, Integer>> pQueue = new ConcurrentLinkedQueue<>();
        pQueue.add(new MyPair<>(Resource.CPU, Integer.MAX_VALUE));
        final Process[] current = {new Process(0, 0, 99999, pQueue)};

        for (int quantum = 0; quantum < totalTime; quantum++) {


            for (Process process : processList) {
                if (quantum == process.getArrivalTime()) {
                    running.add(process);

                }
            }

            if (running.isEmpty())
                result.add(new MyPair<>(quantum, empty));
            else {

                if (current[0] != null) {

                    running.stream().filter(p -> p.getPriority() > current[0].getPriority())
                            .min(Comparator.comparingInt(p -> p.getResources().stream().mapToInt(MyPair::getValue).sum())).ifPresent(p -> current[0] = p);
                } else {
                    running.stream().min(Comparator.comparingInt(p -> p.getResources().stream().mapToInt(MyPair::getValue).sum())).ifPresent(p -> current[0] = p);

                }


                MyPair<Resource, Integer> resource = current[0].getResources().peek();
                resource.setValue(resource.getValue() - 1);
                current[0].setPriority(current[0].getPriority() > 1 ? current[0].getPriority() - 1 : 1);
                if (resource.getValue() <= 0) {
                    current[0].getResources().remove();
                }
                if (current[0].getResources().isEmpty()) {
                    running.remove(current[0]);
                    current[0] = new Process(0, 0, 99999, pQueue);
                }


                result.add(new MyPair<>(quantum, current[0]));


            }
        }
        return new SimulationResult(result);

    }
}
