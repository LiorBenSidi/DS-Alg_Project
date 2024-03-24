public class Runner<K>{
    private Runner<K> left, middle, right, parent;
    private K id;
    private boolean isLeaf;
    private float minRun = Float.MAX_VALUE;
    private float totalRunTime = 0;
    private int runCount = 0;
    private boolean sentinels;
    private Run[] runs = new Run[10]; // Initial size of the array

    public Runner(K id) {
        this.id = id;
        this.isLeaf = true;
    }

    // Constructor for an internal node
    public Runner(Runner<K> left, Runner<K> middle, Runner<K> right, K id, boolean isLeaf, boolean sentinels) {
        this.left = left;
        this.middle = middle;
        this.right = right;
        this.id = id;
        this.isLeaf = isLeaf;
        this.sentinels = sentinels;
    }

    public void addRun(float time) {
        if (runCount == runs.length) {
            // Reallocate the array to a larger size
            Run[] newRuns = new Run[runs.length * 2];
            for (int i = 0; i < runs.length; i++) {
                newRuns[i] = runs[i];
            }
            runs = newRuns;
        }
        runs[runCount++] = new Run(this.id, time);
        minRun = Math.min(minRun, time);
        totalRunTime += time;
    }
    public void removeRun(float time) {
        boolean removed = false;
        for (int i = 0; i < runCount; i++) {
            if (runs[i].getTime() == time) {
                // Shift remaining elements to the left
                for (int j = i; j < runCount - 1; j++) {
                    runs[j] = runs[j + 1];
                }
                runCount--;
                removed = true;
                break;
            }
        }

        if (removed) {
            // Recalculate the minimum run time
            minRun = Float.MAX_VALUE;
            for (int i = 0; i < runCount; i++) {
                minRun = Math.min(minRun, runs[i].getTime());
            }

            // Recalculate the average run time
            totalRunTime -= time;
        }
    }

    public float getMinRun() {
        return minRun;
    }

    public float getAvgRun() {
        return runCount > 0 ? totalRunTime / runCount : Float.MAX_VALUE;
    }

    public K getId() {
        return id;
    }

    public Runner<K> getLeft() {
        return left;
    }

    public void setLeft(Runner<K> left) {
        this.left = left;
    }

    public Runner<K> getMiddle() {
        return middle;
    }

    public void setMiddle(Runner<K> middle) {
        this.middle = middle;
    }

    public Runner<K> getRight() {
        return right;
    }

    public void setRight(Runner<K> right) {
        this.right = right;
    }

    public Runner<K> getParent() {
        return parent;
    }

    public void setParent(Runner<K> parent) {
        this.parent = parent;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public int getRunCount() {
        return runCount;
    }

    public void setRunCount(int runCount) {
        this.runCount = runCount;
    }

    public Run<K>[] getRuns() {
        return runs;
    }

    public void setRuns(Run<Object>[] runs) {
        this.runs = runs;
    }

    public boolean isSentinels() {
        return sentinels;
    }

    public void setSentinels(boolean sentinels) {
        this.sentinels = sentinels;
    }

    public void setMinRun(float minRun) {
        this.minRun = minRun;
    }

    /*
    //TODO: implementing "equals" for "node"?
    private Runner<K> left;
    private Runner<K> middle;
    private Runner<K> right;
    private Runner<K> parent;
    private K id;
    private boolean isLeaf;
    private boolean sentinels;
    public float getMinRun() {
        return minRun;
    }

    public void setMinRun(float minRun) {
        this.minRun = minRun;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    private float minRun;
    private int size;
    public ArrayList<Run> getRuns() {
        return runs;
    }

    public void setRuns(ArrayList<Run> runs) {
        this.runs = runs;
    }

    private ArrayList<Run> runs;

    public int getHeapSize() {
        return heapSize;
    }

    public void setHeapSize(int heapSize) {
        this.heapSize = heapSize;
    }

    private int heapSize;

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }


    public boolean isSentinels() {
        return sentinels;
    }

    public void setSentinels(boolean sentinels) {
        this.sentinels = sentinels;
    }

    public Runner(Runner<K> parent, Runner<K> left, Runner<K> middle, K id, boolean isLeaf, boolean sentinels) {
        this.left = left;
        this.middle = middle;
        this.parent = parent;
        this.id = id;
        this.isLeaf = isLeaf;
        this.sentinels = sentinels;
        this.right = null;
    }

    public Runner(Runner<K> parent, Runner<K> left, Runner<K> middle, K id, Runner<K> right, boolean isLeaf, boolean sentinels) {
        this(parent, left, middle, id, isLeaf, sentinels);
        this.right = right;
    }

    public Runner<K> getParent() {
        return parent;
    }

    public void setParent(Runner<K> parent) {
        this.parent = parent;
    }

    public Runner<K> getMiddle() {
        return middle;
    }

    public void setMiddle(Runner<K> middle) {
        this.middle = middle;
    }

    public Runner<K> getLeft() {
        return left;
    }

    public void setLeft(Runner<K> left) {
        this.left = left;
    }

    public Runner<K> getRight() {
        return right;
    }

    public void setRight(Runner<K> right) {
        this.right = right;
    }

    public K getId() {
        return id;
    }

    public void setId(K id) {
        this.id = id;
    }
     */
}
