public class MinRunsNode<K> {
    private MinRunsNode<K> parent;
    private  MinRunsNode<K> left;
    private MinRunsNode<K> middle;
    private MinRunsNode<K> right;
    private K id;
    private float time;

    public  MinRunsNode( MinRunsNode<K> parent,  MinRunsNode<K> left,  MinRunsNode<K> middle, float time,K id) {
        this.left = left;
        this.middle = middle;
        this.parent = parent;
        this.time = time;
        this.right = null;
        this.id=id;
    }
    public  MinRunsNode( MinRunsNode<K> parent,  MinRunsNode<K> left,  MinRunsNode<K> middle,MinRunsNode<K>right, float time,K id) {
      this(parent,left,middle,time,id);
      this.right=right;
    }
    public  MinRunsNode<K> getParent() {
        return this.parent;
    }

    public void setParent(MinRunsNode<K> parent) {
        this.parent = parent;
    }

    public  MinRunsNode<K> getMiddle() {
        return this.middle;
    }

    public void setMiddle(MinRunsNode<K> middle) {
        this.middle = middle;
    }

    public MinRunsNode<K> getLeft() {
        return left;
    }

    public void setLeft(MinRunsNode<K> left) {
        this.left = left;
    }

    public  MinRunsNode<K> getRight() {
        return right;
    }

    public void setRight(MinRunsNode<K> r) {
        this.right = r;
    }

    public float getTime() {
        return this.time;
    }
    public K getId() {
        return this.id;
    }

    public void setId(K newid) {
        this.id = newid;
    }

    public void setTime(float newTime) {
        this.time = newTime;
    }
    public boolean isLeaf(){//if there is no left child, then it is not a valid 2_3 tree node
        return (this.getLeft() == null);
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
