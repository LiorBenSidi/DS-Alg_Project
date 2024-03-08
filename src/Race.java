public class Race {
    private node<RunnerID> root;
    public void setRoot(node<RunnerID> root) {
        this.root = root;
    }
    public void init()
    {
        root = new node<RunnerID>(null, null, null, null);
        node<RunnerID> left = new node<RunnerID>(root, null, null, null);
        node<RunnerID> middle = new node<RunnerID>(root, null, null, null);

        // Set the left and middle nodes for the current node
        root.setLeft(left);
        root.setMiddle(middle);
        throw new java.lang.UnsupportedOperationException("not implemented");
    }
    public void addRunner(RunnerID id)
    {
        node<RunnerID> y = root;
        while (y.getLeft() != null) {
            if (id.isSmaller(y.getLeft().getId())) {
                y = y.getLeft();
            } else if (id.isSmaller(y.getMiddle().getId())) {
                y = y.getMiddle();
            } else {
                y = y.getRight();
            }
        }

        node<RunnerID> x = y.getParent();

        node<RunnerID> z = new node<RunnerID>(null, null, null, null);
        z = insertAndSplit(x, z);

        while (!(x.equals(root))) {
            x = x.getParent();
            if (z != null) {
                z = insertAndSplit(x, z);
            } else {
                updateKey(x);
            }
        }

        if (z != null) {
            node<RunnerID> w = new node<RunnerID>(null, null, null, null);
            setChildren(w, x, z, null);
            setRoot(w);
        }

        throw new java.lang.UnsupportedOperationException("not implemented");
    }

    public void removeRunner(RunnerID id)
    {
        node<RunnerID> x = search(root, id);
        node<RunnerID> y = x.getParent();
        if (x.equals(y.getLeft())) {
            setChildren(y, y.getMiddle(), y.getRight(), null);
        } else if (x.equals(y.getMiddle())) {
            setChildren(y, y.getLeft(), y. getRight(), null);
        } else {
            setChildren(y, y.getLeft(), y.getMiddle(), null);
        }
        /*delete x* ?*/
        while (y != null) {
            if (y.getMiddle() == null) {
                if (y != root) {
                    y = borrowOrMerge(y);
                } else {
                    setRoot(y.getLeft());
                    /*delete x* ?*/
                    return;
                }
            } else {
                updateKey(y);
                y = y.getParent();
            }
        }
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

    public void updateKey(node<RunnerID> x)
    {
        x.setId(x.getLeft().getId());

        if (x.getMiddle() != null) {
            x.setId(x.getMiddle().getId());
        }
        if (x.getRight() != null) {
            x.setId(x.getRight().getId());
        }
        throw new java.lang.UnsupportedOperationException("not implemented");
    }

    public void setChildren(node<RunnerID> x, node<RunnerID> l,
                            node<RunnerID> m, node<RunnerID> r)
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
        updateKey(x);
    }

    public node<RunnerID> insertAndSplit(node<RunnerID> x, node<RunnerID> z)
    {
        node<RunnerID> l = x.getLeft();
        node<RunnerID> m = x.getMiddle();
        node<RunnerID> r = x.getRight();

        if (r == null) {
            if (z.getId().isSmaller(l.getId())) {
                setChildren(x, z, l, m);
            } else if (z.getLeft().getId().isSmaller(m.getId())) {
                setChildren(x, l, z, m);
            } else {
                setChildren(x, l, m, z);
            }
            return null;
        }
        node<RunnerID> y = new node<RunnerID>(null, null, null, null);
        if (z.getId().isSmaller(l.getId())) {
            setChildren(x, z, l, null);
            setChildren(y , m, r, null);
        } else if (z.getId().isSmaller(m.getId())) {
            setChildren(x, l, z, null);
            setChildren(y, m, r, null);
        } else if (z.getId().isSmaller(r.getId())) {
            setChildren(x, l, m, null);
            setChildren(y, z, r, null);
        } else {
            setChildren(x, l, m, null);
            setChildren(y, r, z, null);
        }

        return y;
    }

    public node<RunnerID> search(node<RunnerID> x ,RunnerID k) {
        if (x.getLeft() != null) {
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
            return search(x.getLeft(), k);
        } else if ((((!(k.isSmaller(x.getMiddle().getId()))) &&
                (!(x.getMiddle().getId().isSmaller(k)))))
                ||
                k.isSmaller(x.getMiddle().getId())) {
            return search(x.getMiddle(), k);
        } else {
            return search(x.getRight(), k); //TODO: we need to check that the
            // algorithm is still valid without the sentinels
        }
    }

    public node<RunnerID> borrowOrMerge(node<RunnerID> y) {
        node<RunnerID> z = y.getParent();
        if (y.equals(z.getLeft())) {
            node<RunnerID> x = z.getMiddle();
            if (x.getRight() != null) {
                setChildren(y, y.getLeft(), x.getLeft(), null);
                setChildren(x, x.getMiddle(), x.getRight(), null);
            } else {
                setChildren(x, y.getLeft(), x.getLeft(), x.getMiddle());
                /* delete y? */
                setChildren(z, x, z.getRight(), null);
            }
            return z;
        }
        if (y.equals(z.getMiddle())) {
            node<RunnerID> x = z.getLeft();
            if (x.getRight() != null) {
                setChildren(y, x.getRight(), y.getLeft(), null);
                setChildren(x, x.getLeft(), x.getMiddle(), null);
            } else {
                setChildren(x, x.getLeft(), x.getMiddle(), y.getLeft());
                /* delete y? */
                setChildren(z, x, z.getRight(), null);
            }
            return z;
        }
        node<RunnerID> x = z.getMiddle();
        if (x.getRight() != null) {
            setChildren(y, x.getRight(), y.getLeft(), null);
            setChildren(x, x.getLeft(), x.getMiddle(), null);
        } else {
            setChildren(x, x.getLeft(), x.getMiddle(), y.getLeft());
            /* delete y? */
            setChildren(z, z.getLeft(), x, null);
        }
        return z;
    }

    public int Rank() {

        return 0;
    }
}
