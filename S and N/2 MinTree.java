import javafx.scene.Node;
public class MinTree {
    private  MinRunsNode<RunnerID> root;

    public void initMinTree() {
        this.root = new MinRunsNode<RunnerID>(null,null,null,0,null);
        MinRunsNode<RunnerID> left = new MinRunsNode<RunnerID>(root,null,null,0,null);
        MinRunsNode<RunnerID> middle = new  MinRunsNode<RunnerID>(root,null,null,0,null);
        root.setLeft(left);
        root.setMiddle(middle);
    }
    public void addMinRunning(float runningTime,RunnerID id) {

        MinRunsNode<RunnerID> newMinRunning =new MinRunsNode<RunnerID>(null,null,null,runningTime,id);
        insert_2_3(this.root, newMinRunning);
    }
    public void removeRunning(float time,RunnerID id){
        MinRunsNode<RunnerID> runningToRemove=search_2_3(this.root,time,id);
        if(runningToRemove!=null){//If the time does not exist in the 2_3_tree we cannot remove it.
            delete_23(this.root,runningToRemove);
        }
    }

    public void update_key(MinRunsNode<RunnerID> x) {
        x.setTime((x.getLeft().getTime()));
        if (x.getMiddle() != null) {
            x.setTime(x.getMiddle().getTime());
        }
        if (x.getRight() != null) {
            x.setTime(x.getRight().getTime());
        }
    }
    public  MinRunsNode<RunnerID> getRoot(){
        return  this.root;
    }
    public void setRoot(MinRunsNode<RunnerID> root) {
        this.root = root;
    }

    public void set_children(MinRunsNode<RunnerID> x, MinRunsNode<RunnerID> l, MinRunsNode<RunnerID> m, MinRunsNode<RunnerID> r) {
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
    public MinRunsNode<RunnerID> insert_and_split_2_3(MinRunsNode<RunnerID> x, MinRunsNode<RunnerID> z) {
        MinRunsNode<RunnerID> l = x.getLeft();
        MinRunsNode<RunnerID> m = x.getMiddle();
        MinRunsNode<RunnerID> r = x.getRight();
        if (r == null) {
            if (z.getTime()<(l.getTime())||(z.getTime()==(l.getTime())&&z.getId().isSmaller(l.getId()))) {
                set_children(x, z, l, m);
            } else if (z.getTime()<(m.getTime())||(z.getTime()==(m.getTime())&&z.getId().isSmaller(m.getId()))) {
                set_children(x, l, z, m);
            } else {
                set_children(x, l, m, z);
            }
            return null;
        }
        MinRunsNode<RunnerID> y = new MinRunsNode<RunnerID>(null, null, null,0,null);
        if (z.getTime()<(l.getTime())||(z.getTime()==(l.getTime())&&z.getId().isSmaller(l.getId()))) {
            set_children(x, z, l, null);
            set_children(y, m, r, null);
        } else if (z.getTime()<(m.getTime())||(z.getTime()==(m.getTime())&&z.getId().isSmaller(m.getId()))) {
            set_children(x, l, z, null);
            set_children(y, m, r, null);
        } else if (z.getTime()<(r.getTime())||(z.getTime()==(r.getTime())&&z.getId().isSmaller(r.getId()))) {
            set_children(x, l, m, null);
            set_children(y, z, r, null);
        } else {
            set_children(x, l, m, null);
            set_children(y, r, z, null);
        }
        return y;
    }
    public void insert_2_3(MinRunsNode<RunnerID> t, MinRunsNode<RunnerID> z) {
        MinRunsNode<RunnerID> y = t;//need to check if it's ok
        while (!y.isLeaf()) {
            if (z.getTime()<(y.getLeft().getTime())||(z.getTime()==(y.getLeft().getTime())&&z.getId().isSmaller(y.getLeft().getId()))) {
                y = y.getLeft();
            } else if (z.getTime()<(y.getMiddle().getTime())||(z.getTime()==(y.getMiddle().getTime())&&z.getId().isSmaller(y.getMiddle().getId()))) {
                y = y.getMiddle();
            } else {
                y = y.getRight();
            }
        }
        MinRunsNode<RunnerID> x = y.getParent();
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
            MinRunsNode<RunnerID> w = new MinRunsNode<RunnerID>(null, null, null, 0,null);
            set_children(w, x, z, null);
            setRoot(w);//we need to check if it really changed the root of our race. probably we can write this.root=w.
        }
    }

