interface InlineExpression {
    void call();
}

class RunnerIDInt extends RunnerID {
    static int isSmallerCount = 0;
    private final int id;

    public RunnerIDInt(int id) {
        super();
        this.id = id;
    }

    static int popIsSmallerCount() {
        int count = isSmallerCount;
        isSmallerCount = 0;
        return count;
    }

    @Override
    public boolean isSmaller(RunnerID other) {
        isSmallerCount += 1;
        return this.id < ((RunnerIDInt) other).id;
    }

    @Override
    public String toString() {
        return String.valueOf(this.id);
    }

}

public class Main {
    private static final boolean PRINT_SUCCESS = false;
    private static final boolean PRINT_RUN_TIME = true;
    private static final float DEFAULT_RUN_TIME = Float.MAX_VALUE;

    public static void main(String[] args) {
        // The ids which we will check will not necessarily be RunnerIDInt
        // This is just for the example
        int[] ranksByAvg;
        int[] ranksByMin;
        float[][] runs = {
                {},
                {},
                {},
                {},
                {},
        };
        float[][] initialRuns = runs;

        Race race = new Race();
        assertIllegalArgumentException("Trying to add runners before calling init()", () -> {
            addRunners(race, initialRuns.length);
        });
        assertIllegalArgumentException("Trying to get fastest runner before calling init()", race::getFastestRunnerMin);

        resetRuntimeOfRunnerID();
        race.init();
        printRuntimeOfRunnerID(0);
        assertIllegalArgumentException("Trying to get runner avg before adding runners", () -> {
            race.getAvgRun(new RunnerIDInt(1));
        });
        assertIllegalArgumentException("Trying to get fastest runner before adding runners", race::getFastestRunnerAvg);

        System.out.println("\nAdding Runners...");
        RunnerIDInt[] runnerIDs = addRunners(race, runs.length);
        RunnerIDInt[] initialRunnerIDs = runnerIDs;
        for (RunnerID runnerID : runnerIDs) {
            System.out.print(runnerID + ", ");
        }
        System.out.println("\n");

        assertIllegalArgumentException("Trying to get avg of non existing runner", () -> {
            race.getAvgRun(new RunnerIDInt(1));
        });
        assertIllegalArgumentException("Trying to remove runner that does not exists", () -> {
            race.removeRunner(new RunnerIDInt(1));
        });
        assertIllegalArgumentException("Trying to remove run from a missing runner", () -> {
            race.removeRunFromRunner(new RunnerIDInt(1), 1f);
        });
        assertIllegalArgumentException("Trying to remove run that does not exists", () -> {
            race.removeRunFromRunner(initialRunnerIDs[0], 1337.420f);
        });
        assertIllegalArgumentException("Trying to add negative run", () -> {
            race.addRunToRunner(initialRunnerIDs[0], -1337.420f);
        });
        if (PRINT_SUCCESS) System.out.println();

        ranksByMin = new int[]{3, 2, 4, 1, 5};
        ranksByAvg = new int[]{3, 2, 4, 1, 5};
        testRuns(race, runnerIDs, runs, ranksByMin, ranksByAvg, runnerIDs[3], runnerIDs[3]);

        System.out.println("\nAdding Runs...\n");
        runs = new float[][]{
                {20.0f, 24.2f, 42.1f, 27f, 32.9f},
                {0.01f, 121.2f, 222.1f, 59f, 335.9f},
                {0.01f, 121.2f, 222.1f, 59f, 335.9f},
                {32.0f, 21.2f, 22.1f, 0.2f, 59f, 35.9f, 0.3f},
                {1000f, 2000f, 3000f, 4000f},
        };
        addRuns(race, runnerIDs, runs);

        ranksByMin = new int[]{4, 1, 2, 3, 5};
        ranksByAvg = new int[]{2, 3, 4, 1, 5};
        testRuns(race, runnerIDs, runs, ranksByMin, ranksByAvg, runnerIDs[1], runnerIDs[3]);

        System.out.println("\nRemoving Invalid Runs...\n");
        removeInvalidRuns(race, runnerIDs, runs);
        runs = new float[][]{
                {20.0f, 24.2f, 42.1f, 27f, 32.9f},
                {121.2f, 222.1f, 59f, 335.9f},
                {121.2f, 222.1f, 59f, 335.9f},
                {32.0f, 21.2f, 22.1f, 59f, 35.9f,},
                {1000f, 2000f, 3000f, 4000f},
        };

        ranksByMin = new int[]{1, 3, 4, 2, 5};
        ranksByAvg = new int[]{1, 3, 4, 2, 5};
        testRuns(race, runnerIDs, runs, ranksByMin, ranksByAvg, runnerIDs[0], runnerIDs[0]);

        System.out.println("\nRemoving Invalid Runners...\n");
        removeRunner(race, runnerIDs[1], runnerIDs.length);
        removeRunner(race, runnerIDs[4], runnerIDs.length);
        runnerIDs = new RunnerIDInt[]{runnerIDs[0], runnerIDs[2], runnerIDs[3]};
        runs = new float[][]{
                {20.0f, 24.2f, 42.1f, 27f, 32.9f},
                {121.2f, 222.1f, 59f, 335.9f},
                {32.0f, 21.2f, 22.1f, 59f, 35.9f,},
        };

        ranksByMin = new int[]{1, 3, 2};
        ranksByAvg = new int[]{1, 3, 2};
        testRuns(race, runnerIDs, runs, ranksByMin, ranksByAvg, runnerIDs[0], runnerIDs[0]);
    }

