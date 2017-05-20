import javafx.util.Pair;

import java.util.Queue;

/**
 * Created by Tom�s on 15/05/2017.
 */
public class Process {

    private int Priority;
    private int ID;
    private int ArrivalTime;
    private Queue<Pair<Resource, Integer>> Resources;

    Process(int priority, int ID, int arrivalTime, Queue<Pair<Resource, Integer>> resources) {
        Priority = priority;
        this.ID = ID;
        ArrivalTime = arrivalTime;
        Resources = resources;
    }

    public int getPriority() {
        return Priority;
    }

    int getID() {
        return ID;
    }

    int getArrivalTime() {
        return ArrivalTime;
    }

    Queue<Pair<Resource, Integer>> getResources() {
        return Resources;
    }
}
