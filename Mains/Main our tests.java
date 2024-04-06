class RunnerIDInt extends RunnerID{
    private int id;
    public RunnerIDInt(int id){
        super();
        this.id = id;
    }
    @Override
    public boolean isSmaller(RunnerID other) {
        return this.id < ((RunnerIDInt)other).id;
    }

    @Override
    public String toString() {
        return String.valueOf(this.id);
    }


}


public class Main {
    public static void main(String[] args) {
        /*
        testAddRunner();
        testLargeDataset();
        testAddRunToRunner();
        testRemoveRunFromRunner();
        testRemoveRunner();
        testGetMinRun();
        testGetAvgRun();
         */
        testGetFastestRunnerAvg();
        testGetFastestRunnerMin();
        /*
        testGetRankAvg();
        testGetRankMin();
        stressTestRace();
        testFastestRunners();

         */
    }

    public static void testAddRunner() {
        System.out.println("Testing addRunner...");

        try {
            RunnerIDInt id1 = new RunnerIDInt(1);
            RunnerIDInt id2 = new RunnerIDInt(2);
            RunnerIDInt id3 = new RunnerIDInt(3);
            Race race = new Race();
            race.addRunner(id1);
            race.addRunner(id2);
            race.addRunToRunner(id1, 100);
            race.addRunToRunner(id2, 100);
            race.addRunToRunner(id2, Float.MAX_VALUE);
            race.getMinRun(id1);
            race.getAvgRun(id1);
            race.removeRunFromRunner(id1, 100);
            race.removeRunFromRunner(id2, 100);
            race.removeRunFromRunner(id2, Float.MAX_VALUE);
            race.removeRunner(id1);
            race.removeRunner(id2);
            race.getAvgRun(id1);
            System.out.println("addRunner test passed.");
        } catch (Exception e) {
            System.out.println("addRunner test failed: " + e.getMessage());
        }
    }

    public static void testAddRunToRunner() {
        System.out.println("Testing addRunToRunner...");

        try {
            RunnerIDInt id1 = new RunnerIDInt(3);
            Race race = new Race();
            race.addRunner(id1);
            race.addRunToRunner(id1, (float) 118.0);
            System.out.println("addRunToRunner test passed.");
        } catch (Exception e) {
            System.out.println("addRunToRunner test failed: " + e.getMessage());
        }
    }

    public static void testRemoveRunFromRunner() {
        System.out.println("Testing removeRunFromRunner...");

        try {
            RunnerIDInt id1 = new RunnerIDInt(3);
            Race race = new Race();
            race.addRunner(id1);
            race.addRunToRunner(id1, (float) 118.0);
            race.removeRunFromRunner(id1, (float) 118.0);
            System.out.println("removeRunFromRunner test passed.");
        } catch (Exception e) {
            System.out.println("removeRunFromRunner test failed: " + e.getMessage());
        }
    }

    public static void testRemoveRunner() {
        System.out.println("Testing removeRunner...");

        try {
            RunnerIDInt id1 = new RunnerIDInt(3);
            Race race = new Race();
            race.addRunner(id1);
            race.removeRunner(id1);
            System.out.println("removeRunner test passed.");
        } catch (Exception e) {
            System.out.println("removeRunner test failed: " + e.getMessage());
        }
    }

    public static void testGetMinRun() {
        System.out.println("Testing getMinRun...");

        try {
            RunnerIDInt id1 = new RunnerIDInt(3);
            Race race = new Race();
            race.addRunner(id1);
            race.addRunToRunner(id1, (float) 118.0);
            float minRun = race.getMinRun(id1);
            System.out.println("getMinRun test passed. Minimum run time: " + minRun);
        } catch (Exception e) {
            System.out.println("getMinRun test failed: " + e.getMessage());
        }
    }

    public static void testGetAvgRun() {
        System.out.println("Testing getAvgRun...");

        try {
            RunnerIDInt id1 = new RunnerIDInt(3);
            Race race = new Race();
            race.addRunner(id1);
            race.addRunToRunner(id1, (float) 118.0);
            float avgRun = race.getAvgRun(id1);
            System.out.println("getAvgRun test passed. Average run time: " + avgRun);
        } catch (Exception e) {
            System.out.println("getAvgRun test failed: " + e.getMessage());
        }
    }

    public static void testGetFastestRunnerAvg() {
        System.out.println("Testing getFastestRunnerAvg...");

        try {
            RunnerIDInt id1 = new RunnerIDInt(3);
            Race race = new Race();
            race.addRunner(id1);
            race.addRunToRunner(id1, (float) 118.0);
            RunnerID fastestRunner = race.getFastestRunnerAvg();
            System.out.println("getFastestRunnerAvg test passed. Fastest runner: " + fastestRunner.toString());
        } catch (Exception e) {
            System.out.println("getFastestRunnerAvg test failed: " + e.getMessage());
        }
    }

    public static void testGetFastestRunnerMin() {
        System.out.println("Testing getFastestRunnerMin...");

        try {
            RunnerIDInt id1 = new RunnerIDInt(3);
            Race race = new Race();
            race.addRunner(id1);
            race.addRunToRunner(id1, (float) 118.0);
            RunnerID fastestRunner = race.getFastestRunnerMin();
            System.out.println("getFastestRunnerMin test passed. Fastest runner: " + fastestRunner.toString());
        } catch (Exception e) {
            System.out.println("getFastestRunnerMin test failed: " + e.getMessage());
        }
    }

