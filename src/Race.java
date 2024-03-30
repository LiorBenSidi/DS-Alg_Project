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
        boolean isEmpty = false;

        //checks if the tree is empty
        if (IDTree.getRoot().getKey() == null) {
            isEmpty = true;
        }

        //check if the runner is already exists
        if (!isEmpty && IDTree.search23(IDTree.getRoot(), new Runner(id)) != null) {
            throw new IllegalArgumentException("Runner with ID " + id + " already exists.");
        }

        Runner runner = new Runner(id);
        IDTree.insert23(new Node<>(runner));
        IDTree.setSize(IDTree.getSize() + 1);
        MinTimeTree.insert23(new Node<>(runner));
        MinTimeTree.setSize(MinTimeTree.getSize() + 1);
        AvgTimeTree.insert23(new Node<>(runner));
        AvgTimeTree.setSize(AvgTimeTree.getSize() + 1);
        if (!isEmpty) {
            IDTree.getRoot().setLeaf(false);
            MinTimeTree.getRoot().setLeaf(false);
            AvgTimeTree.getRoot().setLeaf(false);
        }
    }

    public void removeRunner(RunnerID id)
    {
        //checks if the tree is empty
        if (IDTree.getRoot().getKey() == null) {
            return;
        }
        //check if the runner exists
        if (IDTree.search23(IDTree.getRoot(), new Runner(id)) == null) {
            throw new IllegalArgumentException("Runner with ID " + id + " not found.");
        }
        IDTree.delete23(IDTree.search23(IDTree.getRoot(), new Runner(id)));
        IDTree.setSize(IDTree.getSize() - 1);
        MinTimeTree.delete23(MinTimeTree.search23(MinTimeTree.getRoot(), new Runner(id)));
        MinTimeTree.setSize(MinTimeTree.getSize() - 1);
        AvgTimeTree.delete23(AvgTimeTree.search23(AvgTimeTree.getRoot(), new Runner(id)));
        AvgTimeTree.setSize(AvgTimeTree.getSize() - 1);

    }

    //Version 2
    public void addRunToRunner(RunnerID id, float time) {
        // Search for the runner in the IDTree
        Node<Runner> searchResult = IDTree.search23(IDTree.getRoot(), new Runner(id));
        if (searchResult == null || searchResult.getKey() == null) {
            // Handle the case where the runner does not exist in the tree
            System.out.println("Runner with ID " + id + " not found.");
            return; // Exit the method as we can't add a run to a non-existent runner
        }

        // Get the runner from the search result
        Runner runner = searchResult.getKey();

        // Ensure the runner has a Runs TwoThreeTree initialized
        if (runner.getRuns() == null) {
            runner.setRuns(new TwoThreeTree<Run>());
        }

        // Insert the new run time into the Runs TwoThreeTree of the runner
        runner.getRuns().insert23(new Node<>(new Run(time, id))); // Assuming Run has a constructor Run(float time, RunnerID id)

        // Update min time if necessary
        if (runner.getMinTime() == 0 || runner.getMinTime() > time) {
            runner.setMinTime(time);
        }

        // Update average time
        // The size is incremented within the insert23 method, so get the updated size after insertion
        int numberOfRuns = runner.getRuns().getRoot().getSize();
        float totalTimes = runner.getAvgTime() * (numberOfRuns - 1) + time;
        float newAvg = numberOfRuns > 0 ? totalTimes / numberOfRuns : 0;
        runner.setAvgTime(newAvg);

        // Update MinTimeTree and AvgTimeTree if they exist and are not null
        if (MinTimeTree != null || AvgTimeTree != null) {
            // Assume delete23 and insert23 handle re-balancing and re-ordering the tree
            int size = searchResult.getSize();
            IDTree.delete23(searchResult);
            IDTree.insert23(new Node<>(runner));
            MinTimeTree.insert23(new Node<>(runner));
            AvgTimeTree.insert23(new Node<>(runner));
        }
    }
    //Version 1
    /*
    public void addRunToRunner(RunnerID id, float time)
    {
        Node<Runner> searchResult = IDTree.search23(IDTree.getRoot(), new Runner(id));
        if (searchResult == null || searchResult.getKey() == null) {
            // Handle the case where the runner does not exist in the tree
            System.out.println("Runner with ID " + id + " not found.");
            return;
        }
        Runner runner = searchResult.getKey();

        if (runner.getRuns() == null) {
            // Initialize Runs TwoThreeTree if it's null
            runner.setRuns(new TwoThreeTree<>());
        }

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
     */

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
        //return AvgTimeTree.getFastestRunner().getKey().getID();
        //return MinTimeTree.getFastestRunner().getKey().getID();
        if (AvgTimeTree.getFastestRunner() != null) {
            return AvgTimeTree.getFastestRunner().getKey().getID();
        } else {
            throw new IllegalArgumentException("No runners have been added yet.");
        }
    }

    public RunnerID getFastestRunnerMin()
    {
        // TODO: check if this method is in a complexity time O(1)
        //return MinTimeTree.getFastestRunner().getKey().getID();
        if (MinTimeTree.getFastestRunner() != null) {
            return MinTimeTree.getFastestRunner().getKey().getID();
        } else {
            throw new IllegalArgumentException("No runners have been added yet.");
        }
    }

    public float getMinRun(RunnerID id)
    {
        //return IDTree.search23(IDTree.getRoot(), new Runner(id)).getKey().getMinTime();
        Node<Runner> runnerNode = IDTree.search23(IDTree.getRoot(), new Runner(id));
        if (runnerNode != null && runnerNode.getKey() != null) {
            return runnerNode.getKey().getMinTime();
        } else {
            // Handle the case when the runner is not found
            throw new IllegalArgumentException("Runner with ID " + id + " not found.");
        }
    }

    public float getAvgRun(RunnerID id){
        //return IDTree.search23(IDTree.getRoot(), new Runner(id)).getKey().getAvgTime();
        Node<Runner> runnerNode = IDTree.search23(IDTree.getRoot(), new Runner(id));
        if (runnerNode != null && runnerNode.getKey() != null) {
            return runnerNode.getKey().getAvgTime();
        } else {
            // Handle the case when the runner is not found
            throw new IllegalArgumentException("Runner with ID " + id + " not found.");
        }
    }

    public int getRankAvg(RunnerID id)
    {
        // Implement a search method to find the node with the given id
        Node<Runner> node = AvgTimeTree.search23(AvgTimeTree.getRoot(), new Runner(id));
        if (node != null) {
            return AvgTimeTree.Rank(node);
        } else {
            // Handle the case where the runner is not found
            throw new IllegalArgumentException("Runner with ID " + id + " not found.");
        }
    }

    public int getRankMin(RunnerID id)
    {
        // Implement a search method to find the node with the given id
        Node<Runner> node = MinTimeTree.search23(MinTimeTree.getRoot(), new Runner(id));
        if (node != null) {
            return MinTimeTree.Rank(node);
        } else {
            // Handle the case where the runner is not found
            throw new IllegalArgumentException("Runner with ID " + id + " not found.");
        }
    }
}
