public class Race {
    private Runner<RunnerID> rootMin;
    private Runner<RunnerID> rootAvg;

    public Runner<RunnerID> getRootMin() {
        return rootMin;
    }

    public void setRootMin(Runner<RunnerID> root) {
        // TODO: check if this is the right way to do it
        this.rootMin = rootMin;
    }
    public void init()
    {
        init23();
        throw new java.lang.UnsupportedOperationException("not implemented");
    }
    public void addRunner(RunnerID id)
    {
        Runner<RunnerID> z = new Runner<RunnerID>(null, null, null, id, true, false);
        insert23(rootMin, z);
        insert23(rootAvg, z);


        throw new java.lang.UnsupportedOperationException("not implemented");
    }

    public void removeRunner(RunnerID id)
    {
        Runner<RunnerID> y = search23(rootMin, id);
        delete23(rootMin, y);

        Runner<RunnerID> x = search23(rootAvg, id);
        delete23(rootAvg, x);

        throw new java.lang.UnsupportedOperationException("not implemented");
    }

    public void addRunToRunner(RunnerID id, float time)
    {
        Runner<RunnerID> y = search23(rootMin, id);
        if (y.getMinRun() > time) {
            y.setMinRun(time);
        }
        heapInsert(y, time);

        Runner<RunnerID> x = search23(rootAvg, id);
        heapInsert(x, time);
        x.setMinRun((x.getMinRun() * (x.getHeapSize() - 1) + time) / x.getHeapSize());
    }

    public void removeRunFromRunner(RunnerID id, float time)
    {
        Runner<RunnerID> y = search23(rootMin, id);
        if (y.getMinRun() == time) {
            heapExtractMin(y);
            y.setMinRun(y.getRuns().get(1).getTime());
        } else {
            heapDecreaseKey(y, y.getRuns().indexOf(time), Float.MIN_VALUE);
            heapExtractMin(y);
            heapify(y, y.getRuns().indexOf(time));
        }

        Runner<RunnerID> x = search23(rootAvg, id);
        heapDecreaseKey(x, x.getRuns().indexOf(time), Float.MIN_VALUE);
        heapExtractMin(x);
        heapify(x, x.getRuns().indexOf(time));
        x.setMinRun((x.getMinRun() * (x.getHeapSize() + 1) - time) / x.getHeapSize());
    }

    public RunnerID getFastestRunnerAvg()
    {
        // TODO: WE NEED TO STORE THE FASTEST RUNNER AVG IN THE TREE IN THE ROOT
        return rootAvg.getId();
    }

    public RunnerID getFastestRunnerMin()
    {
        // TODO: WE NEED TO STORE THE FASTEST RUNNER IN THE TREE IN THE ROOT
        return rootMin.getId();
    }

    public float getMinRun(RunnerID id)
    {
        return search23(rootMin, id).getRuns().get(1).getTime();
    }
    public float getAvgRun(RunnerID id){
        return search23(rootAvg, id).getMinRun();
    }

    public int getRankAvg(RunnerID id)
    {
        Rank(search23(rootAvg, id));
        throw new java.lang.UnsupportedOperationException("not implemented");
    }

    public int getRankMin(RunnerID id)
    {
        Rank(search23(rootMin, id));
        throw new java.lang.UnsupportedOperationException("not implemented");
    }

    /** 2_3 tree functions from the lecture: **/
    public void init23()
    {
        rootMin = new Runner<RunnerID>(null, null, null, null, false, true);
        rootAvg = new Runner<RunnerID>(null, null, null, null, false, true);
        Runner<RunnerID> left = new Runner<RunnerID>(rootMin, null, null, null, true, true);
        Runner<RunnerID> middle = new Runner<RunnerID>(rootMin, null, null, null, true, true);

        // Set the left and middle nodes for the current node
        rootMin.setLeft(left);
        rootAvg.setLeft(left);
        rootMin.setMiddle(middle);
        rootAvg.setMiddle(middle);
    }

