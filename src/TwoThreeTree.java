public class TwoThreeTree<T> {
    private Node<T> root;
    private String comparisonType;
    private int size;
    private Node<T> fastestRunner;
    public Node<T> MIN_SENTINEL = new Node<>(null, true); // Sentinel node for minus infinity
    public Node<T> MAX_SENTINEL = new Node<>(null, true); // Sentinel node for plus infinity

    public TwoThreeTree() {
        this.root = new Node<T>(null);
        this.root.setLeft(this.MIN_SENTINEL);
        this.root.setMiddle(this.MAX_SENTINEL);
        this.root.getLeft().setParent(this.root);
        this.root.getMiddle().setParent(this.root);
        this.comparisonType = "ID";
        this.size = 0; // Tree is initially empty
        this.root.setSize(0);
        this.fastestRunner = null;
    }

    public TwoThreeTree(String comparisonType) {
        this();
        this.comparisonType = comparisonType;
    }

    public Node<T> search23(Node<T> node, T key) {
        if (node == null ||
                node.isSentinel(this.MIN_SENTINEL, this.MAX_SENTINEL)) {
            return null;
        }
        if (node.isLeaf()) {
            if (compareNodeWithKey(key, node) == 0) {
                return node;
            } else {
                return null;
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

    public Node<T> Successor23(Node<T> x) {
        if (x.getRight() != null) {
            if (!x.getRight().isMaxNode(this.MAX_SENTINEL)) {
                return findMinimum(x.getRight());
            }
        }

        Node<T> z = x.getParent();
        while (x == z.getRight() || (z.getRight() == null && x == z.getMiddle())) {
            x = z;
            z = z.getParent();
        }

        Node<T> y;
        if (x == z.getLeft()) {
            y = z.getMiddle();
        } else {
            y = z.getRight();
        }

        while (!y.isLeaf()) {
            y = y.getLeft();
        }

        if (!y.isMaxNode(MAX_SENTINEL)) {
            return y;
        } else {
            return null;
        }
    }

    private Node<T> findMinimum(Node<T> node) { // Find the minimum node in the subtree rooted at node
        while (!node.getLeft().isLeaf() && !node.getLeft().isMinNode(this.MIN_SENTINEL)) { // Find the leftmost node
            node = node.getLeft(); // Go left
        }
        return node; // Return the leftmost node
    }

    public void updateKey23(Node<T> x) {
        if (x.isSentinel(this.MIN_SENTINEL, this.MAX_SENTINEL)) {
            return;
        }

        x.setKey(x.getLeft().getKey());

        if (x.getMiddle() != null) {
            x.setKey(x.getMiddle().getKey());
        }

        if (x.getRight() != null) {
            x.setKey(x.getRight().getKey());
        }
    }

    public void setChildren23(Node<T> x, Node<T> left, Node<T> middle, Node<T> right) {
        x.setLeft(left);
        x.setMiddle(middle);
        x.setRight(right);

        left.setParent(x);

        if (middle != null) {
            middle.setParent(x);
        }
        if (right != null) {
            right.setParent(x);
        }

        updateKey23(x);
    }

    //version 4
    public Node<T> insertAndSplit23(Node<T> x, Node<T> z) {
        Node<T> l = x.getLeft();
        Node<T> m = x.getMiddle();
        Node<T> r = x.getRight();

        if (r == null) {
            if (compareNodeWithNode(z, l) < 0) {
                setChildren23(x, z, l, m);
            } else if (compareNodeWithNode(z, m) < 0) {
                setChildren23(x, l, z, m);
            } else {
                setChildren23(x, l, m, z);
            }
            updateSize(x);
            return null;
        }

        Node<T> y = new Node<T>();

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

        updateSize(x);
        updateSize(y);

        return y;
    }

    private void updateSize(Node<T> node) { // Update the size of the node
        if (node.isSentinel(this.MIN_SENTINEL, this.MAX_SENTINEL)) { // If the node is a sentinel node
            return;
        }

        // Get the size of the left child
        int leftSize = node.getLeft().isMinNode(this.MIN_SENTINEL) ? 0 : node.getLeft().getSize();
        // Get the size of the middle child
        int middleSize = (node.getMiddle() == null ||
                node.getMiddle().isMaxNode(this.MAX_SENTINEL)) ? 0 : node.getMiddle().getSize();
        // Get the size of the right child
        int rightSize = (node.getRight() == null ||
                node.getRight().isMaxNode(this.MAX_SENTINEL)) ? 0 : node.getRight().getSize();
        // Set the size of the node
        node.setSize(leftSize + middleSize + rightSize);
    }

    public void insert23(T key) {
        Node<T> z = new Node<>(key, true);
        if (this.root.getLeft().isMinNode(this.MIN_SENTINEL) && this.root.getMiddle().isMaxNode(this.MAX_SENTINEL)) {
            setChildren23(this.root, this.root.getLeft(), z, this.root.getMiddle());
            this.fastestRunner = z;
            this.root.setSize(1);
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

            if (x == null) {
                x = this.root;
            }

            Node<T> splitChild = insertAndSplit23(x, z);

            while (x != this.root) {
                x = x.getParent();
                if (splitChild != null) {
                    splitChild = insertAndSplit23(x, splitChild);
                } else {
                    updateKey23(x);
                    updateSize(x);
                }
            }

            if (splitChild != null) {
                Node<T> newRoot = new Node<>();
                setChildren23(newRoot, x, splitChild, null);
                this.root = newRoot;
                updateSize(newRoot);
            }
        }

    }

    private Node<T> borrowOrMerge23(Node<T> y) {
        Node<T> z = y.getParent();
        Node<T> x;

        if (y == z.getLeft()) {
            x = z.getMiddle();
            if (x.getRight() != null) {
                setChildren23(y, y.getLeft(), x.getLeft(), null);
                setChildren23(x, x.getMiddle(), x.getRight(), null);
                updateSize(y);
                updateSize(x);
            } else {
                setChildren23(x, y.getLeft(), x.getLeft(), x.getMiddle());
                y.setParent(null);
                setChildren23(z, x, z.getRight(), null);
                updateSize(y);
                updateSize(z);
            }
            return z;
        }

        if (y == z.getMiddle()) {
            x = z.getLeft();
            if (x.getRight() != null) {
                setChildren23(y, x.getRight(), y.getLeft(), null);
                setChildren23(x, x.getLeft(), x.getMiddle(), null);
                updateSize(y);
                updateSize(x);
            } else {
                setChildren23(x, x.getLeft(), x.getMiddle(), y.getLeft());
                y.setParent(null);
                setChildren23(z, x, z.getRight(), null);
                updateSize(x);
                updateSize(z);
            }
            return z;
        }

        x = z.getMiddle();
        if (x.getRight() != null) {
            setChildren23(y, x.getRight(), y.getLeft(), null);
            setChildren23(x, x.getLeft(), x.getMiddle(), null);
            updateSize(y);
            updateSize(x);
        } else {
            setChildren23(x, x.getLeft(), x.getMiddle(), y.getLeft());
            y.setParent(null);
            setChildren23(z, z.getLeft(), x, null);
            updateSize(y);
            updateSize(z);
        }
        return z;
    }

    public void delete23(T key, Node<T> searchResult, boolean isExist) {
        Node<T> x;
        if (!isExist) {
            x = search23(this.root, key);
        } else {
            x = searchResult;
        }
        if (x == null || x.isSentinel(this.MIN_SENTINEL, this.MAX_SENTINEL)) {
            throw new IllegalArgumentException("Key not found.");
        }
        if (fastestRunner != null && compareNodeWithNode(x, fastestRunner) == 0) {
            fastestRunner = Successor23(fastestRunner);
        }

        Node<T> y = x.getParent();
        Node<T> z;

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

        x.setParent(null);

        while (y != null) {
            if (y.getMiddle() == null) {
                if (y != this.root) {
                    y = borrowOrMerge23(y);
                } else {
                    setRoot(y.getLeft());
                    y.getLeft().setParent(null);
                    y.setParent(null);
                    return;
                }
            } else {
                updateKey23(y);
                updateSize(y);
                y = y.getParent();
            }
        }
    }

    public int Rank(Node<T> x) { // Find the rank of the node x
        int rank = 1; // Initialize the rank to 1
        Node<T> y = x.getParent(); // Get the parent of the node x
        while (y != null) { // While the parent is not null
            if (x == y.getMiddle()) { // If x is the middle child of y
                rank = rank + y.getLeft().getSize(); // Increment the rank by the size of the left child of y
            } else if (x == y.getRight()) { // If x is the right child of y
                // Increment the rank by the size of the left and middle children of y
                rank = rank + y.getLeft().getSize() + y.getMiddle().getSize();
            }

            x = y; // Set x to y
            y = y.getParent(); // Set y to the parent of y
        }

        return rank;
    }

    public Node<T> getRoot() {
        return root;
    }

    public void setRoot(Node<T> root) {
        this.root = root;
    }

    //version 2
    public int compareNodeWithKey(T key, Node<T> node) { // Compare the key of a node with a given key
        if (node.isMinNode(this.MIN_SENTINEL)) { // If the node is the minimum sentinel node
            return 1; // it is considered smaller than any real key
        } else if (node.isMaxNode(this.MAX_SENTINEL)) { // If the node is the maximum sentinel node
            // it is considered larger than any real key
            return -1;
        }

        T r1 = key; // Get the key of the node
        T r2; // Declare a variable to store the key of the node

        if (node.getKey() == null) { // If the key of the node is null
            return -1; // it is considered larger than any real key
        } else { // If the key of the node is not null
            r2 = node.getKey(); // Get the key of the node
        }

        return compareNodes(r1, r2); // Compare the two keys
    }
    public int compareNodeWithNode(Node<T> n1, Node<T> n2) { // Compare the key of a node with the key of another node
        if (n1.isMinNode(this.MIN_SENTINEL)) { // If the first node is the minimum sentinel node
            return -1; // it is considered smaller
        } else if (n1.isMaxNode(this.MAX_SENTINEL)) { // If the first node is the maximum sentinel node
            return 1; // it is considered larger
        } else if (n2.isMinNode(this.MIN_SENTINEL)) { // If the second node is the minimum sentinel node
            return 1; // it is considered smaller
        } else if (n2.isMaxNode(this.MAX_SENTINEL)) { // If the second node is the maximum sentinel node
            return -1; // it is considered larger
        }

        T r1; // Declare a variable to store the key of the first node
        if (n1.getKey() == null) { // If the key of the first node is null
            return 1; // it is considered larger
        }else { // If the key of the first node is not null
            r1 = n1.getKey(); // Get the key of the first node
        }

        T r2; // Declare a variable to store the key of the second node
        if (n2.getKey() == null) { // If the key of the second node is null
            return -1; // it is considered larger
        } else { // If the key of the second node is not null
            r2 = n2.getKey(); // Get the key of the second node
        }

        return compareNodes(r1, r2); // Compare the two keys
    }
    private int compareNodes(T r1, T r2) { // Compare two keys
        if ((r1 instanceof Runner) && (r2 instanceof Runner)) { // If the keys are instances of Runner
            switch (comparisonType) { // Switch on the comparison type
                case "ID": // If the comparison type is ID, compare the IDs of the runners
                    return compareRunnerID((Runner) r1, (Runner) r2);
                case "MinTime": // If the comparison type is MinTime, compare the minimum times of the runners
                    if (compareMinTime(((Runner) r1), ((Runner) r2)) == 0) { // If the minimum times are equal
                        return compareRunnerID((Runner) r1, (Runner) r2); // Compare the IDs of the runners
                    } else { // If the minimum times are not equal
                        return compareMinTime(((Runner) r1), ((Runner) r2)); // Compare the minimum times of the runners
                    }
                case "AvgTime": // If the comparison type is AvgTime, compare the average times of the runners
                    if (compareAvgTime(((Runner) r1), ((Runner) r2)) == 0) { // If the average times are equal
                        return compareRunnerID((Runner) r1, (Runner) r2); // Compare the IDs of the runners
                    } else { // If the average times are not equal
                        return compareAvgTime(((Runner) r1), ((Runner) r2)); // Compare the average times of the runners
                    }
                default:
                    throw new IllegalArgumentException("Invalid comparison type: " + comparisonType);
            }
        } else if (r1 instanceof Run && r2 instanceof Run){ // If the keys are instances of Run
            if (comparisonType.equals("Time")) { // If the comparison type is Time, compare the times of the runs
                return Float.compare(((Run) r1).getTime(),((Run) r2).getTime());
            }
            throw new IllegalArgumentException("Invalid comparison type: " + comparisonType);
        } else {
            throw new IllegalArgumentException("Invalid comparison type: " + comparisonType);
        }
    }

    private int compareRunnerID(Runner r1, Runner r2) { // Compare the IDs of two runners
        if (r1.getID().isSmaller(r2.getID())) { // If the ID of the first runner is smaller
            return -1;
        } else if (r2.getID().isSmaller(r1.getID())) { // If the ID of the second runner is smaller
            return 1;
        } else { // If the IDs are equal
            return 0;
        }
    }

    private int compareMinTime(Runner r1, Runner r2) { // Compare the minimum times of two runners
        if (r1.getMinTime() < r2.getMinTime()) { // If the minimum time of the first runner is smaller
            return -1;
        } else if (r2.getMinTime() < r1.getMinTime()) { // If the minimum time of the second runner is smaller
            return 1;
        } else { // If the minimum times are equal
            return 0;
        }
    }

    private int compareAvgTime(Runner r1, Runner r2) { // Compare the average times of two runners
        if (r1.getAvgTime() < r2.getAvgTime()) { // If the average time of the first runner is smaller
            return -1;
        } else if (r2.getAvgTime() < r1.getAvgTime()) { // If the average time of the second runner is smaller
            return 1;
        } else { // If the average times are equal
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
}
