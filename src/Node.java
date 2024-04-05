import java.security.Key;

public class Node<T> {
    private T key;
    private Node<T> left, middle, right, parent;
    private boolean isLeaf;
    private int size;

    //TODO:
    //Node Constructor:
    public Node() {
        this.left = null;
        this.middle = null;
        this.right = null;
        this.parent = null;
        this.key = null;
        this.isLeaf = false;
        this.size = 0;
    }

    public Node(T key, boolean isLeaf) {
        this.key = key;
        this.isLeaf = isLeaf; // Only the sentinel node should have this as true
        this.left = null;
        this.middle = null;
        this.right = null;
        this.parent = null;
        if (isLeaf && this.key == null) {
            this.size = 0;
        } else {
            this.size = 1;
        }
    }

    // Public constructor for normal nodes
    public Node(T key) {
        this(key, false);
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
        if (isLeaf) {
            size = 1;
        }
    }

    public boolean isMaxNode(Node<T> MAX_SENTINEL) {
        return this == MAX_SENTINEL;
    }

    public boolean isMinNode(Node<T> MIN_SENTINEL) {
        return this == MIN_SENTINEL;
    }

    public boolean isSentinel(Node<T> MIN_SENTINEL, Node<T> MAX_SENTINEL) {
        return (this == MIN_SENTINEL) || (this == MAX_SENTINEL);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
