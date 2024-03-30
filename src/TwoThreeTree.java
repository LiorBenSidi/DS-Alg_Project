public class TwoThreeTree<T> {
    private Node<T> root;
    private String comparisonType;
    private int size;
    private Node<T> fastestRunner; // Reference to the node with the fastest runner


    public TwoThreeTree() {
        root = new Node<T>();
        comparisonType = "ID";
        size = 0;
        this.fastestRunner = null;
    }

    public TwoThreeTree(String comparisonType) {
        this.root = new Node<T>();
        this.comparisonType = comparisonType;
        size = 0;
        this.fastestRunner = null;
    }

    public Node<T> search23(Node<T> x, T k) {
        if (x.isLeaf()) {
            if (compareNodes(k, x.getKey()) == 0) {
                return x;
            } else {
                return null;
            }
        }
        if (compareNodes(k, x.getLeft().getKey()) <= 0) {
            return search23(x.getLeft(), k);
        } else if (compareNodes(k, x.getMiddle().getKey()) <= 0) {
            return search23(x.getMiddle(), k);
        } else {
            return search23(x.getRight(), k);
        }
    }

    public Node<T> minimum23() {
        Node<T> x = this.root;
        while (!x.isLeaf()) {
            x = x.getLeft();
        }
        x = x.getParent().getMiddle();
        if (x.getSentinel() != 1) { /* instead of the sentinels */
            return x;
        } else {
            return null; /* instead of "error: T is empty" */
        }
    }

    public Node<T> successor23(Node<T> x) {
        Node<T> z = x.getParent();
        while ((x.equals(z.getRight()))
                ||
                ((z.getRight() == null) && (x.equals(z.getMiddle())))) {
            x = z;
            z = z.getParent();
        }
        Node<T> y;
        if (x.equals(z.getLeft())) {
            y = z.getMiddle();
        } else {
            y = z.getRight();
        }
        while (y.getLeft() != null) {
            y = y.getLeft();
        }
        if (y.getSentinel() < 1) { /* instead of the sentinels */
            return y;
        } else {
            return null;
        }
    }

    public void updateKey23(Node<T> x)
    {
        if (x.getLeft() == null) {
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

    private void setChildren23(Node<T> x, Node<T> l, Node<T> m, Node<T> r) {
        x.setLeft(l);
        x.setMiddle(m);
        x.setRight(r);
        if (l != null) {
            l.setParent(x);
        }
        if (m != null) {
            m.setParent(x);
        }
        if (r != null) {
            r.setParent(x);
        }
        updateKey23(x);
    }

    public Node<T> insertAndSplit23(Node<T> x, Node<T> z) {
        Node<T> l = x.getLeft();
        Node<T> m = x.getMiddle();
        Node<T> r = x.getRight();

        // This is the node that will be the "middle" node after the split,
        // which will be promoted to the parent level.
        Node<T> newInternalNode = new Node<T>();

        // This will hold the smaller keys
        Node<T> newLeftNode = new Node<T>();

        // This will hold the larger keys
        Node<T> newRightNode = new Node<T>();

        if (r == null) { // No split needed, just insert and return null.
            if (compareNodes(z.getKey(), l.getKey()) < 0) {
                setChildren23(x, z, l, m);
            } else if (compareNodes(z.getKey(), m.getKey()) < 0) {
                setChildren23(x, l, z, m);
            } else {
                setChildren23(x, l, m, z);
            }
            return null;
        } else { // Split needed.
            // Determine the order of keys and split the node.
            if (compareNodes(z.getKey(), l.getKey()) < 0) {
                newLeftNode.setKey(z.getKey());
                newInternalNode.setKey(l.getKey());
                newRightNode.setKey(m.getKey());
                setChildren23(newLeftNode, z.getLeft(), z.getMiddle(), null);
                setChildren23(newRightNode, m.getLeft(), m.getMiddle(), null);
            } else if (compareNodes(z.getKey(), m.getKey()) < 0) {
                newLeftNode.setKey(l.getKey());
                newInternalNode.setKey(z.getKey());
                newRightNode.setKey(m.getKey());
                setChildren23(newLeftNode, l.getLeft(), l.getMiddle(), null);
                setChildren23(newRightNode, z.getLeft(), z.getMiddle(), null);
            } else { // z is the largest
                newLeftNode.setKey(l.getKey());
                newInternalNode.setKey(m.getKey());
                newRightNode.setKey(z.getKey());
                setChildren23(newLeftNode, l.getLeft(), l.getMiddle(), null);
                setChildren23(newRightNode, m.getLeft(), z.getMiddle(), null);
            }

            // Now, set children of the new internal node.
            setChildren23(newInternalNode, newLeftNode, newRightNode, null);

            // Update the parent links for the new nodes.
            newLeftNode.setParent(newInternalNode);
            newRightNode.setParent(newInternalNode);

            // If x had a parent, it means this split is happening internally
            // and we must handle the middle key moving up the tree.
            if (x.getParent() != null) {
                newInternalNode.setParent(x.getParent());
            }

            return newInternalNode; // Return the new internal node that should be inserted in the tree.
        }
    }

    /*
        Node<T> l = x.getLeft();
        Node<T> m = x.getMiddle();
        Node<T> r = x.getRight();

        if (r == null) {
            if (compareNodes(z.getKey(), l.getKey()) < 0) {
                setChildren23(x, z, l, m);
            } else if (compareNodes(z.getLeft().getKey(), m.getKey()) < 0) {
                setChildren23(x, l, z, m);
            } else {
                setChildren23(x, l, m, z);
            }
            return null;
        }
        Node<T> y = new Node<T>();
        if (compareNodes(z.getKey(), l.getKey()) < 0) {
            setChildren23(x, z, l, null);
            setChildren23(y , m, r, null);
        } else if (compareNodes(z.getKey(), m.getKey()) < 0) {
            setChildren23(x, l, z, null);
            setChildren23(y, m, r, null);
        } else if (compareNodes(z.getKey(), r.getKey()) < 0) {
            setChildren23(x, l, m, null);
            setChildren23(y, z, r, null);
        } else {
            setChildren23(x, l, m, null);
            setChildren23(y, r, z, null);
        }

        return y;
         */

    public void insertIntoLeaf(Node<T> leaf, Node<T> z) {
        // Insert z into the correct position relative to leaf
        if (leaf.getKey() == null) {
            // This is the first insert, and leaf is actually root
            leaf.setKey(z.getKey());
            leaf.setSize(1);  // Since this is the first key, size is 1
            leaf.setLeaf(true); // A leaf node with one key
        } else {
            // There's an existing key, and we need to create a new node
            Node<T> newLeaf = new Node<T>(); // Create a new leaf node for the new key
            newLeaf.setKey(z.getKey());
            newLeaf.setSize(1);
            newLeaf.setLeaf(true);

            // Compare keys to determine the order
            if (compareNodes(newLeaf.getKey(), leaf.getKey()) < 0) {
                // New key is smaller, make it the left child
                Node<T> parent = new Node<T>(); // New root
                setChildren23(parent, newLeaf, leaf, null); // Set newLeaf and leaf as children
                setRoot(parent); // Set parent as new root
            } else {
                // New key is larger, make it the right child
                Node<T> parent = new Node<T>(); // New root
                setChildren23(parent, leaf, newLeaf, null); // Set leaf and newLeaf as children
                setRoot(parent); // Set parent as new root
            }
        }
    }

    public void insert23(Node<T> z) {
        // New node initially has size 1
        z.setSize(1);

        // If the tree is empty, make z the root and return
        if (root == null || root.getKey() == null) {
            root = z;
            root.setSize(1);
            root.setLeaf(true); // It's the only node, so it's a leaf
            return;
        }

        Node<T> y = root;
        while (!y.isLeaf()) {
            y.setSize(y.getSize() + 1); // Update size as we go down
            if (compareNodes(z.getKey(), y.getLeft().getKey()) < 0) {
                y = y.getLeft();
            } else if (y.getMiddle() != null && compareNodes(z.getKey(), y.getMiddle().getKey()) < 0) {
                y = y.getMiddle();
            } else {
                y = y.getRight();
            }
        }

        Node<T> x = y.getParent();
        if (x != null) {
            z = insertAndSplit23(x, z);
        } else {
            // y is the root and it's a leaf, so we're inserting the first value after root was created
            insertIntoLeaf(y, z);
            return;
        }

        // Propagate the split up the tree as necessary
        while (x != null) {
            if (z != null) {
                z = insertAndSplit23(x, z);
                x = (z != null) ? z.getParent() : x.getParent();
            } else {
                updateKey23(x);
                x = x.getParent();
            }
        }

        // If we have a new node after splitting the root, create a new root
        if (z != null) {
            Node<T> newRoot = new Node<T>();
            setChildren23(newRoot, root, z, null);
            setRoot(newRoot);
            newRoot.setLeaf(false);
            root = newRoot;
        }

        /*
        z.setSize(1); // New node initially has size 1
        Node<T> y = root;
        while (y != null && !y.isLeaf()) {
            if (y.getLeft() != null && compareNodes(z.getKey(), y.getLeft().getKey()) < 0) {
                y = y.getLeft();
            } else if (compareNodes(z.getKey(), y.getMiddle().getKey()) < 0) {
                y = y.getMiddle();
            } else {
                y = y.getRight();
            }
        }

        Node<T> x = null;
        if (y != null) {
            x = y.getParent();
        }

        if (x != null) {
            z = insertAndSplit23(x, z);
        }

        while (x != null && !(x.equals(root))) {
            x = x.getParent();
            if (z != null) {
                if (z.getParent() != null) {
                    z = insertAndSplit23(x, z);
                }
            } else {
                updateKey23(x);
            }
        }

        if (z != null) {
            Node<T> w = new Node<T>();
            setChildren23(w, x, z, null);
            setRoot(w);
            root.setKey(z.getKey());
            root.setLeaf(false);
        }

        // After insertion, update the fastestRunner reference if necessary
        if (fastestRunner == null || compareNodes((z != null ? z.getKey() : null), fastestRunner.getKey()) < 0) {
            fastestRunner = z;
        }

        // After insertion, update sizes:
        while (y != null) {
            y.setSize(y.getSize() + 1); // Increment size of current node
            y = y.getParent(); // Move up to update all ancestors
        }
         */
    }

    public Node<T> borrowOrMerge23(Node<T> y) {
        Node<T> z = y.getParent();
        if (y.equals(z.getLeft())) {
            Node<T> x = z.getMiddle();
            if (x.getRight() != null) {
                setChildren23(y, y.getLeft(), x.getLeft(), null);
                setChildren23(x, x.getMiddle(), x.getRight(), null);
            } else {
                setChildren23(x, y.getLeft(), x.getLeft(), x.getMiddle());
                /* delete y? */
                setChildren23(z, x, z.getRight(), null);
            }
            return z;
        }
        if (y.equals(z.getMiddle())) {
            Node<T> x = z.getLeft();
            if (x.getRight() != null) {
                setChildren23(y, x.getRight(), y.getLeft(), null);
                setChildren23(x, x.getLeft(), x.getMiddle(), null);
            } else {
                setChildren23(x, x.getLeft(), x.getMiddle(), y.getLeft());

                setChildren23(z, x, z.getRight(), null);
            }
            return z;
        }
        Node<T> x = z.getMiddle();
        if (x.getRight() != null) {
            setChildren23(y, x.getRight(), y.getLeft(), null);
            setChildren23(x, x.getLeft(), x.getMiddle(), null);
        } else {
            setChildren23(x, x.getLeft(), x.getMiddle(), y.getLeft());
            /* delete y? */
            setChildren23(z, z.getLeft(), x, null);
        }
        return z;
    }

    public void delete23(Node<T> x) {
        Node<T> y = x.getParent();
        Node<T> current = root;
        if (x.equals(y.getLeft())) {
            setChildren23(y, y.getMiddle(), y.getRight(), null);
        } else if (x.equals(y.getMiddle())) {
            setChildren23(y, y.getLeft(), y.getRight(), null);
        } else {
            setChildren23(y, y.getLeft(), y.getMiddle(), null);
        }

        // Prepare node x for garbage collection
        x.setLeft(null);
        x.setMiddle(null);
        x.setRight(null);
        x.setParent(null);
        x.setKey(null);

        while (y != null) {
            if (y.getMiddle() == null) {
                if (y != current) {
                    y = borrowOrMerge23(y);
                } else {
                    setRoot(y.getLeft());
                    y.setLeft(null); // Prepare y for garbage collection if it's the old root
                    return;
                }
            } else {
                updateKey23(y);
                y = y.getParent();
            }
        }

        // After deletion, update the fastestRunner reference if necessary
        if (fastestRunner != null && fastestRunner.equals(x.getKey())) {
            // Find the new fastest runner in the tree
            fastestRunner = findFastestRunner(current);
        }

        // After deletion, update sizes:
        while (current != null) {
            current.setSize(current.getSize() - 1); // Decrement size of current node
            current = current.getParent(); // Move up to update all ancestors
        }
    }

    private Node<T> findFastestRunner(Node<T> node) {
        if (node == null || node.isLeaf()) {
            return node;
        }

        Node<T> leftMin = findFastestRunner(node.getLeft());
        Node<T> middleMin = findFastestRunner(node.getMiddle());
        Node<T> rightMin = node.getRight() != null ? findFastestRunner(node.getRight()) : null;

        Node<T> minNode = leftMin;
        if (middleMin != null && compareNodes(middleMin.getKey(), minNode.getKey()) < 0) {
            minNode = middleMin;
        }
        if (rightMin != null && compareNodes(rightMin.getKey(), minNode.getKey()) < 0) {
            minNode = rightMin;
        }

        return minNode;
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
        } else {
            if (comparisonType.equals("Time")) {
                return Float.compare((Float) r1, (Float) r2);
            }
            throw new IllegalArgumentException("Invalid comparison type: " + comparisonType);
        }
    }

    private int compareRunnerID(Runner r1, Runner r2) {
        if (r1.getID().isSmaller(r2.getID())) {
            return -1;
        } else if (!r1.getID().isSmaller(r2.getID())) {
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
