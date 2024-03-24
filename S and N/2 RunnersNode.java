public class RunnersNode<K>{
    private RunnersNode<K> left;
    private  RunnersNode<K> middle;
    private  RunnersNode<K> right;
    private  RunnersNode<K> parent;
    private Runnings_2_3_Tree playerRunningsTree;
    private K id;
    private float sum;
    private int numOfRunnings;
    private float minRun;

    public  RunnersNode( RunnersNode<K> parent,  RunnersNode<K> left,  RunnersNode<K> middle, K id) {
        this.left = left;
        this.middle = middle;
        this.parent = parent;
        this.id = id;
        this.right = null;
        this.playerRunningsTree=null;
        this.sum=0;
        this.numOfRunnings=0;
        this.minRun=Float.MAX_VALUE;
    }

    public  RunnersNode( RunnersNode<K> parent,  RunnersNode<K> left,  RunnersNode<K> middle, K id,  RunnersNode<K> right) {
        this(parent, left, middle, id);
        this.right = right;
        this.sum=0;
        this.numOfRunnings=0;
        this.minRun=Float.MAX_VALUE;
    }

    public  RunnersNode<K> getParent() {
        return this.parent;
    }

    public void setParent( RunnersNode<K> parent) {
        this.parent = parent;
    }

    public  RunnersNode<K> getMiddle() {
        return this.middle;
    }

    public void setMiddle( RunnersNode<K> middle) {
        this.middle = middle;
    }

    public  RunnersNode<K> getLeft() {
        return left;
    }

    public void setLeft( RunnersNode<K> left) {
        this.left = left;
    }

    public  RunnersNode<K> getRight() {
        return right;
    }

    public void setRight( RunnersNode<K> r) {
        this.right = r;
    }

    public K getId() {
        return this.id;
    }

    public void setId(K newid) {
        this.id = newid;
    }
    public boolean isLeaf(){//if there is no left child, then it is not a valid 2_3 tree node
        return (this.getLeft() == null);
    }

    public Runnings_2_3_Tree getPlayerRunningsTree(){
        return this.playerRunningsTree;
    }
    public void setPlayerRunningsTree(Runnings_2_3_Tree newRunningsTree){
        this.playerRunningsTree= newRunningsTree;

    }
    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public int getNumOfRunnings() {
        return numOfRunnings;
    }

    public void setNumOfRunnings(int numOfRunnings) {
        this.numOfRunnings = numOfRunnings;
    }

    public float getMinRun() {
        return minRun;
    }

    public void setMinRun(float minRun) {
        this.minRun = minRun;
    }
    public float getAvgRun(){
        return (this.getSum()/this.getNumOfRunnings());
    }
    public int getSize() {
        int size = 1; // Count the current node
        if (left != null) {
            size += left.getSize(); // Recursively count the left subtree
        }
        if (middle != null) {
            size += middle.getSize(); // Recursively count the middle subtree
        }
        if (right != null) {
            size += right.getSize(); // Recursively count the right subtree
        }
        return size;
    }
}
