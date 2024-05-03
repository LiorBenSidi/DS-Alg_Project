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

public class Main
{
    public static void main(String[] args) {
        System.out.println("starting to test:");
        System.out.println("test1:");
        test1();
        System.out.println();
        System.out.println("test2:");
        test2();
        System.out.println();
        System.out.println("test3:");
        test3();
        System.out.println();
        System.out.println("test4:");
        test4();
        System.out.println();
        System.out.println("test5:");
        test5();
        System.out.println();
        System.out.println("testAddRunner:\n");
        testAddRunner();
        System.out.println();
        System.out.println("testRemoveRunner:\n");
        testRemoveRunner();
        System.out.println();
        System.out.println("testAddRunToRunner:\n");
        testAddRunToRunner();
        System.out.println();
        System.out.println("testRemoveRunFromRunner:\n");
        testRemoveRunFromRunner();
        System.out.println();
        System.out.println("testGetMinRun:\n");
        testGetMinRun();
        System.out.println();
        System.out.println("testGetAvgRun:\n");
        testGetAvgRun();
        System.out.println();
        System.out.println("testGetFastestRunnerAvg:\n");
        testGetFastestRunnerAvg();
        System.out.println();
        System.out.println("testGetFastestRunnerMin:\n");
        testGetFastestRunnerMin();
        System.out.println();
        System.out.println("testGetRankAvg:\n");
        testGetRankAvg();
        System.out.println();
        System.out.println("testGetRankMin:\n");
        testGetRankMin();
        System.out.println("finished testing");

    }
    public static void testGetRankMin() {
        Race race = new Race();
        race.init();
        System.out.println("--- test 1: null ---");
        try {
            race.getRankMin(null);
        }
        catch (IllegalArgumentException ae) {
            System.out.println("caught");
        }

        System.out.println("--- test 2: runner does not exist ---");
        RunnerID id1 = new RunnerIDInt(1);
        try {
            System.out.println("value: " + race.getRankMin(id1));
        }
        catch (IllegalArgumentException ae) {
            System.out.println("caught");
        }

        System.out.println("--- test 3: no runs ---");
        race.addRunner(id1);
        RunnerID id2 = new RunnerIDInt(2);
        race.addRunner(id2);
        System.out.println("value: " + race.getRankMin(id1));
        System.out.println("value: " + race.getRankMin(id2));

        System.out.println("--- test 4: with runs ---");
        race.addRunToRunner(id2, 15);
        System.out.println("value: " + race.getRankMin(id1));
        System.out.println("value: " + race.getRankMin(id2));
        race.addRunToRunner(id1, 16);
        System.out.println("value: " + race.getRankMin(id1));
        System.out.println("value: " + race.getRankMin(id2));
        race.addRunToRunner(id1, 1);
        System.out.println("value: " + race.getRankMin(id1));
        System.out.println("value: " + race.getRankMin(id2));
    }
    public static void testGetRankAvg() {
        Race race = new Race();
        race.init();
        System.out.println("--- test 1: null ---");
        try {
            race.getRankAvg(null);
        }
        catch (IllegalArgumentException ae) {
            System.out.println("caught");
        }

        System.out.println("--- test 2: runner does not exist ---");
        RunnerID id1 = new RunnerIDInt(1);
        try {
            race.getRankAvg(id1);
        }
        catch (IllegalArgumentException ae) {
            System.out.println("caught");
        }

        System.out.println("--- test 3: no runs ---");
        race.addRunner(id1);
        RunnerID id2 = new RunnerIDInt(2);
        race.addRunner(id2);
        System.out.println("value: " + race.getRankAvg(id1));
        System.out.println("value: " + race.getRankAvg(id2));

        System.out.println("--- test 4: with runs ---");
        race.addRunToRunner(id2, 15);
        System.out.println("value: " + race.getRankAvg(id1));
        System.out.println("value: " + race.getRankAvg(id2));
        race.addRunToRunner(id1, 116);
        System.out.println("value: " + race.getRankAvg(id1));
        System.out.println("value: " + race.getRankAvg(id2));
    }

