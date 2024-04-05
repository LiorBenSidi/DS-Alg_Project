import java.security.Key;

public class TwoThreeTree<T> {
    private Node<T> root; // Root of the tree
    private String comparisonType; // Type of comparison to use for the tree
    private int size; // Number of nodes in the tree
    private Node<T> fastestRunner; // Reference to the node with the fastest Runner/Run in the tree
    public Node<T> MIN_SENTINEL = new Node<>(null, true);
    public Node<T> MAX_SENTINEL = new Node<>(null, true);

    public TwoThreeTree() {
        this.root = new Node<T>(null);
        this.root.setLeft(this.MIN_SENTINEL);
        this.root.setMiddle(this.MAX_SENTINEL);
        this.root.getLeft().setParent(this.root);
        this.root.getMiddle().setParent(this.root);
        this.comparisonType = "ID";
        this.size = 0; // Tree is initially empty
        this.fastestRunner = null;
    }

    public TwoThreeTree(String comparisonType) {
        this();
        this.comparisonType = comparisonType;
    }

    //version 2
    public Node<T> search23(Node<T> node, T key) {
        if (node == null || node.isSentinel(this.MIN_SENTINEL, this.MAX_SENTINEL)) { // Check if the node is a sentinel
            return null; // Key not found, end of search path
        }
        if (node.isLeaf()) {
            if (compareNodeWithKey(key, node) == 0) {
                return node; // Key found
            } else {
                return null; // Key not found
            }
        } else {
            if (compareNodeWithKey(key, node.getLeft()) <= 0 ) {
                return search23(node.getLeft(), key);
            } else if (compareNodeWithKey(key, node.getMiddle()) <= 0) {
                return search23(node.getMiddle(), key);
            } else {
                return search23(node.getRight(), key);
            }
        }
    }

    //version 2
    public Node<T> minimum23() {
        Node<T> x = this.root;
        while (!x.isLeaf() && !x.getLeft().isMinNode(this.MIN_SENTINEL)) {
            x = x.getLeft(); // Continue to the leftmost child
        }
        x = x.getParent().getMiddle(); // Get the middle child of the parent
        if (!x.isMaxNode(this.MAX_SENTINEL)) {
            return x;
        } else {
            throw new IllegalArgumentException("Tree is empty.");
        }
    }

    //version 2
    public Node<T> Successor23(Node<T> x) {
        // If the node has a right child, the successor is the minimum key in the right subtree.
        if (x.getRight() != null) {
            if (!x.getRight().isMaxNode(this.MAX_SENTINEL)) {
                return findMinimum(x.getRight());
            }
        }

        // Otherwise, go up the tree until we find a node that is the left or middle child of its parent.
        Node<T> z = x.getParent();
        while (x == z.getRight() || (z.getRight() == null && x == z.getMiddle())) {
            x = z;
            z = z.getParent();
        }

        //
        Node<T> y;
        if (x == z.getLeft()) {
            y = z.getMiddle();
        } else {
            y = z.getRight();
        }

        while (!y.isLeaf()) {
            y = y.getLeft();
        }

        // The successor is either the middle or right child of the parent node.
        if (!y.isMaxNode(MAX_SENTINEL)) {
            return y;
        } else {
            return null;
        }
    }

    //used in successor23
    private Node<T> findMinimum(Node<T> node) {
        while (!node.getLeft().isLeaf() && !node.getLeft().isMinNode(this.MIN_SENTINEL)) {
            node = node.getLeft();
        }
        return node;
    }

    //version 2
    public void updateKey23(Node<T> x) {
        if (x.isSentinel(this.MIN_SENTINEL, this.MAX_SENTINEL)) {
            // If x is a sentinel, then there is nothing to update.
            return;
        }

        // Start with the leftmost key, since x.left should never be a sentinel.
        x.setKey(x.getLeft().getKey());

        // Update x.key to the key of x.middle if x.middle is not a sentinel.
        if (x.getMiddle() != null) {
            x.setKey(x.getMiddle().getKey());
        }

        // Update x.key to the key of x.right if x.right is not a sentinel.
        if (x.getRight() != null) {
            x.setKey(x.getRight().getKey());
        }
    }

    //used in updateKey
    //TODO: runs at O(n) time complexity, can be optimized to O(1)
    private int calculateSubtreeSize(Node<T> node) {
        if (node.isSentinel(this.MIN_SENTINEL, this.MAX_SENTINEL)) {
            // Sentinel nodes do not count towards the size of the tree.
            return 0;
        }
        if (node.isLeaf()) {
            // Leaf nodes count as 1.
            return 1;
        }
        // Sum the sizes of the child nodes and add 1 for the current node.
        return calculateSubtreeSize(node.getLeft()) +
                calculateSubtreeSize(node.getMiddle()) +
                calculateSubtreeSize(node.getRight()) + 1;
    }

