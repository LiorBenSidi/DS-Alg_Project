public class Run {
    private RunnerID runnerID;

    public RunnerID getRunnerID() {
        return runnerID;
    }

    public void setRunnerID(RunnerID runnerID) {
        this.runnerID = runnerID;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    private float time;

    public Run(RunnerID runnerID, float time) {
        this.runnerID = runnerID;
        this.time = time;
    }
}
