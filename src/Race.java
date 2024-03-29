public class Race {
    private TwoThreeTree<Runner> IDTree;
    private TwoThreeTree<Runner> MinTimeTree;
    private TwoThreeTree<Runner> AvgTimeTree;

    public Race()
    {
        init();
    }

    public void init()
    {
        IDTree = new TwoThreeTree<Runner>("ID");
        MinTimeTree = new TwoThreeTree<Runner>("MinTime");
        AvgTimeTree = new TwoThreeTree<Runner>("AvgTime");
    }

    public void addRunner(RunnerID id)
    {
        IDTree.insert23(new Node<>(new Runner(id)));
        IDTree.setSize(IDTree.getSize() + 1);
        MinTimeTree.insert23(new Node<>(new Runner(id)));
        MinTimeTree.setSize(MinTimeTree.getSize() + 1);
        AvgTimeTree.insert23(new Node<>(new Runner(id)));
        AvgTimeTree.setSize(AvgTimeTree.getSize() + 1);
    }

    public void removeRunner(RunnerID id)
    {
        IDTree.delete23(IDTree.search23(IDTree.getRoot(), new Runner(id)));
        IDTree.setSize(IDTree.getSize() - 1);
        MinTimeTree.delete23(MinTimeTree.search23(MinTimeTree.getRoot(), new Runner(id)));
        MinTimeTree.setSize(MinTimeTree.getSize() - 1);
        AvgTimeTree.delete23(AvgTimeTree.search23(AvgTimeTree.getRoot(), new Runner(id)));
        AvgTimeTree.setSize(AvgTimeTree.getSize() - 1);

    }

    public void addRunToRunner(RunnerID id, float time)
    {
        Runner runner = IDTree.search23(IDTree.getRoot(), new Runner(id)).getKey();
        runner.getRuns().insert23(new Node<>(new Run(time, id)));

        if (runner.getMinTime() == 0) {
            runner.setMinTime(time);
        } else if(runner.getMinTime() > time) {
            runner.setMinTime(time);
        }

        float newAvg = ((runner.getAvgTime() * runner.getRuns().getSize()) + time)/(runner.getRuns().getSize() + 1);
        if (runner.getAvgTime() == 0) {
            runner.setAvgTime(time);
        }
        runner.setAvgTime(newAvg);

        runner.getRuns().setSize(runner.getRuns().getSize() + 1);

        //TODO: check if the update MinTimeTree and AvgTimeTree is valid
    }

    public void removeRunFromRunner(RunnerID id, float time)
    {
        Runner runner = IDTree.search23(IDTree.getRoot(), new Runner(id)).getKey();
        runner.getRuns().delete23(runner.getRuns().search23(runner.getRuns().getRoot(), new Run(time, id)));
        if (runner.getMinTime() == time) {
            runner.setMinTime(runner.getRuns().minimum23().getKey().getTime());
        }

        float newAvg = ((runner.getAvgTime() * runner.getRuns().getSize()) - time)/(runner.getRuns().getSize() - 1);
        runner.setAvgTime(newAvg);

        runner.getRuns().setSize(runner.getRuns().getSize() - 1);

        //TODO: check if the update MinTimeTree and AvgTimeTree is valid
    }

    public RunnerID getFastestRunnerAvg()
    {
        // TODO: check if this method is in a complexity time O(1)
        return AvgTimeTree.getFastestRunner().getKey().getID();
    }

    public RunnerID getFastestRunnerMin()
    {
        // TODO: check if this method is in a complexity time O(1)
        return MinTimeTree.getFastestRunner().getKey().getID();
    }

    public float getMinRun(RunnerID id)
    {
        return IDTree.search23(IDTree.getRoot(), new Runner(id)).getKey().getMinTime();
    }
    public float getAvgRun(RunnerID id){
        return IDTree.search23(IDTree.getRoot(), new Runner(id)).getKey().getAvgTime();
    }

    public int getRankAvg(RunnerID id)
    {
        throw new java.lang.UnsupportedOperationException("not implemented");
    }

    public int getRankMin(RunnerID id)
    {
        throw new java.lang.UnsupportedOperationException("not implemented");
    }
}