    //version 4
    public void setChildren23(Node<T> x, Node<T> left, Node<T> middle, Node<T> right) {
        // Set the children of x to either a valid node or the sentinel node.
        x.setLeft(left);
        x.setMiddle(middle);
        x.setRight(right);

        left.setParent(x);

        // Update the parent pointers of the children to x

        if (middle != null) {
            middle.setParent(x);
        }
        if (right != null) {
            right.setParent(x);
        }

        // Update the key of x to the maximum key in its subtree
        updateKey23(x);
    }

    //version 4
    public Node<T> insertAndSplit23(Node<T> x, Node<T> z) {
        // Assume `z` is already created and `z.key` has been set.

        // Variables to hold children for easy access and comparison.
        Node<T> l = x.getLeft();
        Node<T> m = x.getMiddle();
        Node<T> r = x.getRight();

        // Case 1: x has only 2 children (x.right is sentinel).
        if (r == null) {
            if (compareNodeWithNode(z, l) < 0) {
                setChildren23(x, z, l, m);
            } else if (compareNodeWithNode(z, m) < 0) {
                setChildren23(x, l, z, m);
            } else {
                setChildren23(x, l, m, z);
            }
            // Update size of `x` and return `null` as there was no split.
            updateSize(x);
            return null;
        }

        // Create a new node `y` that might be the result of a split.
        Node<T> y = new Node<T>();

        // Case 2: x has 3 children and needs to be split.
        if (compareNodeWithNode(z, l) < 0) {
            setChildren23(x, z, l, null);
            setChildren23(y, m, r, null);
        } else if (compareNodeWithNode(z, m) < 0) {
            setChildren23(x, l, z, null);
            setChildren23(y, m, r, null);
        } else if (compareNodeWithNode(z, r) < 0) {
            setChildren23(x, l, m, null);
            setChildren23(y, z, r, null);
        } else {
            setChildren23(x, l, m, null);
            setChildren23(y, r, z, null);
        }

        // Update sizes after the split.
        updateSize(x);
        updateSize(y);

        return y;
    }

    //used in insertAndSplit23
    private void updateSize(Node<T> node) {
        if (node.isSentinel(this.MIN_SENTINEL, this.MAX_SENTINEL)) {
            return;
        }
        int leftSize = node.getLeft().isMinNode(this.MIN_SENTINEL) ? 0 : node.getLeft().getSize();
        int middleSize = (node.getMiddle() == null || node.getMiddle().isMaxNode(this.MAX_SENTINEL)) ? 0 : node.getMiddle().getSize();
        int rightSize = (node.getRight() == null || node.getRight().isMaxNode(this.MAX_SENTINEL)) ? 0 : node.getRight().getSize();
        node.setSize(leftSize + middleSize + rightSize);
    }

    //version 4.2
    public void insert23(T key) {
        Node<T> z = new Node<>(key, true);
        if (this.root.getLeft().isMinNode(this.MIN_SENTINEL) && this.root.getMiddle().isMaxNode(this.MAX_SENTINEL)) {
            setChildren23(this.root, this.root.getLeft(), z, this.root.getMiddle());
            this.fastestRunner = z;
        } else {
            if (compareNodeWithNode(z, fastestRunner) < 0) {
                fastestRunner = z;
            }
            Node<T> y = this.root;
            while (!y.isLeaf()) {
                if (compareNodeWithNode(z, y.getLeft()) < 0) {
                    y = y.getLeft();
                } else if (compareNodeWithNode(z, y.getMiddle()) < 0) {
                    y = y.getMiddle();
                } else {
                    y = y.getRight();
                }
            }

            Node<T> x = y.getParent();

            // x is now the parent of y, or x is root if y was root
            if (x == null) {
                x = this.root; // If y was the root, then it's x and we'll split the root
            }

            Node<T> splitChild = insertAndSplit23(x, z);

            while (x != this.root) {
                x = x.getParent();
                if (splitChild != null) {
                    splitChild = insertAndSplit23(x, splitChild);
                } else {
                    updateKey23(x); // Update the key of x
                    updateSize(x); // Update the size of x
                }
            }

            // If root was split, handle the new root creation
            if (splitChild != null) {
                Node<T> newRoot = new Node<>();
                setChildren23(newRoot, x, splitChild, null);
                this.root = newRoot;
                updateSize(newRoot); // Update the size from the root
            }
        }

    }