    private static void assertIllegalArgumentException(String prefix, InlineExpression expression) {
        try {
            expression.call();
            System.out.println(prefix + " - ERROR - exception was not thrown");
        } catch (IllegalArgumentException err) {
            if (PRINT_SUCCESS) System.out.println(prefix + " - HANDLED - " + err);
        }
    }

    private static <T> void assertEquals(String prefix, T expected, T actual) {
        if (expected != actual) {
            System.out.println(prefix + " - " + expected + " - FALSE - Got " + actual);
        } else if (PRINT_SUCCESS) {
            System.out.println(prefix + " - " + expected + " - TRUE");
        }
    }

    private static void assertEquals(String prefix, float expected, float actual) {
        double diff = (double) expected - (double) actual;
        if (expected != actual && (diff < -0.00002 || 0.00002 < diff)) {
            System.out.println(prefix + " - " + expected + " - FALSE - Got " + actual);
        } else if (PRINT_SUCCESS) {
            System.out.println(prefix + " - " + expected + " - TRUE");
        }
    }

    private static void resetRuntimeOfRunnerID() {
        RunnerIDInt.popIsSmallerCount();
    }

    private static void printRuntimeOfRunnerID(double expected) {
        int expectedRuntime = (int) Math.ceil(expected);
        int actualRuntime = RunnerIDInt.popIsSmallerCount();
        if (PRINT_SUCCESS && PRINT_RUN_TIME) {
            System.out.println("isSmaller was expected to be called O(" + expectedRuntime + ") times - Got " + actualRuntime);
        }
    }

    private static float getMin(float[] arr) {
        if (arr.length == 0) {
            return DEFAULT_RUN_TIME;
        }
        float min = arr[0];
        for (float v : arr) {
            if (v < min) {
                min = v;
            }
        }
        return min;
    }

    private static float getAvg(float[] arr) {
        if (arr.length == 0) {
            return DEFAULT_RUN_TIME;
        }
        float sum = 0;
        for (float v : arr) {
            sum += v;
        }
        return sum / arr.length;
    }

    private static RunnerIDInt[] addRunners(Race race, int runnersCount) {
        resetRuntimeOfRunnerID();
        RunnerIDInt[] runnerIDs = new RunnerIDInt[runnersCount];
        for (int i = 0; i < runnersCount; ++i) {
            runnerIDs[i] = new RunnerIDInt(50000 + i * (1 - 2 * (i % 2)));
            race.addRunner(runnerIDs[i]);
            printRuntimeOfRunnerID(Math.log(i + 1));
        }
        return runnerIDs;
    }

    private static void addRuns(Race race, RunnerIDInt[] runnerIDs, float[][] runs) {
        assert runnerIDs.length == runs.length;
        resetRuntimeOfRunnerID();
        for (int i = 0; i < runnerIDs.length; ++i) {
            for (int r = 0; r < runs[i].length; ++r) {
                race.addRunToRunner(runnerIDs[i], runs[i][r]);
                printRuntimeOfRunnerID(Math.log(runs.length));
            }
        }
    }

    private static void removeInvalidRuns(Race race, RunnerIDInt[] runnerIDs, float[][] runs) {
        assert runnerIDs.length == runs.length;
        resetRuntimeOfRunnerID();
        for (int i = 0; i < runnerIDs.length; ++i) {
            for (int r = 0; r < runs[i].length; ++r) {
                if (runs[i][r] < 1f) {
                    race.removeRunFromRunner(runnerIDs[i], runs[i][r]);
                    printRuntimeOfRunnerID(Math.log(runs.length));
                }
            }
        }
    }

    private static void removeRunner(Race race, RunnerIDInt runnerID, int runnersCount) {
        resetRuntimeOfRunnerID();
        race.removeRunner(runnerID);
        printRuntimeOfRunnerID(Math.log(runnersCount));
    }

    private static void testRuns(Race race, RunnerIDInt[] runnerIDs, float[][] runs, int[] ranksByMin, int[] ranksByAvg, RunnerID fastestByMin, RunnerID fastestByAvg) {
        resetRuntimeOfRunnerID();
        for (int i = 0; i < runnerIDs.length; ++i) {
            RunnerIDInt runnerID = runnerIDs[i];
            assertEquals("The min running time of " + runnerID + " is",
                    getMin(runs[i]), race.getMinRun(runnerID));
            printRuntimeOfRunnerID(Math.log(runs.length));
            assertEquals("The avg running time of " + runnerID + " is",
                    getAvg(runs[i]), race.getAvgRun(runnerID));
            printRuntimeOfRunnerID(Math.log(runs.length));

            assertEquals("The rank by min of " + runnerID + " is",
                    ranksByMin[i], race.getRankMin(runnerID));
            printRuntimeOfRunnerID(Math.log(runs.length));
            assertEquals("The rank by avg of " + runnerID + " is",
                    ranksByAvg[i], race.getRankAvg(runnerID));
            printRuntimeOfRunnerID(Math.log(runs.length));

            if (PRINT_SUCCESS) System.out.println();
        }
        assertEquals("The fastest runner by min is",
                fastestByMin, race.getFastestRunnerMin());
        printRuntimeOfRunnerID(0);
        assertEquals("The fastest runner by avg is",
                fastestByAvg, race.getFastestRunnerAvg());
        printRuntimeOfRunnerID(0);
    }
}
