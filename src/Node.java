public class Node<T> {
    //Node Attributes:
    private Node<T> left; //pointer to left child
    private Node<T> middle; //pointer to middle child
    private Node<T> right; //pointer to right child
    private Node<T> parent; //pointer to parent
    private T key; //runner object or Run object
    private boolean isLeaf; //boolean to check if node is a leaf
    private int sentinel; //-1 to -infinity, 1 to infinity, 0 to else
    private int size; //size of the node

    //Node Constructor:
    public Node() {
        this.left = new Node<>(null, null, null, this, null, true, 0);
        this.middle = new Node<>(null, null, null, this, null, true, 0);
        this.right = null;
        this.parent = null;
        this.key = null;
        this.isLeaf = true;
        this.size = 0;
    }

    public Node(T key) {
        this.left = new Node<>(null, null, null, this, null, true, 0);
        this.middle = new Node<>(null, null, null, this, null, true, 0);
        this.right = null;
        this.parent = null;
        this.key = key;
        this.isLeaf = true;
        this.size = 0;
    }

    public Node(Node<T> left, Node<T> middle, Node<T> right, Node<T> parent, T key, Boolean isLeaf, int size) {
        this.left = left;
        this.middle = middle;
        this.right = right;
        this.parent = parent;
        this.key = key;
        this.isLeaf = isLeaf;
        this.size = size;
    }

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public Node<T> getMiddle() {
        return middle;
    }

    public void setMiddle(Node<T> middle) {
        this.middle = middle;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
        size = 1;
    }


    public int getSentinel() {
        return sentinel;
    }

    public void setSentinel(int sentinel) {
        this.sentinel = sentinel;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
