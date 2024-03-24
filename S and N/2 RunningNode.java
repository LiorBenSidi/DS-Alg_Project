public class RunningNode {
    private RunningNode left;
    private  RunningNode middle;
    private  RunningNode right;
    private  RunningNode parent;
    private float time;

    public  RunningNode( RunningNode parent,  RunningNode left,  RunningNode middle, float time) {
        this.left = left;
        this.middle = middle;
        this.parent = parent;
        this.time = time;
        this.right = null;
    }

    public  RunningNode( RunningNode parent,  RunningNode left,  RunningNode middle, float time,  RunningNode right) {
        this(parent, left, middle, time);
        this.right = right;
    }

    public  RunningNode getParent() {
        return this.parent;
    }

    public void setParent(RunningNode parent) {
        this.parent = parent;
    }

    public  RunningNode getMiddle() {
        return this.middle;
    }

    public void setMiddle(RunningNode middle) {
        this.middle = middle;
    }

    public RunningNode getLeft() {
        return left;
    }

    public void setLeft(RunningNode left) {
        this.left = left;
    }

    public  RunningNode getRight() {
        return right;
    }

    public void setRight(RunningNode r) {
        this.right = r;
    }

    public float getTime() {
        return this.time;
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