    public  MinRunsNode<RunnerID> borrowOrMerge23( MinRunsNode<RunnerID> y) {
        MinRunsNode<RunnerID> z = y.getParent();
        if (y.equals(z.getLeft())) {
            MinRunsNode<RunnerID> x = z.getMiddle();
            if (x.getRight() != null) {
                set_children(x, y.getLeft(), x.getLeft(), null);
                set_children(x, x.getMiddle(), x.getRight(), null);

            } else {
                set_children(x, y.getLeft(), x.getLeft(), x.getMiddle());
                y = null;//We have to check what it means delete y, is it change y to null or should I discconect y from everything?
                set_children(z, x, z.getRight(), null);
            }
            return z;
        }
        if (y.equals(z.getMiddle())) {
            MinRunsNode<RunnerID> x = z.getLeft();
            if (x.getRight() != null) {
                set_children(y, x.getRight(), y.getLeft(), null);
                set_children(x, x.getLeft(), x.getMiddle(), null);
            } else {
                set_children(x, x.getLeft(), x.getMiddle(), y.getLeft());
                y = null;//We have to check what it means delete y, is it change y to null or should I discconect y from everything?
                set_children(z, x, z.getRight(), null);
            }
            return z;

        }
        MinRunsNode<RunnerID> x = z.getMiddle();
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
    public void delete_23( MinRunsNode<RunnerID> root,  MinRunsNode<RunnerID> x) {
        MinRunsNode<RunnerID> y = x.getParent();
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
    public  MinRunsNode<RunnerID> search_2_3( MinRunsNode<RunnerID> x, float runningTime,RunnerID id) {
        if (x.isLeaf()) {
            if (x.getTime()==runningTime&&x.getId().equals(id)) {
                return x;
            } else {
                return null;
            }
        }
        if (runningTime<(x.getLeft().getTime()) || (runningTime==(x.getLeft().getTime()))&&x.getLeft().getId().isSmaller(id)) {//checking if the running time is equal or smaller than left.key
            return search_2_3(x.getLeft(), runningTime,id);
        } else if (runningTime<(x.getMiddle().getTime()) || (runningTime==(x.getMiddle().getTime()))&&x.getMiddle().getId().isSmaller(id))//checking if the running time is equal or smaller than middle.key
        {
            return search_2_3(x.getMiddle(), runningTime,id);
        } else {
            return search_2_3(x.getRight(), runningTime,id);
        }
    }

    public  MinRunsNode<RunnerID> minimum_23() {
        MinRunsNode<RunnerID> x = root;
        while (!x.isLeaf()) {
            x = x.getLeft();
        }
        x = x.getParent().getMiddle();
        if (x.getTime() != Float.MAX_VALUE) {
            return x;
        } else {
            return null;//in the pseducode it sending err: T is empty - we need to check about it
        }
    }


    public  MinRunsNode<RunnerID> successor_2_3( MinRunsNode<RunnerID> x) {
        MinRunsNode<RunnerID> z = x.getParent();
        while (x.equals(z.getRight()) || (z.getRight() == null && x.equals(z.getMiddle()))) {
            x = z;
            z = z.getParent();
        }
        MinRunsNode<RunnerID> y;
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
    public int Rank(MinRunsNode<RunnerID> runner) {
        int rank = 1;
        MinRunsNode<RunnerID> y = runner.getParent();
        while (y != null) {
            if (runner.equals(y.getMiddle())) {
                rank = rank + y.getLeft().getSize();
            }
            else if (runner.equals(y.getRight())) {
                rank = rank + y.getLeft().getSize() + y.getMiddle().getSize();
            }
            runner = y;
            y = y.getParent();
        }
        return rank;
    }













    }


