import java.util.Queue;

/**
 * Created by Tomás on 15/05/2017.
 */
public class Process {

    private int Priority;
    private int ID;
    private int ArrivalTime;
    private Queue<MyPair<Resource, Integer>> Resources;

    Process(int priority, int ID, int arrivalTime, Queue<MyPair<Resource, Integer>> resources) {
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

    Queue<MyPair<Resource, Integer>> getResources() {
        return Resources;
    }


}