    public static void testGetFastestRunnerMin() {
        Race race = new Race();
        race.init();
        System.out.println("--- test 1: no runners ---");
        try {
            System.out.println("value: " + race.getFastestRunnerMin());
        }
        catch (IllegalArgumentException ae) {
            System.out.println("caught");
        }

        System.out.println("--- test 2: no runs ---");
        RunnerID id2 = new RunnerIDInt(2);
        race.addRunner(id2);
        System.out.println("value: " + race.getFastestRunnerMin());
        RunnerID id1 = new RunnerIDInt(1);
        race.addRunner(id1);
        System.out.println("value: " + race.getFastestRunnerMin());

        System.out.println("--- test 3: with runs ---");
        RunnerID id0 = new RunnerIDInt(0);
        race.addRunner(id0);
        race.addRunToRunner(id1, 17);
        race.addRunToRunner(id1, 15);
        race.addRunToRunner(id1, 16);
        System.out.println("value: " + race.getFastestRunnerMin());
        race.addRunToRunner(id2, 15);
        System.out.println("value: " + race.getFastestRunnerMin());
        race.addRunToRunner(id2, 10);
        System.out.println("value: " + race.getFastestRunnerMin());
        race.addRunToRunner(id0, 15);
        race.removeRunFromRunner(id2, 10);
        System.out.println("value: " + race.getFastestRunnerMin());
        race.removeRunner(id0);
        System.out.println("value: " + race.getFastestRunnerMin());
    }

    public static void testGetFastestRunnerAvg() {
        Race race = new Race();
        race.init();
        System.out.println("--- test 1: no runners ---");
        try {
            System.out.println("value: " + race.getFastestRunnerAvg());
        }
        catch (IllegalArgumentException ae){
            System.out.println("caught");
        }
        System.out.println("--- test 2: no runs ---");
        RunnerID id2 = new RunnerIDInt(2);
        race.addRunner(id2);
        System.out.println("value: " + race.getFastestRunnerAvg());
        RunnerID id1 = new RunnerIDInt(1);
        race.addRunner(id1);
        System.out.println("value: " + race.getFastestRunnerAvg());

        System.out.println("--- test 3: with runs ---");
        race.addRunToRunner(id1, 17);
        race.addRunToRunner(id1, 15);
        race.addRunToRunner(id1, 16);
        System.out.println("value: " + race.getFastestRunnerAvg());
        race.addRunToRunner(id2, 16);
        System.out.println("value: " + race.getFastestRunnerAvg());
        race.addRunToRunner(id2, 10);
        System.out.println("value: " + race.getFastestRunnerAvg());
    }
    public static void testGetAvgRun() {
        Race race = new Race();
        race.init();
        System.out.println("--- test 1: get null ---");
        try {
            race.getAvgRun(null);
        }
        catch (IllegalArgumentException ae){
            System.out.println("caught");
        }

        System.out.println("--- test 2: general ---");
        RunnerID id = new RunnerIDInt(1);
        try {
            System.out.println("value: " + race.getAvgRun(id));
        }
        catch (IllegalArgumentException ae){
            System.out.println("caught");
        }

        race.addRunner(id);
        try {
            System.out.println("value: " + race.getAvgRun(id));
        }
        catch (IllegalArgumentException ae){
            System.out.println("caught");
        }
        race.addRunToRunner(id, 17);
        race.addRunToRunner(id, 15);
        race.addRunToRunner(id, 16);
        System.out.println("value: " + race.getAvgRun(id));
    }

    public static void testGetMinRun() {
        Race race = new Race();
        race.init();
        System.out.println("--- test 1: get null ---");
        try {
            System.out.println("value: " + race.getMinRun(null));
        } catch (IllegalArgumentException ae){
            System.out.println("caught");
        }

        System.out.println("--- test 2: general ---");
        RunnerID id = new RunnerIDInt(1);
        try {
            System.out.println("value: " + race.getMinRun(id));
        } catch (IllegalArgumentException ae){
            System.out.println("caught");
        }

        race.addRunner(id);
        try {
            System.out.println("value: " + race.getMinRun(id));
        } catch (IllegalArgumentException ae){
            System.out.println("caught");
        }
        race.addRunToRunner(id, 20);
        race.addRunToRunner(id, 15);
        race.addRunToRunner(id, 16);
        race.addRunToRunner(id, 20);
        race.addRunToRunner(id, 15);
        System.out.println("value: " + race.getMinRun(id));
    }