    //version 2
    private Node<T> borrowOrMerge23(Node<T> y) {
        Node<T> z = y.getParent();
        Node<T> x;

        // Choose sibling to borrow from or merge with.
        if (y == z.getLeft()) {
            x = z.getMiddle();
            if (x.getRight() != null) { // Borrow from x
                // y borrows the leftmost child of x
                setChildren23(y, y.getLeft(), x.getLeft(), null);
                setChildren23(x, x.getMiddle(), x.getRight(), null);
                updateSize(y); //TODO: check if the size is decreased
                updateSize(x);
            } else { // Merge y and x
                setChildren23(x, y.getLeft(), x.getLeft(), x.getMiddle());
                // Remove x from parent
                y.setParent(null); // y is now deleted
                setChildren23(z, x, z.getRight(), null);
                updateSize(y);
                updateSize(z);
            }
            return z;
        }

        if (y == z.getMiddle()) {
            x = z.getLeft();
            if (x.getRight() != null) { // Borrow from x
                // y borrows the rightmost child of x
                setChildren23(y, x.getRight(), y.getLeft(), null);
                setChildren23(x, x.getLeft(), x.getMiddle(), null);
                updateSize(y); //TODO: check if the size is decreased
                updateSize(x);
            } else { // Merge y and x
                setChildren23(x, x.getLeft(), x.getMiddle(), y.getLeft());
                // Remove y from parent
                y.setParent(null); // y is now deleted
                setChildren23(z, x, z.getRight(), null);
                updateSize(x);
                updateSize(z);
            }
            return z;
        }

        x = z.getMiddle();
        if (x.getRight() != null) { // Borrow from x
            // y borrows the leftmost child of x
            setChildren23(y, x.getRight(), y.getLeft(), null);
            setChildren23(x, x.getLeft(), x.getMiddle(), null);
            updateSize(y); //TODO: check if the size is decreased
            updateSize(x);
        } else { // Merge y and x
            setChildren23(x, x.getLeft(), x.getMiddle(), y.getLeft());
            // Remove y from parent
            y.setParent(null); // y is now deleted
            setChildren23(z, z.getLeft(), x, null);
            updateSize(y);
            updateSize(z);
        }
        return z;
    }

    //version 3
    public void delete23(T key, Node<T> searchResult, boolean isExist) {
        Node<T> x;
        if (!isExist) {
            x = search23(this.root, key); // Assume search is implemented
        } else {
            x = searchResult;
        }
        if (x == null || x.isSentinel(this.MIN_SENTINEL, this.MAX_SENTINEL)) {
            // Handle key not found or trying to delete a sentinel.
            throw new IllegalArgumentException("Key not found.");
        }
        if (fastestRunner != null && compareNodeWithNode(x, fastestRunner) == 0) {
            // Find the new fastest runner in the tree
            fastestRunner = Successor23(fastestRunner);
        }
        Node<T> y = x.getParent();
        Node<T> z;
        // Handle deletion at the leaf level.
        if (x == y.getLeft()) {
            z = y.getMiddle();
            setChildren23(y, z, y.getRight(), null);
        } else if (x == y.getMiddle()) {
            z = y.getLeft();
            setChildren23(y, z, y.getRight(), null);
        } else {
            z = y.getMiddle();
            setChildren23(y, y.getLeft(), z, null);
        }
        x.setParent(null); // Prepare x for garbage collection

        while (y != null) {
            if (y.getMiddle() == null) {
                if (y != this.root) {
                    y = borrowOrMerge23(y);
                } else {
                    setRoot(y.getLeft());
                    y.getLeft().setParent(null);
                    y.setParent(null); // Prepare y for garbage collection
                    return;
                }
            } else {
                updateKey23(y);
                updateSize(y);
                y = y.getParent();
            }
        }
    }

    public int Rank(Node<T> x) {
        int rank = 1;
        Node<T> y = x.getParent();
        while (y != null) {
            if (x == y.getMiddle()) {
                rank = rank + y.getLeft().getSize();

            } else if (x == y.getRight()) {
                rank = rank + y.getLeft().getSize() + y.getMiddle().getSize();
            }
            x = y;
            y = y.getParent();
        }
        return rank;
    }




    public Node<T> getRoot() {
        return root;
    }

    public void setRoot(Node<T> root) {
        this.root = root;
    }

    public String getComparisonType() {
        return comparisonType;
    }

    public void setComparisonType(String comparisonType) {
        this.comparisonType = comparisonType;
    }

