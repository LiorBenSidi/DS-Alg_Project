public class Race {
    private TwoThreeTree<Runner> IDTree; //Tree to store the runners
    private TwoThreeTree<Runner> MinTimeTree; //Tree to store the runners sorted by min time
    private TwoThreeTree<Runner> AvgTimeTree; //Tree to store the runners sorted by avg time

    public Race()
    {
        init();
    }

    public void init()
    {
        IDTree = new TwoThreeTree<Runner>("ID"); //Tree to store the runners
        MinTimeTree = new TwoThreeTree<Runner>("MinTime"); //Tree to store the runners sorted by min time
        AvgTimeTree = new TwoThreeTree<Runner>("AvgTime"); //Tree to store the runners sorted by avg time
    }

    public void addRunner(RunnerID id)
    {
        boolean isEmpty = false; //checks if the tree is empty

        //checks if the tree is empty
        if (IDTree.getRoot().getLeft().isMinNode(IDTree.MIN_SENTINEL) &&
                IDTree.getRoot().getMiddle().isMaxNode(IDTree.MAX_SENTINEL)) {
            isEmpty = true;
        }

        //check if the runner is already exists
        if (!isEmpty && IDTree.search23(IDTree.getRoot(), new Runner(id)) != null) {
            throw new IllegalArgumentException("Runner with ID " + id + " already exists.");
        }

        IDTree.insert23(new Runner(id)); //inserts the runner to the IDTree
        IDTree.setSize(IDTree.getSize() + 1); //increments the size of the IDTree

        MinTimeTree.insert23(new Runner(id)); //inserts the runner to the MinTimeTree
        MinTimeTree.setSize(MinTimeTree.getSize() + 1); //increments the size of the MinTimeTree

        AvgTimeTree.insert23(new Runner(id)); //inserts the runner to the AvgTimeTree
        AvgTimeTree.setSize(AvgTimeTree.getSize() + 1); //increments the size of the AvgTimeTree

        if (!isEmpty) { //if the tree is not empty
            IDTree.getRoot().setLeaf(false); //set the root of the IDTree to not be a leaf
            MinTimeTree.getRoot().setLeaf(false); //set the root of the MinTimeTree to not be a leaf
            AvgTimeTree.getRoot().setLeaf(false); //set the root of the AvgTimeTree to not be a leaf
        }
    }

    public void removeRunner(RunnerID id)
    {
        boolean isEmpty = false;

        //checks if the runners tree is empty
        if (IDTree.getRoot().getLeft().isMinNode(IDTree.MIN_SENTINEL) &&
                IDTree.getRoot().getMiddle().isMaxNode(IDTree.MAX_SENTINEL)) {
            isEmpty = true;
        }

        //check if the runner is already exists
        if (!isEmpty && IDTree.search23(IDTree.getRoot(), new Runner(id)) == null) {
            throw new IllegalArgumentException("Runner with ID " + id + " already exists.");
        }

        //deletes the runner from the IDTree
        IDTree.delete23(IDTree.search23(IDTree.getRoot(), new Runner(id)).getKey(),
                null, false);
        IDTree.setSize(IDTree.getSize() - 1); //decrements the size of the IDTree

        //deletes the runner from the MinTimeTree
        MinTimeTree.delete23(MinTimeTree.search23(MinTimeTree.getRoot(), new Runner(id)).getKey(),
                null, false);
        MinTimeTree.setSize(MinTimeTree.getSize() - 1); //decrements the size of the MinTimeTree

        //deletes the runner from the AvgTimeTree
        AvgTimeTree.delete23(AvgTimeTree.search23(AvgTimeTree.getRoot(), new Runner(id)).getKey(),
                null, false);
        AvgTimeTree.setSize(AvgTimeTree.getSize() - 1); //decrements the size of the AvgTimeTree
    }

    //Version 2
    public void addRunToRunner(RunnerID id, float time) {
        if (time <= 0) { //checks if the time is positive
            throw new IllegalArgumentException("the given time is not positive");
        }

        // Search for the runner in the IDTree
        Node<Runner> searchResult = IDTree.search23(IDTree.getRoot(), new Runner(id));
        if (searchResult == null || searchResult.getKey() == null) { //checks if the runner exists
            throw new IllegalArgumentException("Runner with ID " + id + " not found.");
        }

        Runner runnerID = searchResult.getKey(); //gets the runner of the IDTree from the search result
        Node<Runner> searchResultMinTime = MinTimeTree.search23(MinTimeTree.getRoot(), runnerID); //searches for the runner in the MinTimeTree
        Runner runnerMimTime = searchResultMinTime.getKey(); //gets the runner of the MinTimeTree from the search result
        Node<Runner> searchResultAvgTime = AvgTimeTree.search23(AvgTimeTree.getRoot(), runnerID); //searches for the runner in the AvgTimeTree
        Runner runnerAvgTime = searchResultAvgTime.getKey(); //gets the runner of the AvgTimeTree from the search result

        runnerID.getRuns().insert23(new Run(time, id)); //inserts the run to the runner of the IDTree
        runnerID.getRuns().setSize(runnerID.getRuns().getSize() + 1); //increments the size of the runs of the runner of the IDTree

        runnerMimTime.getRuns().insert23(new Run(time, id)); //inserts the run to the runner of the MinTimeTree
        runnerMimTime.getRuns().setSize(runnerMimTime.getRuns().getSize() + 1); //increments the size of the runs of the runner of the MinTimeTree

        runnerAvgTime.getRuns().insert23(new Run(time, id)); //inserts the run to the runner of the AvgTimeTree
        runnerAvgTime.getRuns().setSize(runnerAvgTime.getRuns().getSize() + 1); //increments the size of the runs of the runner of the AvgTimeTree

        if (runnerID.getMinTime() > time) { //checks if the given time is the minimum time
            runnerID.setMinTime(time); //sets the minimum time of the runner of the IDTree
            runnerMimTime.setMinTime(time); //sets the minimum time of the runner of the MinTimeTree
            runnerAvgTime.setMinTime(time); //sets the minimum time of the runner of the AvgTimeTree
        }

        int numberOfRuns = runnerID.getRuns().getRoot().getSize(); //gets the number of runs of the runner
        float totalTimes = runnerID.getAvgTime() * (numberOfRuns - 1) + time; //calculates the total time of the runs
        float newAvg = numberOfRuns > 0 ? totalTimes / numberOfRuns : Float.MAX_VALUE; //calculates the new average time
        runnerID.setAvgTime(newAvg); //sets the average time of the runner of the IDTree
        runnerMimTime.setAvgTime(newAvg); //sets the average time of the runner of the MinTimeTree
        runnerAvgTime.setAvgTime(newAvg); //sets the average time of the runner of the AvgTimeTree

        // re-balancing and re-ordering each of the 3 trees after the insertion of the run
        IDTree.delete23(runnerID, null, false); //deletes the runner from the IDTree
        IDTree.insert23(runnerID); //inserts the runner to the IDTree

        MinTimeTree.delete23(runnerMimTime, searchResultMinTime, true); //deletes the runner from the MinTimeTree
        MinTimeTree.insert23(runnerMimTime); //inserts the runner to the MinTimeTree

        AvgTimeTree.delete23(runnerAvgTime, searchResultAvgTime, true); //deletes the runner from the AvgTimeTree
        AvgTimeTree.insert23(runnerAvgTime); //inserts the runner to the AvgTimeTree
    }

    public void removeRunFromRunner(RunnerID id, float time)
    {
        if (time <= 0) { //checks if the time is positive
            throw new IllegalArgumentException("the given time is not positive");
        }

        Node<Runner> searchResult = IDTree.search23(IDTree.getRoot(), new Runner(id)); //searches for the runner in the IDTree
        if (searchResult == null) { //checks if the runner exists
            throw new IllegalArgumentException("Runner with ID " + id + " not found.");
        }

        TwoThreeTree<Run> runs = searchResult.getKey().getRuns(); //gets the runs tree of the runner from the search result

        Node<Run> searchResultRun = runs.search23(runs.getRoot(), new Run(time, id)); //searches for the run in the runs tree
        if (searchResultRun == null) { //checks if the run exists for the runner
            throw new IllegalArgumentException("Run with time " + time +
                                               " not found for Runner " + id + ".");
        }

        //gets the runner of the IDTree from the search result
        Runner runnerID = searchResult.getKey();

        //searches for the runner in the MinTimeTree
        Node<Runner> searchResultMinTime = MinTimeTree.search23(MinTimeTree.getRoot(), runnerID);
        //gets the runner of the MinTimeTree from the search result
        Runner runnerMimTime = searchResultMinTime.getKey();
        //searches for the run in the runs tree of the runner of the MinTimeTree
        Node<Run> searchResultRunMinTime = runnerMimTime.getRuns().search23(runnerMimTime.getRuns().getRoot(), searchResultRun.getKey());

        //searches for the runner in the AvgTimeTree
        Node<Runner> searchResultAvgTime = AvgTimeTree.search23(AvgTimeTree.getRoot(), runnerID);
        //gets the runner of the AvgTimeTree from the search result
        Runner runnerAvgTime = searchResultAvgTime.getKey();
        //searches for the run in the runs tree of the runner of the AvgTimeTree
        Node<Run> searchResultRunAvgTime = runnerAvgTime.getRuns().search23(runnerAvgTime.getRuns().getRoot(), searchResultRun.getKey());

        // Delete the run from the runner's runs tree in the IDTree
        runnerID.getRuns().delete23(searchResultRun.getKey(), searchResultRun, true);
        runnerID.getRuns().setSize(runnerID.getRuns().getSize() - 1); //decrements the size of the runs of the runner of the IDTree

        // Delete the run from the runner's runs tree in the MinTimeTree
        runnerMimTime.getRuns().delete23(searchResultRun.getKey(), searchResultRunMinTime, true);
        runnerMimTime.getRuns().setSize(runnerMimTime.getRuns().getSize() - 1); //decrements the size of the runs of the runner of the MinTimeTree

        // Delete the run from the runner's runs tree in the AvgTimeTree
        runnerAvgTime.getRuns().delete23(searchResultRun.getKey(), searchResultRunAvgTime, true);
        runnerAvgTime.getRuns().setSize(runnerAvgTime.getRuns().getSize() - 1); //decrements the size of the runs of the runner of the AvgTimeTree

        if (runnerID.getMinTime() == time) { //checks if the given time is the minimum time
            Node<Run> currentFastestRunner = runnerID.getRuns().getFastestRunner(); //gets the current fastest runner
            //sets the minimum time of the runner of the IDTree, MinTimeTree, and AvgTimeTree
            float newMinTime = currentFastestRunner == null ? Float.MAX_VALUE : currentFastestRunner.getKey().getTime();
            runnerID.setMinTime(newMinTime);
            runnerMimTime.setMinTime(newMinTime);
            runnerAvgTime.setMinTime(newMinTime);
        }

        int numberOfRuns = runnerID.getRuns().getRoot().getSize(); //gets the number of runs of the runner
        float totalTimes = runnerID.getAvgTime() * (numberOfRuns + 1) - time; //calculates the total time of the runs
        float newAvg = numberOfRuns > 0 ? totalTimes / numberOfRuns : Float.MAX_VALUE; //calculates the new average time
        runnerID.setAvgTime(newAvg); //sets the average time of the runner of the IDTree
        runnerMimTime.setAvgTime(newAvg); //sets the average time of the runner of the MinTimeTree
        runnerAvgTime.setAvgTime(newAvg); //sets the average time of the runner of the AvgTimeTree

        // re-balancing and re-ordering each of the 3 trees after the deletion of the run
        IDTree.delete23(runnerID, null, false); //deletes the runner from the IDTree
        IDTree.insert23(runnerID); //inserts the runner to the IDTree

        MinTimeTree.delete23(runnerMimTime, searchResultMinTime, true); //deletes the runner from the MinTimeTree
        MinTimeTree.insert23(runnerMimTime); //inserts the runner to the MinTimeTree

        AvgTimeTree.delete23(runnerAvgTime, searchResultAvgTime, true); //deletes the runner from the AvgTimeTree
        AvgTimeTree.insert23(runnerAvgTime); //inserts the runner to the AvgTimeTree

    }

    public RunnerID getFastestRunnerAvg()
    {
        //checks if the runners tree is empty
        if (IDTree.getRoot().getLeft().isMinNode(IDTree.MIN_SENTINEL) &&
                IDTree.getRoot().getMiddle().isMaxNode(IDTree.MAX_SENTINEL)) {
            throw new IllegalArgumentException("No runners have been added yet.");
        } else { //if the runners tree is not empty, get the fastest runner of the AvgTimeTree
            System.out.println(AvgTimeTree.getFastestRunner().getKey().getID()); //TODO: delete
            return AvgTimeTree.getFastestRunner().getKey().getID();
        }
    }

    public RunnerID getFastestRunnerMin()
    {
        //checks if the runners tree is empty
        if (IDTree.getRoot().getLeft().isMinNode(IDTree.MIN_SENTINEL) &&
                IDTree.getRoot().getMiddle().isMaxNode(IDTree.MAX_SENTINEL)) {
            throw new IllegalArgumentException("No runners have been added yet.");
        } else { //if the runners tree is not empty, get the fastest runner of the MinTimeTree
            System.out.println(MinTimeTree.getFastestRunner().getKey().getID()); //TODO: delete
            return MinTimeTree.getFastestRunner().getKey().getID();
        }
    }

    public void checkRunnerAndRunExists(RunnerID id, Node<Runner> runnerNode) {
        if (runnerNode == null) { //checks if the runner exists
            throw new IllegalArgumentException("Runner with ID " + id + " not found.");
        }

        Node<Run> runRoot = runnerNode.getKey().getRuns().getRoot(); //gets the root of the runs tree of the runner
        //checks if the runner has no runs
        if (runRoot.getLeft().isMinNode(runnerNode.getKey().getRuns().MIN_SENTINEL) &&
                runRoot.getMiddle().isMaxNode(runnerNode.getKey().getRuns().MAX_SENTINEL)) {
            throw new IllegalArgumentException("No runs have been added for runner with ID " + id + ".");
        }
    }

    public float getMinRun(RunnerID id)
    {
        Node<Runner> runnerNode = IDTree.search23(IDTree.getRoot(), new Runner(id)); //searches for the runner in the IDTree
        checkRunnerAndRunExists(id, runnerNode); //checks if the runner exists and has runs

        System.out.println(runnerNode.getKey().getMinTime()); //TODO: delete
        return runnerNode.getKey().getMinTime();
    }

    public float getAvgRun(RunnerID id){
        Node<Runner> runnerNode = IDTree.search23(IDTree.getRoot(), new Runner(id)); //searches for the runner in the IDTree
        checkRunnerAndRunExists(id, runnerNode); //checks if the runner exists and has runs

        System.out.println(runnerNode.getKey().getAvgTime()); //TODO: delete
        return runnerNode.getKey().getAvgTime();
    }

    public int getRankAvg(RunnerID id)
    {
        Runner runnerNode = IDTree.search23(IDTree.getRoot(), new Runner(id)).getKey(); //searches for the runner in the IDTree
        Node<Runner> node = AvgTimeTree.search23(AvgTimeTree.getRoot(), runnerNode); //searches for the runner in the AvgTimeTree
        if (node != null) { //checks if the runner exists
            System.out.println(AvgTimeTree.Rank(node)); //TODO: delete
            return AvgTimeTree.Rank(node);
        } else { //if the runner does not exist
            throw new IllegalArgumentException("Runner with ID " + id + " not found.");
        }
    }

    public int getRankMin(RunnerID id)
    {
        Runner runnerNode = IDTree.search23(IDTree.getRoot(), new Runner(id)).getKey(); //searches for the runner in the IDTree
        Node<Runner> node = MinTimeTree.search23(MinTimeTree.getRoot(), runnerNode); //searches for the runner in the MinTimeTree
        if (node != null) { //checks if the runner exists
            System.out.println(MinTimeTree.Rank(node)); //TODO: delete
            return MinTimeTree.Rank(node);
        } else { //if the runner does not exist
            throw new IllegalArgumentException("Runner with ID " + id + " not found.");
        }
    }
}
