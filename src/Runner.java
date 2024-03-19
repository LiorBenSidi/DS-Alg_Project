public class Runner<K>{

    //TODO: implementing "equals" for "node"?
    private Runner<K> left;
    private Runner<K> middle;
    private Runner<K> right;
    private Runner<K> parent;
    private K id;
    private boolean isLeaf;
    private boolean sentinels;

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
}