    public static void testRemoveRunFromRunner() {
        Race race = new Race();
        race.init();
        System.out.println("--- test 1: removing unique runs ---");
        RunnerID id = new RunnerIDInt(1);
        race.addRunner(id);
        //Main.printTree(race.getIdTree());
        race.addRunToRunner(id, 16);
        race.addRunToRunner(id, 18);
//        Main.printTree(race.getRunnerGivenRunnerId(id).getRuns());
//        System.out.println("Number of runs: " + race.getRunnerGivenRunnerId(id).getAmountOfRuns());
        race.removeRunFromRunner(id, 16);
        race.removeRunFromRunner(id, 18);
//        Main.printTree(race.getRunnerGivenRunnerId(id).getRuns());
//        System.out.println("Number of runs: " + race.getRunnerGivenRunnerId(id).getAmountOfRuns());

//        Main.printTree(race.getMinTree());

        System.out.println("--- test 2: removing non unique runs ---");
        race.addRunToRunner(id, 15);
        race.addRunToRunner(id, 15);
//        System.out.println("Number of runs: " + race.getRunnerGivenRunnerId(id).getAmountOfRuns());
        race.removeRunFromRunner(id, 15);
//        System.out.println("Number of runs: " + race.getRunnerGivenRunnerId(id).getAmountOfRuns());
        race.removeRunFromRunner(id, 15);
//        System.out.println("Number of runs: " + race.getRunnerGivenRunnerId(id).getAmountOfRuns());

        System.out.println("--- test 3: remove a run that does not exist ---");
        try {
            race.removeRunFromRunner(id, 100); // needs to raise an error
        } catch (IllegalArgumentException ae){
            System.out.println("caught");
        }
//

        System.out.println("--- test 4: remove a run that used to exist ---");
        try {
            race.removeRunFromRunner(id, 15); // needs to raise an error
        } catch (IllegalArgumentException ae){
            System.out.println("caught");
        }

        try {
            race.removeRunFromRunner(id, 16); // needs to raise an error
        } catch (IllegalArgumentException ae){
            System.out.println("caught");
        }

        System.out.println("--- test 5: remove a run from a runner that does not exist ---");
        RunnerID id2 = new RunnerIDInt(2);
        try {
            race.removeRunFromRunner(id2, 16); // needs to raise an error
        } catch (IllegalArgumentException ae){
            System.out.println("caught");
        }

        System.out.println("--- test 6: remove a run from null ---");
        try {
            race.removeRunFromRunner(null, 123); // needs to raise an error
        } catch (IllegalArgumentException ae){
            System.out.println("caught");
        }

        System.out.println("--- test 7: avg and min trees ---");
        //Main.printTree(race.getIdTree());
//        Main.printTree(race.getMinTree());
//        Main.printTree(race.getAvgTree());
        race.addRunToRunner(id, 15);
        race.addRunToRunner(id, 15);
        race.addRunToRunner(id, 10);
        race.addRunner(id2);
//        Main.printTree(race.getMinTree());
//        Main.printTree(race.getAvgTree());
        System.out.println("change runner 2:");
        race.addRunToRunner(id2, 11);
//        Main.printTree(race.getMinTree());
//        Main.printTree(race.getAvgTree());
    }

