public class Race {
    private Runner<RunnerID> root;
    public void setRoot(Runner<RunnerID> root) {
        this.root = root;
    }
    public void init()
    {
        init23();
        throw new java.lang.UnsupportedOperationException("not implemented");
    }
    public void addRunner(RunnerID id)
    {
        Runner<RunnerID> z = new Runner<RunnerID>(null, null, null, id, true, false);
        insert23(root, z);

        throw new java.lang.UnsupportedOperationException("not implemented");
    }

    public void removeRunner(RunnerID id)
    {
        Runner<RunnerID> x = search23(root, id);
        delete23(root, x);
        throw new java.lang.UnsupportedOperationException("not implemented");
    }

    public void addRunToRunner(RunnerID id, float time)
    {
        throw new java.lang.UnsupportedOperationException("not implemented");
    }

    public void removeRunFromRunner(RunnerID id, float time)
    {
        throw new java.lang.UnsupportedOperationException("not implemented");
    }

    public RunnerID getFastestRunnerAvg()
    {

        throw new java.lang.UnsupportedOperationException("not implemented");
    }

    public RunnerID getFastestRunnerMin()
    {

        throw new java.lang.UnsupportedOperationException("not implemented");
    }

    public float getMinRun(RunnerID id)
    {

        throw new java.lang.UnsupportedOperationException("not implemented");
    }
    public float getAvgRun(RunnerID id){
        throw new java.lang.UnsupportedOperationException("not implemented");
    }

    public int getRankAvg(RunnerID id)
    {
        throw new java.lang.UnsupportedOperationException("not implemented");
    }

    public int getRankMin(RunnerID id)
    {
        throw new java.lang.UnsupportedOperationException("not implemented");
    }

    /** 2_3 tree functions from the lecture: **/
    public void init23()
    {
        root = new Runner<RunnerID>(null, null, null, null, false, true);
        Runner<RunnerID> left = new Runner<RunnerID>(root, null, null, null, true, true);
        Runner<RunnerID> middle = new Runner<RunnerID>(root, null, null, null, true, true);

        // Set the left and middle nodes for the current node
        root.setLeft(left);
        root.setMiddle(middle);
    }

    public Runner<RunnerID> search23(Runner<RunnerID> x , RunnerID k) {
        if (x.isLeaf()) {
            if ((!(x.getId().isSmaller(k))) && (!(k.isSmaller(x.getId()))))  {
                return x;
            } else {
                return null;
            }
        }
        if ((((!(k.isSmaller(x.getLeft().getId()))) &&
                (!(x.getLeft().getId().isSmaller(k)))))
                ||
                k.isSmaller(x.getLeft().getId())) {
            return search23(x.getLeft(), k);
        } else if ((((!(k.isSmaller(x.getMiddle().getId()))) &&
                (!(x.getMiddle().getId().isSmaller(k)))))
                ||
                k.isSmaller(x.getMiddle().getId())) {
            return search23(x.getMiddle(), k);
        } else {
            return search23(x.getRight(), k); //TODO: we need to check that the
            // algorithm is still valid without the sentinels
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
        x.setId(x.getLeft().getId());

        if (x.getMiddle() != null) {
            x.setId(x.getMiddle().getId());
        }
        if (x.getRight() != null) {
            x.setId(x.getRight().getId());
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
            if (z.getId().isSmaller(l.getId())) {
                setChildren23(x, z, l, m);
            } else if (z.getLeft().getId().isSmaller(m.getId())) {
                setChildren23(x, l, z, m);
            } else {
                setChildren23(x, l, m, z);
            }
            return null;
        }
        Runner<RunnerID> y = new Runner<RunnerID>(null, null, null, null, true, false);
        if (z.getId().isSmaller(l.getId())) {
            setChildren23(x, z, l, null);
            setChildren23(y , m, r, null);
        } else if (z.getId().isSmaller(m.getId())) {
            setChildren23(x, l, z, null);
            setChildren23(y, m, r, null);
        } else if (z.getId().isSmaller(r.getId())) {
            setChildren23(x, l, m, null);
            setChildren23(y, z, r, null);
        } else {
            setChildren23(x, l, m, null);
            setChildren23(y, r, z, null);
        }

        return y;
    }

    public void insert23(Runner<RunnerID> root, Runner<RunnerID> z)
    {
        Runner<RunnerID> y = root;
        while (y.getLeft() != null) {
            if (z.getId().isSmaller(y.getLeft().getId())) {
                y = y.getLeft();
            } else if (z.getId().isSmaller(y.getMiddle().getId())) {
                y = y.getMiddle();
            } else {
                y = y.getRight();
            }
        }

        Runner<RunnerID> x = y.getParent();

        z = insertAndSplit23(x, z);

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
            setRoot(w);
        }
    }

    public Runner<RunnerID> borrowOrMerge23(Runner<RunnerID> y) {
        Runner<RunnerID> z = y.getParent();
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
                /* delete y? */
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
                    setRoot(y.getLeft());
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
    public int Rank() {

        return 0;
    }
}
