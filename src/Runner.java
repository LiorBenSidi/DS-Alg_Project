public class Runner{
    private RunnerID ID;
    private TwoThreeTree<Run> Runs;
    private float MinTime;
    private float AvgTime;

    public Runner(RunnerID id) {
        this.ID = id;
        this.Runs = new TwoThreeTree<Run>("Time");
        this.MinTime = Float.MAX_VALUE;
        this.AvgTime = Float.MAX_VALUE;
    }

    public RunnerID getID() {
        return ID;
    }

    public void setID(RunnerID ID) {
        this.ID = ID;
    }

    public TwoThreeTree<Run> getRuns() {
        return Runs;
    }

    public void setRuns(TwoThreeTree<Run> runs) {
        Runs = runs;
    }

    public float getMinTime() {
        return MinTime;
    }

    public void setMinTime(float minTime) {
        MinTime = minTime;
    }

    public float getAvgTime() {
        return AvgTime;
    }

    public void setAvgTime(float avgTime) {
        AvgTime = avgTime;
    }
}