    public static void testAddRunToRunner() {
        Race race = new Race();
        race.init();
        System.out.println("--- test 1: adding runs ---");
        RunnerID id1 = new RunnerIDInt(1);
        race.addRunner(id1);
        //Main.printTree(race.getIdTree());
        race.addRunToRunner(id1, 15);
        race.addRunToRunner(id1, 20);
        race.addRunToRunner(id1, 16);
        race.addRunToRunner(id1, 15);
//        Main.printTree(race.getRunnerGivenRunnerId(id1).getRuns());
//        System.out.println("Number of runs: " + race.getRunnerGivenRunnerId(id1).getAmountOfRuns());

        System.out.println("--- test 2: negative runs are not allowed ---");
        try {
            race.addRunToRunner(id1, -15); // needs to raise an error
        } catch (IllegalArgumentException ae){
            System.out.println("caught");
        }


        System.out.println("--- test 3: add a run to a runner that does not exist ---");
        RunnerID id2 = new RunnerIDInt(2);
        try {
            race.addRunToRunner(id2, 15); // needs to raise an error
        } catch (IllegalArgumentException ae){
            System.out.println("caught");
        }

        System.out.println("--- test 4: add a run to a runner that is null ---");
        try {
            race.addRunToRunner(null, 15); // needs to raise an error
        } catch (IllegalArgumentException ae){
            System.out.println("caught");
        }

        System.out.println("--- test 5: what if I add runner, add runs, remove the runner, add it again ---");
        RunnerID id3 = new RunnerIDInt(3);
        race.addRunner(id3);
        race.addRunToRunner(id3, 15);
//        System.out.println("Number of runs: " + race.getRunnerGivenRunnerId(id1).getAmountOfRuns());
        race.removeRunner(id3);
        race.addRunner(id3);
//        System.out.println("Number of runs: " + race.getRunnerGivenRunnerId(id1).getAmountOfRuns());

        System.out.println("--- test 6: avg and min trees ---");
        //Main.printTree(race.getIdTree());
//        Main.printTree(race.getMinTree());
//        Main.printTree(race.getAvgTree());

    }

    public static void testAddRunner() {
        Race race = new Race();
        race.init();
        //Main.printTree(race.getIdTree());


        // add runner
        for (int i : new int[] {2, 5, 6, 3, 9, 1}) {
            System.out.println("adding the " + i + " element:");
            RunnerID id = new RunnerIDInt(i);
            race.addRunner(id);
            //Main.printTree(race.getIdTree());
//            Main.printTree(race.getMinTree());
//            Main.printTree(race.getAvgTree());
        }

        // add runner that already exists
        RunnerID id = new RunnerIDInt(0);
        race.addRunner(id);
        try {
            for (int i : new int[] {2, 5, 6, 3, 9, 1, 0}) {
                id = new RunnerIDInt(i);
                System.out.println("rank of" + id + "is: " + race.getRankMin(id));
            }

            race.addRunner(id);// needs to raise an error
        } catch (IllegalArgumentException ae){
            System.out.println("caught");
        }
        System.out.println("--- test 4: add null ---");
        try {
            race.addRunner(null); // needs to raise an error
        } catch (IllegalArgumentException ae){
            System.out.println("caught");
        }
    }

    public static void testRemoveRunner() {
        Race race = new Race();
        race.init();

        System.out.println("--- test 1: adding deleting and adding again ---");
        RunnerID id1 = new RunnerIDInt(1);
        race.addRunner(id1);
        //Main.printTree(race.getIdTree());
        race.removeRunner(id1);
        //Main.printTree(race.getIdTree());
        race.addRunner(id1);
        //Main.printTree(race.getIdTree());

        System.out.println("--- test 2: deleting in different orders ---");
        RunnerID id2 = new RunnerIDInt(2);
        race.addRunner(id2);
        RunnerID id3 = new RunnerIDInt(3);
        race.addRunner(id3);
        race.removeRunner(id1);
        race.removeRunner(id3);
        //Main.printTree(race.getIdTree());

        System.out.println("--- test 3: deleting a runner that does not exist ---");
        RunnerID id4 = new RunnerIDInt(4);
        try {
            race.removeRunner(id4); // needs to raise an error
        } catch (IllegalArgumentException ae){
            System.out.println("caught");
        }

        System.out.println("--- test 4: deleting a runner that used to exist ---");
        //Main.printTree(race.getIdTree());
        try {
            race.removeRunner(id1); // needs to raise an error
        } catch (IllegalArgumentException ae){
            System.out.println("caught");
        }

        System.out.println("--- test 5: remove a  null ---");
        try {
            race.removeRunner(null); // needs to raise an error
        } catch (IllegalArgumentException ae){
            System.out.println("caught");
        }

        System.out.println("--- test 6: what if I add runner, remove the runner, add it again ---");
        RunnerID id5 = new RunnerIDInt(5);
        race.addRunner(id5);
//        //Main.printTree(race.getIdTree());
        race.removeRunner(id5);
//        //Main.printTree(race.getIdTree());
        race.addRunner(id5);
//        //Main.printTree(race.getIdTree());





    }
    public static void test5() {
//        Race race = new Race();
//        race.init();
//        RunnerID id1 = new RunnerIDInt(1);
//        race.addRunner(id1);
//        race.removeRunner(id1);
//        System.out.println(race.getMinRun(id1));
        Race race = new Race();
        int[] runners = new int[]{1,4,5,3, 7,14,19,22,25,29};
        for (int j : runners) {
            race.addRunner(new RunnerIDInt(j));
        }
//        //Main.printTree(race.getIdTree());
    }

