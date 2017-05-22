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

    public void setPriority(int priority) {
        Priority = priority;
    }

    int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    int getArrivalTime() {
        return ArrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        ArrivalTime = arrivalTime;
    }

    Queue<MyPair<Resource, Integer>> getResources() {
        return Resources;
    }

    public void setResources(Queue<MyPair<Resource, Integer>> resources) {
        Resources = resources;
    }


}
