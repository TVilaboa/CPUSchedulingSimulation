import javafx.util.Pair;

import java.util.List;

/**
 * Created by Tom�s on 15/05/2017.
 */
class SimulationResult {

    private List<Pair<Integer, Process>> Result;

    SimulationResult(List<Pair<Integer, Process>> result) {
        Result = result;
    }

    List<Pair<Integer, Process>> getResult() {
        return Result;
    }
}
