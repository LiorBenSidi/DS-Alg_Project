public class RaceFormer {
    private RunnersNode<RunnerID> root;
    private  RunnersNode<RunnerID> fastestMinRunner;
    private RunnersNode<RunnerID> fastestAvgRunner;
    private MinTree minTree;

    public void init() {
        this.root = new RunnersNode<RunnerID>(null, null, null, null);
        RunnersNode<RunnerID> left = new RunnersNode<RunnerID>(root, null, null, null);
        RunnersNode<RunnerID> middle = new RunnersNode<RunnerID>(root, null, null, null);
        root.setLeft(left);
        root.setMiddle(middle);
        fastestMinRunner =null;
        fastestAvgRunner=null;
        this.minTree.initMinTree();
        throw new java.lang.UnsupportedOperationException("not implemented");
    }
    public void addRunner(RunnerID id) {

        RunnersNode<RunnerID> newRunner=new RunnersNode<RunnerID>(null,null,null,id);
        insert_2_3(this.root,newRunner);
        throw new java.lang.UnsupportedOperationException("not implemented");
    }

    public void removeRunner(RunnerID id) {
        RunnersNode<RunnerID> runner = search_2_3(root, id);
        if (runner != null) {
            runner.setPlayerRunningsTree(null);// we need to check if we did it correctly
            delete_23(this.root, runner);
            minTree.delete_23(minTree,
            if (fastestMinRunner.equals(runner)) {
                fastestMinRunner = minTree.minimum_23();
            }
            if (fastestAvgRunner.getId().equals(id)) {
                set
            }
        }
    }

    public void addRunToRunner(RunnerID id, float time) {
        RunnersNode<RunnerID> runner =search_2_3(root,id);
        if(runner!=null&&runner.getPlayerRunningsTree()!=null){//when the runner already has a running tree
            Runnings_2_3_Tree runRoot=runner.getPlayerRunningsTree();
            runRoot.addRunning(time);
            runner.setSum(runner.getSum()+time);
            runner.setNumOfRunnings(runner.getNumOfRunnings()+1);
            if(time<runner.getMinRun()){//not nesecery if we have min2_3tree?
                runner.setMinRun(time);
                MinRunsNode<RunnerID> runnerToAdd=minTree.search_2_3(minTree.getRoot(),time,id);
                if(runnerToAdd==null){
                    minTree.addMinRunning(time,id);
                }
                else {
                    minTree.delete_23(minTree.getRoot(),runnerToAdd);
                    minTree.addMinRunning(time,id);
                }


            }
            if(this.fastestMinRunner ==null){//in case that this is the first running in the race
                fastestMinRunner =runner;
            }
            else {
                if(runner.getMinRun()==this.fastestMinRunner.getMinRun()){
                    if(runner.getId().isSmaller(this.fastestMinRunner.getId())){
                        fastestMinRunner =runner;
                    }
                }
                if(runner.getMinRun()<this.fastestMinRunner.getMinRun()){
                    // we need to check SHOVER SHIVYON _DONE
                    fastestMinRunner =runner;
                }

            }
            if(this.fastestAvgRunner ==null){//in case that this is the first running in the race
                fastestAvgRunner =runner;
            }
            else {
                if(runner.getAvgRun()==this.fastestAvgRunner.getAvgRun()){
                    if(runner.getId().isSmaller(this.fastestAvgRunner.getId())){
                        fastestAvgRunner =runner;
                    }
                }
                if(runner.getAvgRun()<this.fastestAvgRunner.getAvgRun()){
                    // we need to check SHOVER SHIVYON_Done

                    fastestAvgRunner =runner;
                }
            }
        }
        else if((runner!=null&&runner.getPlayerRunningsTree()==null)){


            Runnings_2_3_Tree newRunRoot=new Runnings_2_3_Tree();
            newRunRoot.initRunningTree();
            newRunRoot.addRunning(time);
            runner.setMinRun(time);// not nesecery if we have min2_3tree
            runner.setSum(time);
            runner.setNumOfRunnings(1);
            MinRunsNode<RunnerID> runnerToAdd=minTree.search_2_3(minTree.getRoot(),time,id);
            if(runnerToAdd==null){
                minTree.addMinRunning(time,id);
            }
            else {
                minTree.delete_23(minTree.getRoot(),runnerToAdd);//הסרה של הזמן הישן והכנסה מחודשת
                minTree.addMinRunning(time,id);
            }




            if(this.fastestMinRunner ==null){//in case that this is the first running in the race
                fastestMinRunner =runner;
            }
            else {

                if(runner.getMinRun()==this.fastestMinRunner.getMinRun()){
                    if(runner.getId().isSmaller(this.fastestMinRunner.getId())){
                        fastestMinRunner =runner;
                    }
                }
                if(runner.getMinRun()<this.fastestMinRunner.getMinRun()){
                    // we need to check SHOVER SHIVYON_DONE

                    fastestMinRunner =runner;
                }
            }
            if(this.fastestAvgRunner ==null){//in case that this is the first running in the race
                fastestAvgRunner =runner;
            }
            else {
                if(runner.getAvgRun()==this.fastestAvgRunner.getAvgRun()){
                    if(runner.getId().isSmaller(this.fastestAvgRunner.getId())){
                        fastestAvgRunner =runner;
                    }
                }
                if(runner.getAvgRun()<this.fastestAvgRunner.getAvgRun()){
                    // we need to check SHOVER SHIVYON

                    fastestAvgRunner =runner;
                }
            }

            //we have to increase the min.
        }//do we need to check the case when the search doest find the runner with this id?
    }

    public void removeRunFromRunner(RunnerID id, float time) {
        RunnersNode<RunnerID> runner =search_2_3(root,id);
        if (runner!=null) {
            Runnings_2_3_Tree runRoot = runner.getPlayerRunningsTree();
            if (runRoot != null) {
                runRoot.removeRunning(time);
                runner.setSum(runner.getSum()-time);
                runner.setNumOfRunnings(runner.getNumOfRunnings()-1);
                if (time==runner.getMinRun()){
                    runner.setMinRun(runRoot.minimum_23().getTime());
                    MinRunsNode<RunnerID> runnerToRemove=minTree.search_2_3(minTree.getRoot(),time,id);
                    minTree.delete_23(minTree.getRoot(),runnerToRemove);
                    minTree.addMinRunning(runner.getMinRun(),id);
                }

                if(this.fastestMinRunner.equals(runner)){
                    fastestAvgRunner=
                }

                if(this.fastestAvgRunner.equals(runner)){
                   set
                }

                }


                //we have to decrease the min.
            }

        }



    public RunnerID getFastestRunnerAvg() {
        return this.fastestAvgRunner.getId();
        //throw new java.lang.UnsupportedOperationException("not implemented");
    }

    public RunnerID getFastestRunnerMin() {
        return fastestMinRunner.getId();
    }

    public float getMinRun(RunnerID id) {
        RunnersNode<RunnerID> runner =search_2_3(root,id);
        return runner.getMinRun();
        // throw new java.lang.UnsupportedOperationException("not implemented");
    }

    public float getAvgRun(RunnerID id) {
        RunnersNode<RunnerID> runner =search_2_3(root,id);
        return (runner.getAvgRun());
    }

    public int getRankAvg(RunnerID id) {

        throw new java.lang.UnsupportedOperationException("not implemented");
    }

    public int getRankMin(RunnerID id) {
        throw new java.lang.UnsupportedOperationException("not implemented");
    }


    public RunnersNode<RunnerID> search_2_3(RunnersNode<RunnerID> x, RunnerID k) {
        if (x.isLeaf()) {
            if (x.getId().equals(k)) {
                return x;
            } else {
                return null;
            }
        }
        if (k.isSmaller(x.getLeft().getId()) || k.equals(x.getLeft().getId())) {//checking if k is equal or smaller than left.key
            return search_2_3(x.getLeft(), k);
        } else if (k.isSmaller(x.getMiddle().getId()) || k.equals(x.getMiddle().getId()))//checking if k is equal or smaller than middle.key
        {
            return search_2_3(x.getMiddle(), k);
        } else {
            return search_2_3(x.getRight(), k);
        }
    }


    public RunnersNode<RunnerID> minimum_23() {
        RunnersNode<RunnerID> x = root;
        while (!x.isLeaf()) {
            x = x.getLeft();
        }
        x = x.getParent().getMiddle();
        if (x.getId() != null) {
            return x;
        } else {
            return null;//in the pseducode it sending err: T is empty- we need to check about it
        }
    }


    public RunnersNode<RunnerID> successor_2_3(RunnersNode<RunnerID> x) {
        RunnersNode<RunnerID> z = x.getParent();
        while (x.equals(z.getRight()) || (z.getRight() == null && x.equals(z.getMiddle()))) {
            x = z;
            z = z.getParent();
        }
        RunnersNode<RunnerID> y;
        if (x.equals(z.getLeft())) {
            y = z.getMiddle();
        } else {
            y = z.getRight();
        }
        while (!y.isLeaf()) {
            y = y.getLeft();
        }
        if (y.getId() != null) { //sentinels in the pseudocode
            return y;
        } else {
            return null;
        }
    }

    public void update_key(RunnersNode<RunnerID> x) {
        x.setId((x.getLeft().getId()));
        if (x.getMiddle() != null) {
            x.setId(x.getMiddle().getId());
        }
        if (x.getRight() != null) {
            x.setId(x.getRight().getId());
        }
    }

    public void set_children(RunnersNode<RunnerID> x, RunnersNode<RunnerID> l, RunnersNode<RunnerID> m, RunnersNode<RunnerID> r) {
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

    public RunnersNode<RunnerID> insert_and_split_2_3(RunnersNode<RunnerID> x, RunnersNode<RunnerID> z) {
        RunnersNode<RunnerID> l = x.getLeft();
        RunnersNode<RunnerID> m = x.getMiddle();
        RunnersNode<RunnerID> r = x.getRight();
        if (r == null) {
            if (z.getId().isSmaller(l.getId())) {
                set_children(x, z, l, m);
            } else if (z.getId().isSmaller(m.getId())) {
                set_children(x, l, z, m);
            } else {
                set_children(x, l, m, z);
            }
            return null;
        }
        RunnersNode<RunnerID> y = new RunnersNode<RunnerID>(null, null, null, null);
        if (z.getId().isSmaller(l.getId())) {
            set_children(x, z, l, null);
            set_children(y, m, r, null);
        } else if (z.getId().isSmaller(m.getId())) {
            set_children(x, l, z, null);
            set_children(y, m, r, null);
        } else if (z.getId().isSmaller(r.getId())) {
            set_children(x, l, m, null);
            set_children(y, z, r, null);
        } else {
            set_children(x, l, m, null);
            set_children(y, r, z, null);
        }
        return y;
    }

    public void insert_2_3(RunnersNode<RunnerID> t, RunnersNode<RunnerID> z) {
        RunnersNode<RunnerID> y = t;//need to check if it's ok
        while (!y.isLeaf()) {
            if (z.getId().isSmaller(y.getLeft().getId())) {
                y = y.getLeft();
            } else if (z.getId().isSmaller(y.getMiddle().getId())) {
                y = y.getMiddle();
            } else {
                y = y.getRight();
            }
        }
        RunnersNode<RunnerID> x = y.getParent();
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
            RunnersNode<RunnerID> w = new RunnersNode<RunnerID>(null, null, null, null);
            set_children(w, x, z, null);
            setRoot(w);//we need to check if it really changed the root of our race. probably we can write this.root=w.
        }
    }

    public void setRoot(RunnersNode<RunnerID> root) {
        this.root = root;
    }

    public RunnersNode<RunnerID> borrowOrMerge23(RunnersNode<RunnerID> y) {
        RunnersNode<RunnerID> z=y.getParent();
        if(y.equals(z.getLeft())){
            RunnersNode <RunnerID> x=z.getMiddle();
            if(x.getRight()!=null){
                set_children(x,y.getLeft(),x.getLeft(),null);
                set_children(x,x.getMiddle(),x.getRight(),null);

            }
            else {
                set_children(x,y.getLeft(),x.getLeft(),x.getMiddle());
                y=null;//We hate to check what it means delete y, is it change y to null or should I discconect y from everything?
                set_children(z,x,z.getRight(),null);
            }
            return z;
        }
        if (y.equals(z.getMiddle())) {
            RunnersNode<RunnerID> x = z.getLeft();
            if (x.getRight() != null) {
                set_children(y, x.getRight(), y.getLeft(), null);
                set_children(x, x.getLeft(), x.getMiddle(), null);
            } else {
                set_children(x, x.getLeft(), x.getMiddle(), y.getLeft());
                y=null;//We hate to check what it means delete y, is it change y to null or should I discconect y from everything?
                set_children(z, x, z.getRight(), null);
            }
            return z;

        }
        RunnersNode<RunnerID> x = z.getMiddle();
        if (x.getRight() != null) {
            set_children(y, x.getRight(), y.getLeft(), null);
            set_children(x, x.getLeft(), x.getMiddle(), null);
        } else {
            set_children(x, x.getLeft(), x.getMiddle(), y.getLeft());
            y=null;//We hate to check what it means delete y, is it change y to null or should I discconect y from everything?
            set_children(z, z.getLeft(), x, null);
        }
        return z;

    }

    public void delete_23(RunnersNode<RunnerID> root, RunnersNode<RunnerID> x) {
        RunnersNode<RunnerID> y = x.getParent();
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





}