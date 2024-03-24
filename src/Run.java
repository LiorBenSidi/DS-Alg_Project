public class Run<K> {
    private K runnerID;
    private float time;

    public Run(K runnerID, float time) {
        this.runnerID = runnerID;
        this.time = time;
    }

    public K getRunnerID() {
        return runnerID;
    }

    public void setRunnerID(K runnerID) {
        this.runnerID = runnerID;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }
}
