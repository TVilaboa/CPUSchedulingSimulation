import java.util.List;

/**
 * Created by Tom�s on 15/05/2017.
 */
public abstract class SchedulingAlgorithm {

    private String name;

    SchedulingAlgorithm(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract SimulationResult Schedule(List<Process> processList);
}
