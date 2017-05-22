import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Tom�s on 15/05/2017.
 */
public class ShortestJobFirst extends SchedulingAlgorithm {
    ShortestJobFirst(String name) {
        super(name);
    }

    @Override
    public SimulationResult Schedule(List<Process> processList) {
        processList.sort(Comparator.comparingInt(p -> p.getResources().stream().mapToInt(MyPair::getValue).sum()));
        List<MyPair<Integer, Process>> result = new ArrayList<>();
        processList.forEach((process -> result.add(new MyPair<>(process.getResources().stream().mapToInt(MyPair::getValue).sum(),
                process))));
        return new SimulationResult(result);
    }
}
