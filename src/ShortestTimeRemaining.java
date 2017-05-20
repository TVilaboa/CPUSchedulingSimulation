import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Tom�s on 15/05/2017.
 */
public class ShortestTimeRemaining extends SchedulingAlgorithm {
    public ShortestTimeRemaining(String name) {
        super(name);
    }

    @Override
    public SimulationResult Schedule(List<Process> processList) {
        //Una opcion puede ser hacer una lista con todos los ms desde el arriva hasta que terminaria el ultimo y por cada ms evaluar que proceso corresponde.
        // SE guarda cual es el current y cuando cambia se agrega un process al resultado.
        //processList.sort(Comparator.comparingInt(p -> p.getResources().stream().mapToInt(Pair::getValue).sum()));
        processList.sort(Comparator.comparingInt(Process::getArrivalTime));
        for (Process process : processList) {
            int finishTime = process.getArrivalTime() + process.getResources().stream().mapToInt(Pair::getValue).sum();
            Stream<Process> fasterProcesses = processList.stream().filter(p -> p.getArrivalTime() + p.getResources().stream().mapToInt(Pair::getValue).sum() < finishTime);
        }
        List<Pair<Integer, Process>> result = new ArrayList<>();
        processList.forEach((process -> result.add(new Pair<>(process.getResources().stream().mapToInt(Pair::getValue).sum(),
                process))));
        return new SimulationResult(result);
    }
}
