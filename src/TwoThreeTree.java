public class TwoThreeTree<T> {
    private Node<T> root; // Root of the tree
    private String comparisonType; // Type of comparison to use for the tree
    private int size; // Number of nodes in the tree
    private Node<T> fastestRunner; // Reference to the node with the fastest runner(Run) in the tree

    public TwoThreeTree() {
        this.root = new Node<T>();
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
        if (node.isSentinel()) { // Check if the node is a sentinel
            return null; // Key not found, end of search path
        }
        if (node.isLeaf()) {
            if (compareNodeWithKey(node, key) == 0) {
                return node; // Key found
            } else {
                return null; // Key not found
            }
        } else {
            if (compareNodeWithKey(node.getLeft(), key) >= 0) {
                return search23(node.getLeft(), key);
            } else if (compareNodeWithKey(node.getMiddle(), key) >= 0) {
                return search23(node.getMiddle(), key);
            } else {
                return search23(node.getRight(), key);
            }
        }
    }

    //version 1
    /*
    public Node<T> search23(Node<T> x, T k) {
        if (x.isLeaf() && x.getKey() != null) {
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
        } else if (x.getRight() != null){
            return search23(x.getRight(), k);
        } else {
            return null;
        }
    }
     */


    //version 2
    public Node<T> minimum23() {
        Node<T> x = this.root;
        while (!x.isLeaf() && !x.getLeft().isMinNode()) {
            x = x.getLeft(); // Continue to the leftmost child
        }
        x = x.getParent().getMiddle(); // Get the middle child of the parent
        if (!x.isMaxNode()) {
            return x;
        } else {
            throw new IllegalArgumentException("Tree is empty.");
        }
    }
    //version 1
    /*
    public Node<T> minimum23() {
        Node<T> x = this.root;
        while (!x.isLeaf()) {
            x = x.getLeft();
        }
        x = x.getParent().getMiddle();
        if (x.getSentinel() != 1) {
            return x;
        } else {
            return null;
        }
    }
    */

    //version 2
    public Node<T> findSuccessor(Node<T> x) {
        // If the node has a right child, the successor is the minimum key in the right subtree.
        if (!x.getRight().isSentinel()) {
            return findMinimum(x.getRight());
        }

        // Otherwise, go up the tree until we find a node that is the left or middle child of its parent.
        Node<T> z = x.getParent();
        while (!z.isSentinel() && (x == z.getRight() || (z.getRight().isSentinel() && x == z.getMiddle()))) {
            x = z;
            z = z.getParent();
        }

        // If we're at the root of the tree, there is no successor.
        if (z.isSentinel()) {
            return null;
        }

        // The successor is either the middle or right child of the parent node.
        if (x == z.getLeft()) {
            return z.getMiddle().isSentinel() ? z.getRight() : findMinimum(z.getMiddle());
        } else {
            return z.getRight();
        }
    }

    //used in successor23
    private Node<T> findMinimum(Node<T> node) {
        while (!node.getLeft().isSentinel()) {
            node = node.getLeft();
        }
        return node;
    }

    //version 1
    /*
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
        if (y.getSentinel() < 1) {
            return y;
        } else {
            return null;
        }
    }
    */

    //version 2
    public void updateKey23(Node<T> x) {
        if (x.isSentinel()) {
            // If x is a sentinel, then there is nothing to update.
            return;
        }

        // Start with the leftmost key, since x.left should never be a sentinel.
        x.setKey(x.getLeft().getKey());

        // Update x.key to the key of x.middle if x.middle is not a sentinel.
        if (!x.getMiddle().isSentinel()) {
            x.setKey(x.getMiddle().getKey());
        }

        // Update x.key to the key of x.right if x.right is not a sentinel.
        if (!x.getRight().isSentinel()) {
            x.setKey(x.getRight().getKey());
        }

        // Update the size attribute if necessary
        x.setSize(calculateSubtreeSize(x));
    }

    //version 1
    /*
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
     */
    //used in updateKey
    private int calculateSubtreeSize(Node<T> node) {
        if (node.isSentinel()) {
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
        x.setLeft(left != null ? left : (Node<T>) Node.SENTINEL);
        x.setMiddle(middle != null ? middle : (Node<T>) Node.SENTINEL);
        x.setRight(right != null ? right : (Node<T>) Node.SENTINEL);

        // Update the parent pointers of the children to x
        if (left != Node.SENTINEL) {
            left.setParent(x);
        }
        if (middle != Node.SENTINEL) {
            middle.setParent(x);
        }
        if (right != Node.SENTINEL) {
            right.setParent(x);
        }

        // Update the key of x to the maximum key in its subtree
        updateKey23(x);
    }

    //version 3
    /*
    public void setChildren23(Node<T> x, Node<T> left, Node<T> middle, Node<T> right) {
        // Set the children of x to either a valid node or the sentinel node.
        x.setLeft(left == null ? (Node<T>) Node.SENTINEL : left);
        x.setMiddle(middle == null ? (Node<T>) Node.SENTINEL : middle);
        x.setRight(right == null ? (Node<T>) Node.SENTINEL : right);

        // Update parent pointers. No need to check for sentinel here,
        // because setLeft/setMiddle/setRight already handle nulls.
        if (x.getLeft() != Node.SENTINEL) {
            x.getLeft().setParent(x);
        }
        if (x.getMiddle() != Node.SENTINEL) {
            x.getMiddle().setParent(x);
        }
        if (x.getRight() != Node.SENTINEL) {
            x.getRight().setParent(x);
        }

        // Update the key and size of x.
        updateKey23(x);
    }
     */
    //version 2
    /*
    public void setChildren23(Node<T> x, Node<T> left, Node<T> middle, Node<T> right) {
        // Set the children of x
        x.setLeft(left != null ? left : (Node<T>) Node.SENTINEL);
        x.setMiddle(middle != null ? middle : (Node<T>) Node.SENTINEL);
        x.setRight(right != null ? right : (Node<T>) Node.SENTINEL);

        // Set the parent pointers of the children to x
        left.setParent(x);

        if (!middle.isSentinel()) {
            middle.setParent(x);
        }
        if (!right.isSentinel()) {
            right.setParent(x);
        }

        // Update the key of x to the maximum key in its subtree
        updateKey23(x);
    }
     */
    //version 1
    /*
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
     */

    //version 4
    public Node<T> insertAndSplit23(Node<T> x, Node<T> z) {
        // Assume `z` is already created and `z.key` has been set.

        // Variables to hold children for easy access and comparison.
        Node<T> l = x.getLeft();
        Node<T> m = x.getMiddle();
        Node<T> r = x.getRight();

        // Create a new node `y` that might be the result of a split.
        Node<T> y = new Node<T>();

        // Case 1: x has only 2 children (x.right is sentinel).
        if (r.isSentinel()) {
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

        // Update keys and sizes after the split.
        updateKey23(x);
        updateKey23(y);
        updateSize(x);
        updateSize(y);

        return y;
    }

    //used in insertAndSplit23
    private void updateSize(Node<T> node) {
        if (node.isSentinel()) {
            return;
        }
        int leftSize = node.getLeft().isSentinel() ? 0 : node.getLeft().getSize();
        int middleSize = node.getMiddle().isSentinel() ? 0 : node.getMiddle().getSize();
        int rightSize = node.getRight().isSentinel() ? 0 : node.getRight().getSize();
        node.setSize(leftSize + middleSize + rightSize + 1); // +1 for the current node if it is not a leaf.
    }

    //version 3.5
    /*
    private void replaceChildInParent(Node<T> parent, Node<T> oldChild, Node<T> newChild) {
        if (parent.getLeft() == oldChild) {
            parent.setLeft(newChild);
        } else if (parent.getMiddle() == oldChild) {
            parent.setMiddle(newChild);
        } else if (parent.getRight() == oldChild) {
            parent.setRight(newChild);
        }
        newChild.setParent(parent);
    }

    public Node<T> insertAndSplit23(Node<T> x, Node<T> z) {
        Node<T> l = x.getLeft();
        Node<T> m = x.getMiddle();
        Node<T> r = x.getRight();

        if (r == null) {
            // No split needed, just insert
            if (compareNodes(z.getKey(), l.getKey()) < 0) {
                setChildren23(x, z, l, m);
            } else if (compareNodes(z.getKey(), m.getKey()) < 0) {
                setChildren23(x, l, z, m);
            } else {
                setChildren23(x, l, m, z);
            }
            x.setSize(x.getLeft().getSize() + x.getMiddle().getSize() + (x.getRight() != null ? x.getRight().getSize() : 0));
            return null;
        }

        // Split needed
        Node<T> newInternalNode = new Node<T>();
        Node<T> newLeftNode = new Node<T>();
        Node<T> newRightNode = new Node<T>();

        if (compareNodes(z.getKey(), l.getKey()) < 0) {
            newLeftNode = z;
            newRightNode = new Node<T>(m, r, null, newInternalNode, r.getKey(), false, m.getSize() + r.getSize());
        } else if (compareNodes(z.getKey(), m.getKey()) < 0) {
            newLeftNode = l;
            newRightNode = new Node<T>(z, r, null, newInternalNode, r.getKey(), false, z.getSize() + r.getSize());
        } else {
            newLeftNode = l;
            newRightNode = new Node<T>(m, z, null, newInternalNode, z.getKey(), false, m.getSize() + z.getSize());
        }

        setChildren23(newInternalNode, newLeftNode, newRightNode, null);
        newInternalNode.setSize(newLeftNode.getSize() + newRightNode.getSize());

        if (x.getParent() != null) {
            replaceChildInParent(x.getParent(), x, newInternalNode);
        } else {
            setRoot(newInternalNode);
        }

        return newInternalNode;
    }
     */
    //version 3**
    /*
    public Node<T> insertAndSplit23(Node<T> x, Node<T> z) {
        Node<T> l = x.getLeft();
        Node<T> m = x.getMiddle();
        Node<T> r = x.getRight();

        // Check if a split is needed
        if (r == null) {
            // No split needed, just insert
            if (compareNodes(z.getKey(), l.getKey()) < 0) {
                setChildren23(x, z, l, m);
            } else if (compareNodes(z.getKey(), m.getKey()) < 0) {
                setChildren23(x, l, z, m);
            } else {
                setChildren23(x, l, m, z);
            }
            x.setSize(x.getLeft().getSize() + x.getMiddle().getSize() + (x.getRight() != null ? x.getRight().getSize() : 0));
            return null;
        }

        // Split needed
        Node<T> newInternalNode = new Node<T>();
        newInternalNode.setSize(x.getSize() + 1); // Size of new internal node

        // Determine the order of the nodes and set their keys
        if (compareNodes(z.getKey(), l.getKey()) < 0) {
            setChildren23(x, z, l, null);
            setChildren23(newInternalNode, m, r, null);
            //x.setSize(x.getLeft().getSize() + x.getMiddle().getSize()); // Update size of x
        } else if (compareNodes(z.getKey(), m.getKey()) < 0) {
            setChildren23(x, l, z, null);
            setChildren23(newInternalNode, m, r, null);
            //x.setSize(x.getLeft().getSize() + x.getMiddle().getSize()); // Update size of x
        } else if (compareNodes(z.getKey(), r.getKey()) < 0) {
            setChildren23(x, l, m, null);
            setChildren23(newInternalNode, z, r, null);
        } else {
            setChildren23(newInternalNode, r, z, null);
        }

        // Update size of new internal node and its children
        newInternalNode.getLeft().setSize(m.getSize());
        newInternalNode.getMiddle().setSize(r.getSize());
        newInternalNode.setSize(newInternalNode.getLeft().getSize() + newInternalNode.getMiddle().getSize());

        return newInternalNode;
    }
     */
    //version 3*
    /*
    public Node<T> insertAndSplit23(Node<T> x, Node<T> z) {
        Node<T> l = x.getLeft();
        Node<T> m = x.getMiddle();
        Node<T> r = x.getRight();

        // Check if a split is needed
        if (r == null) {
            // No split needed, just insert
            if (compareNodes(z.getKey(), l.getKey()) < 0) {
                setChildren23(x, z, l, m);
            } else if (compareNodes(z.getKey(), m.getKey()) < 0) {
                setChildren23(x, l, z, m);
            } else {
                setChildren23(x, l, m, z);
            }
            return null;
        }
        // Split needed
        Node<T> newInternalNode = new Node<T>();
        //Node<T> newLeftNode = new Node<T>();
        //Node<T> newRightNode = new Node<T>();

        // Determine the order of the nodes and set their keys
        if (compareNodes(z.getKey(), l.getKey()) < 0) {
            setChildren23(x, z, l, null);
            setChildren23(newInternalNode, m, r, null);
            //newLeftNode.setKey(z.getKey());
            //newInternalNode.setKey(l.getKey());
            //newRightNode.setKey(m.getKey());
        } else if (compareNodes(z.getKey(), m.getKey()) < 0) {
            setChildren23(x, l, z, null);
            setChildren23(newInternalNode, m, r, null);
            //newLeftNode.setKey(l.getKey());
            //newInternalNode.setKey(z.getKey());
            //newRightNode.setKey(m.getKey());
        } else if (compareNodes(z.getKey(), r.getKey()) < 0) {
            setChildren23(x, l, m, null);
            setChildren23(newInternalNode, z, r, null);
            //newLeftNode.setKey(l.getKey());
            //newInternalNode.setKey(m.getKey());
            //newRightNode.setKey(z.getKey());
        } else {
            setChildren23(newInternalNode, r, z, null);
        }

        // Set the children for the new nodes
        //setChildren23(newInternalNode, newLeftNode, newRightNode, null);
        //newLeftNode.setParent(newInternalNode);
        //newRightNode.setParent(newInternalNode);

        // If the split node has a parent, update its reference
        //if (x.getParent() != null) {
        //    newInternalNode.setParent(x.getParent());
        //    replaceChildInParent(x.getParent(), x, newInternalNode);
        //}

        return newInternalNode;
    }
    */
    //version 2
    /*
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
    */
    //version 1
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



    //insertIntoLeaf
    //version 2
    /*
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

            Node<T> parent = new Node<T>(); // New root
            // Compare keys to determine the order
            if (compareNodes(newLeaf.getKey(), leaf.getKey()) < 0) {
                // New key is smaller, make it the left child
                parent = new Node<T>(); // New root
                setChildren23(parent, newLeaf, leaf, null); // Set newLeaf and leaf as children
                setRoot(parent); // Set parent as new root
            } else {
                // New key is larger, make it the right child
                parent = new Node<T>(); // New root
                setChildren23(parent, leaf, newLeaf, null); // Set leaf and newLeaf as children
                setRoot(parent); // Set parent as new root
            }

            // Update the size of the ancestors
            Node<T> current = parent;
            while (current != null) {
                current.setSize(current.getLeft().getSize() + (current.getMiddle() != null ? current.getMiddle().getSize() : 0));
                current = current.getParent();
            }
        }
    }
     */
    //version 1
    /*
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
    */


    //example to split the root:
    //while:
    //2 is the root
    //0 is left child
    //1 is middle child
    //2 is right child
    //and we are enter a node with key = 3
    //
    //the tree should be looking like that:
    //       New Root (key = 3)
    //         /             \
    //   (key = 1)           (key = 3)
    //   /    \              /       \
    //(key = 0) (key = 1) (key = 2)  (key = 3)

    //version 4.2
    public void insert23(T key) {
        Node<T> z = new Node<>(key); // Node constructor sets children to SENTINEL and size to 1
        if (this.root.isSentinel()) {
            this.root = z; // Tree was empty, z becomes new root
            z.setParent((Node<T>) Node.SENTINEL); // Root's parent is sentinel
            z.setLeaf(true); // The root is a leaf if it has no children
        } else {
            Node<T> y = this.root;
            Node<T> x = null;
            while (!y.isLeaf()) {
                x = y; // x follows y down the tree
                if (compareNodes(z.getKey(), y.getLeft().getKey()) < 0) {
                    y = y.getLeft();
                } else if (y.getMiddle().isSentinel() || compareNodes(z.getKey(), y.getMiddle().getKey()) < 0) {
                    y = y.getMiddle();
                } else {
                    y = y.getRight();
                }
            }
            // x is now the parent of y, or x is root if y was root
            if (x == null) {
                x = this.root; // If y was the root, then it's x and we'll split the root
            }
            Node<T> splitChild = insertAndSplit23(x, z);
            // If root was split, handle the new root creation
            if (splitChild != null && x == this.root) {
                Node<T> newRoot = new Node<>();
                setChildren23(newRoot, x, splitChild, null);
                this.root = newRoot;
                updateSize(newRoot); // Update the size from the root
            }
        }
    }

    //version 4.1
    /*
    public void insert23(T key) {
        Node<T> z = new Node<>(key); // Assume Node constructor sets left, middle, right to SENTINEL and size to 1.
        if (this.root.isSentinel()) {
            this.root = z; // Tree was empty, z becomes new root.
            z.setParent((Node<T>) Node.SENTINEL); // Root's parent is sentinel
            z.setLeaf(true); // The root is also a leaf if it has no children
        } else {
            Node<T> y = this.root;
            Node<T> x = null;
            while (!y.isLeaf()) {
                x = y; // x follows y down the tree
                if (compareNodes(z.getKey(), y.getLeft().getKey()) < 0) {
                    y = y.getLeft();
                } else if (y.getMiddle().isSentinel() || compareNodes(z.getKey(), y.getMiddle().getKey()) < 0) {
                    y = y.getMiddle();
                } else {
                    y = y.getRight();
                }
            }
            x = y.getParent(); // x is the parent of y
            // If we've reached a leaf, x cannot be null, it's the parent of y.
            if (x == null) {
                throw new IllegalStateException("Parent node is unexpectedly null");
            }
            Node<T> splitChild = insertAndSplit23(x, z);
            // Propagate split up the tree
            while (splitChild != null && x != this.root) {
                z.setLeaf(false); // New internal nodes are not leaves
                x = x.getParent(); // Go up the tree
                splitChild = insertAndSplit23(x, splitChild);
            }
            if (splitChild != null) {
                // New root needs to be created
                Node<T> newRoot = new Node<>();
                newRoot.setLeaf(false);
                setChildren(newRoot, x, splitChild, (Node<T>) Node.SENTINEL);
                this.root = newRoot;
            }
        }
        updateSize(this.root); // Update the size from the root
    }
     */
    //version 4
    /*
    public void insert23(T key) {
        Node<T> z = new Node<>(key); // Assume Node constructor sets left, middle, right to SENTINEL and size to 1.
        if (this.root.isSentinel()) {
            this.root = z; // Tree was empty, z becomes new root.
            z.setLeaf(false);
            z.getMiddle().setKey(key);
        } else {
            Node<T> y = this.root;
            Node<T> x;
            while (!y.isLeaf()) {
                x = y;
                // Sentinel checks are implicit because we should never go down a sentinel path.
                if (compareNodes(z.getKey(), y.getLeft().getKey()) < 0) {
                    y = y.getLeft();
                } else if (y.getMiddle().isSentinel() || compareNodes(z.getKey(), y.getMiddle().getKey()) < 0) {
                    y = y.getMiddle();
                } else {
                    y = y.getRight();
                }
            }
            x = y.getParent();
            Node<T> splitChild = insertAndSplit23(x, z);
            while (splitChild != null) {
                if (x == this.root) {
                    Node<T> newRoot = new Node<>();
                    setChildren23(newRoot, x, splitChild, null);
                    this.root = newRoot;
                    updateSize(newRoot);
                    splitChild = null;
                } else {
                    x = x.getParent();
                    splitChild = insertAndSplit23(x, splitChild);
                }
            }
            updateSize(x); // Update size from insertion point up to root.
        }
    }
     */
    //version 3
    /*
    public void insert23(Node<T> z) {
        z.setSize(1); // Set the size of the new node to 1

        // If the tree is empty, make z the root and return
        if (root == null || root.getKey() == null) {
            root = z;
            root.setSize(1);
            root.setLeaf(true); // It's the only node, so it's a leaf
            return;
        }

        Node<T> y = root;
        while (y != null && !y.isLeaf()) {
            y.setSize(y.getSize() + 1); // Update size as we go down
            if (compareNodes(z.getKey(), y.getLeft().getKey()) < 0) {
                y = y.getLeft();
            } else if (y.getMiddle() != null && compareNodes(z.getKey(), y.getMiddle().getKey()) < 0) {
                y = y.getMiddle();
            } else {
                // If the right child is null, it means we're at a 2-node and we should use the middle child
                y = (y.getRight() != null) ? y.getRight() : y.getMiddle();
            }
        }

        Node<T> x = y != null ? y.getParent() : null;
        if (x != null) {
            z = insertAndSplit23(x, z);
        } else {
            // y is the root, and it's a leaf, so we're inserting the first value after root was created
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
                //x.setSize(x.getLeft().getSize() + (x.getMiddle() != null ? x.getMiddle().getSize() : 0) + (x.getRight() != null ? x.getRight().getSize() : 0));
                x = x.getParent();
            }
        }

        // If we have a new node after splitting the root, create a new root
        if (z != null) {
            Node<T> newRoot = new Node<T>();
            setChildren23(newRoot, root, z, null);
            setRoot(newRoot);
            newRoot.setLeaf(false);
            newRoot.setSize(root.getSize() + z.getSize());
            root = newRoot;
        }
    }
     */
    //version 2(correct but the size update is wrong)
    /*
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
        while (y != null && !y.isLeaf()) {
            y.setSize(y.getSize() + 1); // Update size as we go down
            if (compareNodes(z.getKey(), y.getLeft().getKey()) < 0) {
                y = y.getLeft();
            } else if (y.getMiddle() != null && compareNodes(z.getKey(), y.getMiddle().getKey()) < 0) {
                y = y.getMiddle();
            } else {
                // If the right child is null, it means we're at a 2-node and we should use the middle child
                y = (y.getRight() != null) ? y.getRight() : y.getMiddle();
            }
        }

        Node<T> x = y != null ? y.getParent() : null;
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
    }
    */
    //version 1
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

    //version 2
    private Node<T> borrowOrMerge23(Node<T> y) {
        Node<T> z = y.getParent();
        Node<T> x;

        // Choose sibling to borrow from or merge with.
        if (y == z.getLeft()) {
            x = z.getMiddle().isSentinel() ? z.getRight() : z.getMiddle();
            if (!x.getRight().isSentinel()) { // Borrow from x
                // y borrows the leftmost child of x
                setChildren(y, y.getLeft(), x.getLeft(), null);
                setChildren(x, x.getMiddle(), x.getRight(), null);
                updateKey23(y);
                updateKey23(x);
                updateSize(y);
                updateSize(x);
            } else { // Merge y and x
                setChildren(y, y.getLeft(), x.getLeft(), x.getMiddle());
                // Remove x from parent
                if (!z.getMiddle().isSentinel()) {
                    z.setMiddle(z.getRight());
                }
                z.setRight((Node<T>) Node.SENTINEL);
                updateKey23(y);
                updateSize(y);
                updateKey23(z);
                updateSize(z);
                if (z == this.root && z.getRight().isSentinel()) {
                    this.root = y; // New root after merge
                    y.setParent((Node<T>) Node.SENTINEL);
                    return null; // Tree has decreased in height
                }
            }
        } else if (y == z.getMiddle()) {
            x = z.getLeft();
            // Similar logic as above, adjusting for y being the middle child.
            // ...
        } else { // y == z.getRight()
            x = z.getMiddle().isSentinel() ? z.getLeft() : z.getMiddle();
            // Similar logic as above, adjusting for y being the right child.
            // ...
        }

        // Return the parent node to continue the deletion process up the tree.
        return z;
    }

    //used in borrowOrMerge23
    private void setChildren(Node<T> node, Node<T> left, Node<T> middle, Node<T> right) {
        node.setLeft(left != null ? left : (Node<T>) Node.SENTINEL);
        node.setMiddle(middle != null ? middle : (Node<T>) Node.SENTINEL);
        node.setRight(right != null ? right : (Node<T>) Node.SENTINEL);

        // Update parent pointers
        if (!left.isSentinel()) left.setParent(node);
        if (!middle.isSentinel()) middle.setParent(node);
        if (!right.isSentinel()) right.setParent(node);

        // Update the key and size of the node
        updateKey23(node);
        updateSize(node);
    }

    //version 1
    /*
    public Node<T> borrowOrMerge23(Node<T> y) {
        Node<T> z = y.getParent();
        if (y.equals(z.getLeft())) {
            Node<T> x = z.getMiddle();
            if (x.getRight() != null) {
                setChildren23(y, y.getLeft(), x.getLeft(), null);
                setChildren23(x, x.getMiddle(), x.getRight(), null);
            } else {
                setChildren23(x, y.getLeft(), x.getLeft(), x.getMiddle());
                //delete y?
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
            // delete y?
            setChildren23(z, z.getLeft(), x, null);
        }
        return z;
    }
     */


    //version 3
    public void delete23(T key) {
        Node<T> x = search23(this.root, key); // Assume search is implemented
        if (x == null || x.isSentinel()) {
            // Handle key not found or trying to delete a sentinel.
            return;
        }

        Node<T> y = x.getParent();
        Node<T> z;
        // Handle deletion at the leaf level.
        if (x == y.getLeft()) {
            y.setLeft(y.getMiddle().isSentinel() ? y.getRight() : y.getMiddle());
        } else if (x == y.getMiddle()) {
            y.setMiddle(y.getRight());
        }
        y.setRight((Node<T>) Node.SENTINEL); // Remove the far right child.

        // Now we deal with the potential underflow at y.
        while (!y.isSentinel()) {
            if (y.getMiddle().isSentinel()) { // y is underfull
                z = borrowOrMerge23(y);
                if (z == null) { // If y was root and is now merged, we need to update root.
                    this.root = y.getLeft();
                    this.root.setParent((Node<T>) Node.SENTINEL);
                    break;
                }
                y = z.getParent(); // Move up the tree
            } else {
                updateKey23(y);
                updateSize(y);
                y = y.getParent();
            }
        }
    }

    //version 2
    /*
    public void delete23(Node<T> x) {
        Node<T> y = x.getParent();
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

        // Update the size of the ancestors of y
        Node<T> current = y;
        while (current != null) {
            current.setSize(current.getSize() - 1);
            current = current.getParent();
        }

        while (y != null) {
            if (y.getMiddle() == null) {
                if (y != root) {
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
            fastestRunner = findFastestRunner(root);
        }
    }
     */
    //version 1
    /*
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
     */

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

    //version 2
    public int compareNodeWithKey(Node<T> node, T key) {
        // Check for sentinel nodes first.
        if (node.isMinNode()) {
            // Sentinel is considered greater than any real key.
            return -1;
        } else if (node.isMaxNode()) {
            // Sentinel is considered greater than any real key.
            return 1;
        }

        // If neither node is a sentinel, extract keys and proceed with comparison.
        T r1 = node.getKey();
        T r2 = key;

        // Existing comparison logic for non-sentinel nodes.
        return compareNodes(r1, r2);
    }
    public int compareNodeWithNode(Node<T> n1, Node<T> n2) {
        // Check for sentinel nodes first.
        if (n1.isMinNode() && n2.isMaxNode()) {
            return -1;
        } else if (n1.isMaxNode() && n2.isMinNode()) {
            return 1;
        } else if (n1.isSentinel() && n2.isSentinel()) {
            // Two sentinels are considered equal.
            return 0;
        }

        // If neither node is a sentinel, extract keys and proceed with comparison.
        T r1 = n1.getKey();
        T r2 = n2.getKey();

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
