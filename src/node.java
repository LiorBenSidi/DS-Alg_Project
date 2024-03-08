public class node<K>{

    //TODO: implementing "equals" for "node"?
    private node<K> left;
    private node<K> middle;
    private node<K> right;
    private node<K> parent;
    private K id;

    public node(node<K> parent, node<K> left, node<K> middle, K id) {
        this.left = left;
        this.middle = middle;
        this.parent = parent;
        this.id = id;
        this.right = null;
    }

    public node(node<K> parent, node<K> left, node<K> middle, K id, node<K> right) {
        this(parent, left, middle, id);
        this.right = right;
    }

    public node<K> getParent() {
        return parent;
    }

    public void setParent(node<K> parent) {
        this.parent = parent;
    }

    public node<K> getMiddle() {
        return middle;
    }

    public void setMiddle(node<K> middle) {
        this.middle = middle;
    }

    public node<K> getLeft() {
        return left;
    }

    public void setLeft(node<K> left) {
        this.left = left;
    }

    public node<K> getRight() {
        return right;
    }

    public void setRight(node<K> right) {
        this.right = right;
    }

    public K getId() {
        return id;
    }

    public void setId(K id) {
        this.id = id;
    }
}