    public Runner<RunnerID> search23(Runner<RunnerID> x , RunnerID k) {
        if (x.isLeaf()) {
            if ((!(x.getId().isSmaller(k))) && (!(k.isSmaller(x.getId()))))  {
                return x;
            } else {
                return null;
            }
        }
        if (((((!(k.isSmaller(x.getLeft().getId()))) &&
                (!(x.getLeft().getId().isSmaller(k)))))
                ||
                k.isSmaller(x.getLeft().getId()))
                &&
                (!x.isSentinels())) {
            return search23(x.getLeft(), k);
        } else if (((((!(k.isSmaller(x.getMiddle().getId()))) &&
                (!(x.getMiddle().getId().isSmaller(k)))))
                ||
                k.isSmaller(x.getMiddle().getId()))
                &&
                (!x.isSentinels())) {
            return search23(x.getMiddle(), k);
        } else {
            return search23(x.getRight(), k);
        }
    }

    public Runner<RunnerID> minimum23(Runner<RunnerID> root) {
        Runner<RunnerID> x = root;
        while (!x.isLeaf()) {
            x = x.getLeft();
        }
        x = x.getParent().getMiddle();
        if (x != null) { /* instead of the sentinels */
            return x;
        } else {
            return null; /* instead of "error: T is empty" */
        }
    }

    public Runner<RunnerID> successor23(Runner<RunnerID> x) {
        Runner<RunnerID> z = x.getParent();
        while ((x.equals(z.getRight()))
                ||
                ((z.getRight() == null) && (x.equals(z.getMiddle())))) {
            x = z;
            z = z.getParent();
        }
        Runner<RunnerID> y;
        if (x.equals(z.getLeft())) {
            y = z.getMiddle();
        } else {
            y = z.getRight();
        }
        while (y.getLeft() != null) {
            y = y.getLeft();
        }
        if (y.getId() != null) { /* instead of the sentinels */
            return y;
        } else {
            return null;
        }
    }

    public void updateKey23(Runner<RunnerID> x)
    {
        x.setMinRun(x.getLeft().getMinRun());

        if (x.getMiddle() != null) {
            x.setMinRun(x.getMiddle().getMinRun());
        }
        if (x.getRight() != null) {
            x.setMinRun(x.getRight().getMinRun());
        }
    }

    public void setChildren23(Runner<RunnerID> x, Runner<RunnerID> l,
                              Runner<RunnerID> m, Runner<RunnerID> r)
    {
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
        updateKey23(x);
    }

    public Runner<RunnerID> insertAndSplit23(Runner<RunnerID> x, Runner<RunnerID> z)
    {
        Runner<RunnerID> l = x.getLeft();
        Runner<RunnerID> m = x.getMiddle();
        Runner<RunnerID> r = x.getRight();

        if (r == null) {
            if (z.getMinRun() < (l.getMinRun())) {
                setChildren23(x, z, l, m);
            } else if (z.getLeft().getMinRun() < (m.getMinRun())) {
                setChildren23(x, l, z, m);
            } else {
                setChildren23(x, l, m, z);
            }
            return null;
        }
        Runner<RunnerID> y = new Runner<RunnerID>(null, null, null, null, true, false);
        if (z.getMinRun() < (l.getMinRun())) {
            setChildren23(x, z, l, null);
            setChildren23(y , m, r, null);
        } else if (z.getMinRun() < (m.getMinRun())) {
            setChildren23(x, l, z, null);
            setChildren23(y, m, r, null);
        } else if (z.getMinRun() < (r.getMinRun())) {
            setChildren23(x, l, m, null);
            setChildren23(y, z, r, null);
        } else {
            setChildren23(x, l, m, null);
            setChildren23(y, r, z, null);
        }

        return y;
    }

    public void insert23(Runner<RunnerID> root, Runner<RunnerID> z) {
        // TODO: check if this is the right way to do it
        Runner<RunnerID> y = root;
        while (y.getLeft() != null) {
            if (z.getMinRun() < (y.getLeft().getMinRun())) {
                y = y.getLeft();
            } else if (z.getMinRun() < (y.getMiddle().getMinRun())) {
                y = y.getMiddle();
            } else {
                y = y.getRight();
            }
        }

        Runner<RunnerID> x = y.getParent();

        z = insertAndSplit23(x, z);

        // TODO: check if this is the right way to do it
        while (!(x.equals(root))) {
            x = x.getParent();
            if (z != null) {
                z = insertAndSplit23(x, z);
            } else {
                updateKey23(x);
            }
        }

        if (z != null) {
            Runner<RunnerID> w = new Runner<RunnerID>(null, null,
                                null, null, false, false);
            setChildren23(w, x, z, null);
            setRootMin(w);
        }
    }

