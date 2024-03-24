import javafx.scene.Node;

public class Runnings_2_3_Tree {

    private RunningNode root;


    public void initRunningTree() {
        this.root = new RunningNode(null, null, null, 0);
        RunningNode left = new RunningNode(root, null, null, 0);
        RunningNode middle = new RunningNode(root, null, null, 0);
        root.setLeft(left);
        root.setMiddle(middle);
        throw new java.lang.UnsupportedOperationException("not implemented");
    }
    public void addRunning(float runningTime) {

        RunningNode newRunning =new RunningNode(null,null,null,runningTime);
        insert_2_3(this.root, newRunning);
        throw new java.lang.UnsupportedOperationException("not implemented");
    }
    public void removeRunning(float time){
        RunningNode runningToRemove=search_2_3(this.root,time);
        if(runningToRemove!=null){//If the time does not exist in the 2_3_tree we cannot remove it.
            delete_23(this.root,runningToRemove);
        }
    }

    public void update_key(RunningNode x) {
        x.setTime((x.getLeft().getTime()));
        if (x.getMiddle() != null) {
            x.setTime(x.getMiddle().getTime());
        }
        if (x.getRight() != null) {
            x.setTime(x.getRight().getTime());
        }
    }
    public  RunningNode getRoot(){
        return  this.root;
    }
    public void setRoot(RunningNode root) {
        this.root = root;
    }

    public void set_children(RunningNode x, RunningNode l, RunningNode m, RunningNode r) {
        x.setLeft(l);
        x.setMiddle(m);
        x.setRight(r);
        l.setParent(x);
        if (m != null) {
            m.setParent(x);
        }
        if (r != null) {
            r.setParent(x);
        }
        update_key(x);
    }
    public RunningNode insert_and_split_2_3(RunningNode x, RunningNode z) {
        RunningNode l = x.getLeft();
        RunningNode m = x.getMiddle();
        RunningNode r = x.getRight();
        if (r == null) {
            if (z.getTime()<(l.getTime())) {
                set_children(x, z, l, m);
            } else if (z.getTime()<(m.getTime())) {
                set_children(x, l, z, m);
            } else {
                set_children(x, l, m, z);
            }
            return null;
        }
        RunningNode y = new RunningNode(null, null, null, 0);
        if (z.getTime()<(l.getTime())) {
            set_children(x, z, l, null);
            set_children(y, m, r, null);
        } else if (z.getTime()<(m.getTime())) {
            set_children(x, l, z, null);
            set_children(y, m, r, null);
        } else if (z.getTime()<(r.getTime())) {
            set_children(x, l, m, null);
            set_children(y, z, r, null);
        } else {
            set_children(x, l, m, null);
            set_children(y, r, z, null);
        }
        return y;
    }
    public void insert_2_3(RunningNode t, RunningNode z) {
        RunningNode y = t;//need to check if it's ok
        while (!y.isLeaf()) {
            if (z.getTime()<(y.getLeft().getTime())) {
                y = y.getLeft();
            } else if (z.getTime()<(y.getMiddle().getTime())) {
                y = y.getMiddle();
            } else {
                y = y.getRight();
            }
        }
        RunningNode x = y.getParent();
        z = insert_and_split_2_3(x, z);
        while (!x.equals(t)) {
            x = x.getParent();
            if (z != null) {
                z = insert_and_split_2_3(x, z);
            } else {
                update_key(x);
            }

        }
        if (z != null) {
            RunningNode w = new RunningNode(null, null, null, 0);
            set_children(w, x, z, null);
            setRoot(w);//we need to check if it really changed the root of our race. probably we can write this.root=w.
        }
    }

