package yuanjun.chen.base.disjoint;

import java.util.Arrays;

// disjoint set
public class CLRS_Chapter21 {
    private int[] parent; // parent
    private int[] rank; // rank

    public void makeSet(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public int findSet(int x) {
        if (parent[x] != x) {
            parent[x] = findSet(parent[x]);
        }
        return parent[x];
    }

    private void link(int x, int y) {
        if (x == y) return;
        if (rank[x] > rank[y]) {
            parent[y] = x;
        } else {
            parent[x] = y;
            if (rank[x] == rank[y]) {
                rank[y] += 1;
            }
        }
    }

    public void union(int x, int y) {
        link(findSet(x), findSet(y));
    }

    public void printout() {
        System.out.println(Arrays.toString(parent));
        System.out.println(Arrays.toString(rank));
    }

    public static void main(String[] args) {
        CLRS_Chapter21 cc = new CLRS_Chapter21();
        cc.makeSet(10);

        cc.union(1, 3); // bd
        cc.union(4, 6); // eg
        cc.union(0, 2); // ac
        cc.union(7, 8); // hi
        cc.union(0, 1); // ab
        cc.union(4, 5); // ef
        cc.union(1, 2); // bc

        cc.printout();
    }
}