    public static void testGetRankAvg() {
        System.out.println("Testing getRankAvg...");

        try {
            RunnerIDInt id1 = new RunnerIDInt(3);
            Race race = new Race();
            race.addRunner(id1);
            race.addRunToRunner(id1, (float) 118.0);
            int rank = race.getRankAvg(id1);
            System.out.println("getRankAvg test passed. Rank: " + rank);
        } catch (Exception e) {
            System.out.println("getRankAvg test failed: " + e.getMessage());
        }
    }

    public static void testGetRankMin() {
        System.out.println("Testing getRankMin...");

        try {
            RunnerIDInt id1 = new RunnerIDInt(3);
            Race race = new Race();
            race.addRunner(id1);
            race.addRunToRunner(id1, (float) 118.0);
            int rank = race.getRankMin(id1);
            System.out.println("getRankMin test passed. Rank: " + rank);
        } catch (Exception e) {
            System.out.println("getRankMin test failed: " + e.getMessage());
        }
    }
    public static void testLargeDataset() {
        System.out.println("Testing with a large dataset...");

        try {
            // Generate a large number of runners
            int numRunners = 1000;
            Race race = new Race();
            for (int i = 0; i < numRunners; i++) {
                RunnerIDInt id = new RunnerIDInt(i);
                race.addRunner(id);
            }

            // Add runs for each runner
            for (int i = 0; i < numRunners; i++) {
                RunnerIDInt id = new RunnerIDInt(i);
                for (int j = 0; j < 10; j++) {
                    float time = (float) (Math.random() * 200); // Random run time between 0 and 200
                    race.addRunToRunner(id, time);
                }
            }

            // Perform various operations and validate results
            // You can add more test cases here as needed

            System.out.println("Large dataset test passed.");
        } catch (Exception e) {
            System.out.println("Large dataset test failed: " + e.getMessage());
        }
    }
    public static void stressTestRace() {
        System.out.println("Starting stress test...");

        try {
            // Create a race instance
            Race race = new Race();

            // Generate a large number of runners
            int numRunners = 10000;
            for (int i = 0; i < numRunners; i++) {
                RunnerIDInt id = new RunnerIDInt(i);
                race.addRunner(id);
            }

            // Add runs for each runner
            for (int i = 0; i < numRunners; i++) {
                RunnerIDInt id = new RunnerIDInt(i);
                for (int j = 0; j < 10; j++) {
                    float time = (float) (Math.random() * 200); // Random run time between 0 and 200
                    race.addRunToRunner(id, time);
                }
            }

            // Perform various operations on the race data structure
            long startTime = System.currentTimeMillis();

            // Example operations: getMinRun, getAvgRun, getFastestRunnerAvg, getFastestRunnerMin, getRankAvg, getRankMin
            for (int i = 0; i < numRunners; i++) {
                RunnerIDInt id = new RunnerIDInt(i);
                float minRun = race.getMinRun(id);
                float avgRun = race.getAvgRun(id);
                RunnerID fastestByAvg = race.getFastestRunnerAvg();
                RunnerID fastestByMin = race.getFastestRunnerMin();
                int rankAvg = race.getRankAvg(id);
                int rankMin = race.getRankMin(id);
            }

            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;

            System.out.println("Stress test completed successfully.");
            System.out.println("Elapsed time: " + elapsedTime + " milliseconds");
        } catch (Exception e) {
            System.out.println("Stress test failed: " + e.getMessage());
        }
    }
    public static void testFastestRunners() {
        System.out.println("Testing fastest runners...");

        try {
            // Create a race instance
            Race race = new Race();

            // Add runners
            RunnerIDInt id1 = new RunnerIDInt(1);
            RunnerIDInt id2 = new RunnerIDInt(2);
            race.addRunner(id1);
            race.addRunner(id2);

            // Add initial runs
            race.addRunToRunner(id1, 100);
            race.addRunToRunner(id2, 110);

            // Display initial fastest runners
            System.out.println("Initial fastest runner by avg: " + race.getFastestRunnerAvg());
            System.out.println("Initial fastest runner by min: " + race.getFastestRunnerMin());

            // Add more runs
            race.addRunToRunner(id1, 300);
            race.addRunToRunner(id2, 120);

            // Display updated fastest runners
            System.out.println("Updated fastest runner by avg: " + race.getFastestRunnerAvg());
            System.out.println("Updated fastest runner by min: " + race.getFastestRunnerMin());

            // Remove runs
            race.removeRunFromRunner(id1, 3);
            race.removeRunFromRunner(id2, 110);

            // Display final fastest runners
            System.out.println("Final fastest runner by avg: " + race.getFastestRunnerAvg());
            System.out.println("Final fastest runner by min: " + race.getFastestRunnerMin());

            System.out.println("Test completed successfully.");
        } catch (Exception e) {
            System.out.println("Test failed: " + e.getMessage());
        }
    }
}

