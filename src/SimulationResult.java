import java.util.List;

/**
 * Created by Tom�s on 15/05/2017.
 */
class SimulationResult {

    private List<MyPair<Integer, Process>> Result;

    SimulationResult(List<MyPair<Integer, Process>> result) {
        Result = result;
    }

    List<MyPair<Integer, Process>> getResult() {
        return Result;
    }
}
