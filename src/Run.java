public class Run {
    private float Time; //Time of the run
    private final RunnerID ID; //ID of the runner

    public Run(float time, RunnerID id) {
        Time = time;
        ID = id;
    }

    public float getTime() {
        return Time;
    }

    public void setTime(float time) {
        Time = time;
    }

    public RunnerID getID() {
        return ID;
    }
}
