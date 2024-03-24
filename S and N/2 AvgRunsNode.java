public class AvgRunsNode<K> {
        private AvgRunsNode<K> parent;
        private AvgRunsNode<K> left;
        private AvgRunsNode<K> middle;
        private AvgRunsNode<K> right;
        private K id;
        private float time;

        public  AvgRunsNode(AvgRunsNode<K> parent, AvgRunsNode<K> left, AvgRunsNode<K> middle, float time, K id) {
            this.left = left;
            this.middle = middle;
            this.parent = parent;
            this.time = time;
            this.right = null;
            this.id=id;
        }
        public  AvgRunsNode(AvgRunsNode<K> parent, AvgRunsNode<K> left, AvgRunsNode<K> middle, AvgRunsNode<K> right, float time, K id) {
            this(parent,left,middle,time,id);
            this.right=right;
        }
        public AvgRunsNode<K> getParent() {
            return this.parent;
        }

        public void setParent(AvgRunsNode<K> parent) {
            this.parent = parent;
        }

        public AvgRunsNode<K> getMiddle() {
            return this.middle;
        }

        public void setMiddle(AvgRunsNode<K> middle) {
            this.middle = middle;
        }

        public AvgRunsNode<K> getLeft() {
            return left;
        }

        public void setLeft(AvgRunsNode<K> left) {
            this.left = left;
        }

        public AvgRunsNode<K> getRight() {
            return right;
        }

        public void setRight(AvgRunsNode<K> r) {
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