    public RunningNode borrowOrMerge23(RunningNode y) {
        RunningNode z = y.getParent();
        if (y.equals(z.getLeft())) {
            RunningNode x = z.getMiddle();
            if (x.getRight() != null) {
                set_children(x, y.getLeft(), x.getLeft(), null);
                set_children(x, x.getMiddle(), x.getRight(), null);

            } else {
                set_children(x, y.getLeft(), x.getLeft(), x.getMiddle());
                y = null;//We hate to check what it means delete y, is it change y to null or should I discconect y from everything?
                set_children(z, x, z.getRight(), null);
            }
            return z;
        }
        if (y.equals(z.getMiddle())) {
            RunningNode x = z.getLeft();
            if (x.getRight() != null) {
                set_children(y, x.getRight(), y.getLeft(), null);
                set_children(x, x.getLeft(), x.getMiddle(), null);
            } else {
                set_children(x, x.getLeft(), x.getMiddle(), y.getLeft());
                y = null;//We hate to check what it means delete y, is it change y to null or should I discconect y from everything?
                set_children(z, x, z.getRight(), null);
            }
            return z;

        }
        RunningNode x = z.getMiddle();
        if (x.getRight() != null) {
            set_children(y, x.getRight(), y.getLeft(), null);
            set_children(x, x.getLeft(), x.getMiddle(), null);
        } else {
            set_children(x, x.getLeft(), x.getMiddle(), y.getLeft());
            y = null;//We hate to check what it means delete y, is it change y to null or should I discconect y from everything?
            set_children(z, z.getLeft(), x, null);
        }
        return z;

    }
    public void delete_23(RunningNode root, RunningNode x) {
        RunningNode y = x.getParent();
        if (x.equals(y.getLeft())) {
            set_children(y, y.getMiddle(), y.getRight(), null);
        } else if (x.equals(y.getMiddle())) {
            set_children(y, y.getLeft(), y.getRight(), null);
        } else {
            set_children(y, y.getLeft(), y.getMiddle(), null);
        }
        x=null;//We hate to check what it means delete x, is it change x to null or should I discconect y from everything?
        while (y != null) {
            if (y.getMiddle() != null) {
                update_key(y);
                y=y.getParent();
            }
            else {
                if (!(y.equals(root))){
                    y=borrowOrMerge23(y);

                }
                else {
                    root=y.getLeft();
                    y.getLeft().setParent(null);
                    y=null;//We hate to check what it means delete y, is it change y to null or should I discconect y from everything?
                    return;
                }
            }
        }
    }
    public RunningNode search_2_3(RunningNode x, float runningTime) {
        if (x.isLeaf()) {
            if (x.getTime()==runningTime) {
                return x;
            } else {
                return null;
            }
        }
        if (runningTime<(x.getLeft().getTime()) || runningTime==(x.getLeft().getTime())) {//checking if the running time is equal or smaller than left.key
            return search_2_3(x.getLeft(), runningTime);
        } else if (runningTime<(x.getMiddle().getTime()) || runningTime==(x.getMiddle().getTime()))//checking if the running time is equal or smaller than middle.key
        {
            return search_2_3(x.getMiddle(), runningTime);
        } else {
            return search_2_3(x.getRight(), runningTime);
        }
    }

    public RunningNode minimum_23() {
        RunningNode x = root;
        while (!x.isLeaf()) {
            x = x.getLeft();
        }
        x = x.getParent().getMiddle();
        if (x.getTime() != Float.MAX_VALUE) {
            return x;
        } else {
            return null;//in the pseducode it sending err: T is empty- we need to check about it
        }
    }


    public RunningNode successor_2_3(RunningNode x) {
        RunningNode z = x.getParent();
        while (x.equals(z.getRight()) || (z.getRight() == null && x.equals(z.getMiddle()))) {
            x = z;
            z = z.getParent();
        }
        RunningNode y;
        if (x.equals(z.getLeft())) {
            y = z.getMiddle();
        } else {
            y = z.getRight();
        }
        while (!y.isLeaf()) {
            y = y.getLeft();
        }
        if (y.getTime() < Float.MAX_VALUE) { //sentinels in the pseudocode
            return y;
        } else {
            return null;
        }
    }


}
