public class Run {
    private float Time;
    private final RunnerID ID;

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
}