    public static void test4() {
        Race race = new Race();
        race.init();

        RunnerID id1 = new RunnerIDInt(1);
        RunnerID id2 = new RunnerIDInt(2);
        RunnerID id3 = new RunnerIDInt(3);
        race.addRunner(id1);
        race.addRunner(id2);
        race.addRunner(id3);

        race.addRunToRunner(id1, 1);
        race.addRunToRunner(id1, 9);

        race.addRunToRunner(id2, 4);
        race.addRunToRunner(id2, 4);
        race.addRunToRunner(id2, 4);
        race.addRunToRunner(id2, 4);


//        //Main.printTree(race.getIdTree());
//        Main.printTree(race.getAvgTree());

        race.removeRunFromRunner(id1, 1);
        try {
            race.addRunToRunner(id3, -9);
        }
        catch (IllegalArgumentException ae){
            System.out.println("caught");
        }

        race.removeRunFromRunner(id2, 4);
        race.removeRunFromRunner(id2, 4);
        race.removeRunFromRunner(id2, 4);
        race.removeRunFromRunner(id2, 4);
        try {
            race.removeRunFromRunner(id2, 4);
        }
        catch (IllegalArgumentException ae){
            System.out.println("caught");
        }


    }

    public static void test3() {
        // checks that you can not add the same runner twice
        // needs to raise an error
        Race race = new Race();
        race.init();
        RunnerID id1 = new RunnerIDInt(3);
        race.addRunner(id1);
        RunnerID id2 = new RunnerIDInt(3);
        try {
            race.addRunner(id2);
        }
        catch (IllegalArgumentException ae){
            System.out.println("caught");
        }
    }

    public static void test2() {
        // checks that you can not add the same runner twice
        // needs to raise an error
        Race race = new Race();
        race.init();
        RunnerID id = new RunnerIDInt(3);

        race.addRunner(id);
        try {
            race.addRunner(id);
        }
        catch (IllegalArgumentException ae){
            System.out.println("caught");
        }
    }

    public static void test1() {
        Race race = new Race();
        race.init();

        RunnerID id = new RunnerIDInt(3);
        race.addRunner(id);

        try {
            System.out.println("The minimum time is: " + race.getMinRun(id) + "\n");
        }
        catch (IllegalArgumentException ae){
            System.out.println("caught");
        }

        try {
            System.out.println("The average time is: " + race.getAvgRun(id) + "\n");
        }
        catch (IllegalArgumentException ae){
            System.out.println("caught");
        }

        System.out.println();
        race.addRunToRunner(id, 118);

        System.out.println("The minimum time is: " + race.getMinRun(id));
        System.out.println("The average time is: " + race.getAvgRun(id));
        System.out.println("Fastest runner average: " + race.getFastestRunnerAvg());
        System.out.println("Fastest runner minimum: " + race.getFastestRunnerMin());
        System.out.println("the rank in the average tree is: " + race.getRankAvg(id));
        System.out.println("the rank in the minimum tree is: " + race.getRankMin(id));
        System.out.println();


        race.removeRunFromRunner(id, 118);

        race.removeRunner(id);

        try {
            System.out.println("The average time is: " + race.getAvgRun(id) + "\n");
        } catch (IllegalArgumentException iae) {
            System.out.println("caught");
        }
        try {
            System.out.println("The minimum time is: " + race.getMinRun(id) + "\n");
        } catch (IllegalArgumentException iae) {
            System.out.println("caught");
        }
    }
}
