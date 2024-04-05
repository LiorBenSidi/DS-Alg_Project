import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Random;

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
        // The ids which we will check will not necessarily be RunnerIDInt
        // This is just for the example

        Race race = new Race();

        HashSet<Integer> runnerSet = new HashSet<>();
        Random random = new Random(66);

        while (runnerSet.size() < 9) { // Adjust the size as per your requirement
            int randomNumber = random.nextInt(99) + 1; // Generating random integers from 1 to 99
            runnerSet.add(randomNumber);
        }

        for (int j : runnerSet) {
//            System.out.println(j); // Printing for demonstration
             race.addRunner(new RunnerIDInt(j));
        }

        printTree(race.getRoot(),"",true);
        DecimalFormat df = new DecimalFormat("#.##");

        int r = 10;
        float[] run_times = new float[r];
        RunnerIDInt id;
        for (int j : runnerSet) {
            for (int i = 0; i < r; i++) {
                run_times[i] = Float.parseFloat(df.format(random.nextFloat() * 98 + 1)); // generates a random double between 1 and 99
            }
            id = new RunnerIDInt(j);
            for(float time: run_times)
                race.addRunToRunner(id, time);
            System.out.println();
            System.out.println("The min running time of " + id + " is " + race.getMinRun(id));
            System.out.println("The avg running time of " + id + " is " + race.getAvgRun(id));
            System.out.println();

        }

        System.out.println("The runner with the smallest minimum time is "+ race.getFastestRunnerMin());
        System.out.println("The runner with the smallest minimum time is "+ race.getFastestRunnerAvg());
        System.out.println("Runners rank:");
        for(int i : runnerSet)
            System.out.print("("+i+" - "+race.getRankMin(new RunnerIDInt(i))+ ") ");

    }

    public static void printTree(node<RunnerID> root, String prefix, boolean isTail) {
        if (root instanceof internalNode<RunnerID>) {
//            System.out.println(prefix + (isTail ? "└── InnerNode with key: " : "├── InnerNode with key: ") + root.key+", size: "+root.getSize());
            System.out.println(prefix + (isTail ? "└──" : "├── "));
            internalNode<RunnerID> innerNode = (internalNode<RunnerID>) root;
            printTree(innerNode.getLeft(), prefix + (isTail ? "    " : "│   "), false);
            printTree(innerNode.getMiddle(), prefix + (isTail ? "    " : "│   "), false);
            printTree(innerNode.getRight(), prefix + (isTail ? "    " : "│   "), true);
        } else if (root instanceof leaf<RunnerID>) {
//            System.out.println(prefix + (isTail ? "└── Leaf with key: " : "├── Leaf with key: ") + root.key+", size: "+root.getSize());
            System.out.println(prefix + (isTail ? "└── : " : "├── : ") + root.getKey());
        }
    }

    public static void printTree(node<RunnerID> root) {
        printTree(root, "", true);
    }
}