    //version 2
    public int compareNodeWithKey(T key, Node<T> node) {
        // Check for sentinel nodes first.
        if (node.isMinNode(this.MIN_SENTINEL)) {
            return 1;
        } else if (node.isMaxNode(this.MAX_SENTINEL)) {
            // Sentinel is considered greater than any real key.
            return -1;
        }

        T r1 = key;
        T r2;

        if (node.getKey() == null) {
            return -1;
        } else {
            r2 = node.getKey();
        }

        // Existing comparison logic for non-sentinel nodes.
        return compareNodes(r1, r2);
    }
    public int compareNodeWithNode(Node<T> n1, Node<T> n2) {
        // Check for sentinel nodes first.
        if (n1.isMinNode(this.MIN_SENTINEL)) {
            return -1;
        } else if (n1.isMaxNode(this.MAX_SENTINEL)) {
            return 1;
        } else if (n2.isMinNode(this.MIN_SENTINEL)) {
            return 1;
        } else if (n2.isMaxNode(this.MAX_SENTINEL)) {
            return -1;
        }

        T r1;
        if (n1.getKey() == null) {
            return 1;
        }else {
            r1 = n1.getKey();
        }

        T r2;
        if (n2.getKey() == null) {
            return -1;
        } else {
            r2 = n2.getKey();
        }

        // Existing comparison logic for non-sentinel nodes.
        return compareNodes(r1, r2);
    }
    private int compareNodes(T r1, T r2) {
        // Existing comparison logic for non-sentinel nodes.
        if ((r1 instanceof Runner) && (r2 instanceof Runner)) {
            switch (comparisonType) {
                case "ID":
                    return compareRunnerID((Runner) r1, (Runner) r2);
                case "MinTime":
                    if (compareMinTime(((Runner) r1), ((Runner) r2)) == 0) {
                        return compareRunnerID((Runner) r1, (Runner) r2);
                    } else {
                        return compareMinTime(((Runner) r1), ((Runner) r2));
                    }
                case "AvgTime":
                    if (compareAvgTime(((Runner) r1), ((Runner) r2)) == 0) {
                        return compareRunnerID((Runner) r1, (Runner) r2);
                    } else {
                        return compareAvgTime(((Runner) r1), ((Runner) r2));
                    }
                default:
                    throw new IllegalArgumentException("Invalid comparison type: " + comparisonType);
            }
        } else if (r1 instanceof Run && r2 instanceof Run){
            if (comparisonType.equals("Time")) {
                return Float.compare(((Run) r1).getTime(),((Run) r2).getTime());
            }
            throw new IllegalArgumentException("Invalid comparison type: " + comparisonType);
        } else {
            throw new IllegalArgumentException("Invalid comparison type: " + comparisonType);
        }
    }


    //version 1
    /*
    private int compareNodes(T r1, T r2) {
        if ((r1 instanceof Runner) && (r2 instanceof Runner)) {
            switch (comparisonType) {
                case "ID":
                    return compareRunnerID((Runner) r1, (Runner) r2);
                case "MinTime":
                    return Float.compare(((Runner) r1).getMinTime(), ((Runner) r2).getMinTime());
                case "AvgTime":
                    return Float.compare(((Runner) r1).getAvgTime(), ((Runner) r2).getAvgTime());
                default:
                    throw new IllegalArgumentException("Invalid comparison type: " + comparisonType);
            }
        } else if (r1 instanceof Run && r2 instanceof Run){
            if (comparisonType.equals("Time")) {
                return Float.compare(((Run) r1).getTime(),((Run) r2).getTime());
            }
            throw new IllegalArgumentException("Invalid comparison type: " + comparisonType);
        } else {
            throw new IllegalArgumentException("Invalid comparison type: " + comparisonType);
        }
    }
     */


    private int compareRunnerID(Runner r1, Runner r2) {
        if (r1.getID().isSmaller(r2.getID())) {
            return -1;
        } else if (r2.getID().isSmaller(r1.getID())) {
            return 1;
        } else {
            return 0;
        }
    }

    private int compareMinTime(Runner r1, Runner r2) {
        if (r1.getMinTime() < r2.getMinTime()) {
            return -1;
        } else if (r2.getMinTime() < r1.getMinTime()) {
            return 1;
        } else {
            return 0;
        }
    }

    private int compareAvgTime(Runner r1, Runner r2) {
        if (r1.getAvgTime() < r2.getAvgTime()) {
            return -1;
        } else if (r2.getAvgTime() < r1.getAvgTime()) {
            return 1;
        } else {
            return 0;
        }
    }
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Node<T> getFastestRunner() {
        return fastestRunner;
    }

    public void setFastestRunner(Node<T> fastestRunner) {
        this.fastestRunner = fastestRunner;
    }
}