    public Runner<RunnerID> borrowOrMerge23(Runner<RunnerID> y) {
        Runner<RunnerID> z = y.getParent();

        // TODO: check if this is the right way to do it
        if (y.equals(z.getLeft())) {
            Runner<RunnerID> x = z.getMiddle();
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
            Runner<RunnerID> x = z.getLeft();
            if (x.getRight() != null) {
                setChildren23(y, x.getRight(), y.getLeft(), null);
                setChildren23(x, x.getLeft(), x.getMiddle(), null);
            } else {
                setChildren23(x, x.getLeft(), x.getMiddle(), y.getLeft());

                setChildren23(z, x, z.getRight(), null);
            }
            return z;
        }
        Runner<RunnerID> x = z.getMiddle();
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

    public void delete23(Runner<RunnerID> root, Runner<RunnerID> x) {
        Runner<RunnerID> y = x.getParent();
        if (x.equals(y.getLeft())) {
            setChildren23(y, y.getMiddle(), y.getRight(), null);
        } else if (x.equals(y.getMiddle())) {
            setChildren23(y, y.getLeft(), y.getRight(), null);
        } else {
            setChildren23(y, y.getLeft(), y.getMiddle(), null);
        }
        /*delete x* ?*/
        while (y != null) {
            if (y.getMiddle() == null) {
                if (y != root) {
                    y = borrowOrMerge23(y);
                } else {
                    setRootMin(y.getLeft());
                    /*delete x* ?*/
                    return;
                }
            } else {
                updateKey23(y);
                y = y.getParent();
            }
        }
    }

    /** 2_3 tree functions from the tutorial: **/

    public void heapInsert(Runner<RunnerID> runner, float time) {
        int s = runner.getHeapSize() + 1;
        runner.getRuns().set(s, new Run(runner.getId(),time));
        runner.getRuns().get(s).setRunnerID(null); //TODO: check if this is the right way to do it
        runner.setHeapSize(s);
        heapDecreaseKey(runner, s, time);
    }

    public void heapDecreaseKey(Runner<RunnerID> runner, int i, float time) {
        if (time > runner.getRuns().get(i).getTime()) {
            System.out.println("new key is larger than current key");
        }
        runner.getRuns().get(i).setTime(time);
        while ((i > 1) && (runner.getRuns().get(i/2).getTime() > runner.getRuns().get(i).getTime())) {
            Run temp = runner.getRuns().get(i);
            runner.getRuns().set(i, runner.getRuns().get(i/2));
            runner.getRuns().set(i/2, temp);
            i = i/2;
        }
    }

    public float heapExtractMin(Runner<RunnerID> runner) {
        if (runner.getHeapSize() < 1) {
            System.out.println("heap underflow");
        }
        float min = runner.getRuns().get(1).getTime();
        runner.getRuns().set(1, runner.getRuns().get(runner.getHeapSize()));
        runner.setHeapSize(runner.getHeapSize() - 1);
        heapify(runner, 1);
        return min;
    }

    public void heapify(Runner<RunnerID> runner, int i) {
        int l = 2*i;
        int r = 2*i + 1;
        int smallest;
        if ((l <= runner.getHeapSize()) && (runner.getRuns().get(l).getTime() < runner.getRuns().get(i).getTime())) {
            smallest = l;
        } else {
            smallest = i;
        }
        if ((r <= runner.getHeapSize()) && (runner.getRuns().get(r).getTime() < runner.getRuns().get(smallest).getTime())) {
            smallest = r;
        }
        if (smallest != i) {
            Run temp = runner.getRuns().get(i);
            runner.getRuns().set(i, runner.getRuns().get(smallest));
            runner.getRuns().set(smallest, temp);
            heapify(runner, smallest);
        }
    }

    public int Rank(Runner<RunnerID> runner) {
        int rank = 1;
        Runner<RunnerID> y = runner.getParent();
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

