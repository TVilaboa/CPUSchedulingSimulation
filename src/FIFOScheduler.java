import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Tom�s on 15/05/2017.
 */
public class FIFOScheduler extends SchedulingAlgorithm {
    FIFOScheduler(String name) {
        super(name);
    }

    @Override
    public SimulationResult Schedule(List<Process> processList) {
        processList.sort(Comparator.comparingInt(Process::getArrivalTime));
        List<Pair<Integer, Process>> result = new ArrayList<>();
        processList.forEach((process -> result.add(new Pair<>(process.getResources().stream().mapToInt(Pair::getValue).sum(),
                process))));
        return new SimulationResult(result);
    }
}
