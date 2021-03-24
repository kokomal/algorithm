/**
 * @Title: OptimalBSTAlgo.java
 * @Package: yuanjun.chen.base.dynamicprogramming
 * @Description: 最优二叉搜索树
 * @author: 陈元俊
 * @date: 2018年9月5日 上午9:59:14
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.dynamicprogramming;

import java.util.Arrays;
import yuanjun.chen.base.container.TreeNode;

/**
 * @ClassName: OptimalBSTAlgo
 * @Description: 最优二叉搜索树
 * @author: 陈元俊
 * @date: 2018年9月5日 上午9:59:14
 */
public class OptimalBSTAlgo {
    protected static int n;

    protected static String[] keys;

    /** 命中概率，1...n */
    protected static Double[] p;
    /** 伪命中概率，0...n */
    protected static Double[] q;

    /** 期望值，(n+1)*(n+1),取值范围{1...n+1, 0...n} */
    protected static Double[][] e;
    /** 权值，(n+1)*(n+1),取值范围{1...n+1, 0...n} */
    protected static Double[][] w;

    /** 结果，n*n,取值范围{1...n, 1...n} */
    protected static Integer[][] root;

    public static void init(String[] srcKeys, Double[] srcP, Double[] srcQ) {
        n = srcP.length;
        p = new Double[n];
        System.arraycopy(srcP, 0, p, 0, n);
        q = new Double[n + 1];
        System.arraycopy(srcQ, 0, q, 0, n + 1);

        keys = new String[n];
        System.arraycopy(srcKeys, 0, keys, 0, n);

        e = new Double[n + 1][n + 1];
        w = new Double[n + 1][n + 1];
        root = new Integer[n][n];
    }

    /** O[n^3]的DP算法计算最优二叉搜索树. */
    public static void optimalBST() {
        for (int i = 1; i <= n + 1; i++) {
            setE(i, i - 1, q(i - 1)); // e(i,i-1)=qi-1
            setW(i, i - 1, q(i - 1)); // w(i,i-1)=qi-1
        }
        for (int l = 1; l <= n; l++) {
            for (int i = 1; i <= n - l + 1; i++) {
                int j = i + l - 1;
                setE(i, j, Double.MAX_VALUE);
                setW(i, j, w(i, j - 1) + p(j) + q(j));
                for (int r = i; r <= j; r++) {
                    Double t = e(i, r - 1) + e(r + 1, j) + w(i, j);
                    if (t < e(i, j)) {
                        setE(i, j, t);
                        setRoot(i, j, r);
                    }
                }
            }
        }
    }

    /** 前序遍历. */
    public static void printTree(TreeNode<String> tr) {
        if (tr == null) {
            return;
        }
        System.out.println(tr.getVal());
        printTree(tr.getLeft());
        printTree(tr.getRight());
    }

    public static void buildWrap() {
        System.out.println("===Display root first===");
        for (Integer[] integers : root) {
            System.out.println(Arrays.toString(integers));
        }
        System.out.println("===Display tree next===");
        TreeNode<String> tr = buildTree(1, n);
        printTree(tr);
    }

    public static TreeNode<String> buildTree(int left, int right) {
        TreeNode<String> t = new TreeNode<>();
        Integer core = root(left, right);
        String val = "Key[" + core + "]{" + keys[core - 1] + "}";
        t.setVal(val);
        if (right == left) { // 孤悬，那么子女均为伪节点，n-1和n
            TreeNode<String> tleft = new TreeNode<>();
            tleft.setVal("D-" + (right - 1) + "-" + q(right - 1));
            TreeNode<String> tright = new TreeNode<>();
            tright.setVal("D-" + right + "-" + q(right));
            t.setLeft(tleft);
            t.setRight(tright);
            return t;
        }
        if (core == left) { // 左端选中，则左子女为伪节点n-1
            TreeNode<String> tleft = new TreeNode<>();
            tleft.setVal("D-" + (core - 1) + "-" + q(core - 1));
            TreeNode<String> tright = buildTree(core + 1, right);
            t.setLeft(tleft);
            t.setRight(tright);
        } else if (core == right) { // 右端选中，则右子女为伪节点n
            TreeNode<String> tright = new TreeNode<>();
            tright.setVal("D-" + core + "-" + q(core));
            TreeNode<String> tleft = buildTree(left, core - 1);
            t.setLeft(tleft);
            t.setRight(tright);
        } else { // 中间选中，则皆大欢喜
            TreeNode<String> tleft = buildTree(left, core - 1);
            TreeNode<String> tright = buildTree(core + 1, right);
            t.setLeft(tleft);
            t.setRight(tright);
        }
        return t;
    }

    public static void main(String[] args) {
        // String[] keys = new String[] {"NedStark", "JamieLannister", "Danaeris", "Imp", "JoraMormont"};
        // Double[] srcP = new Double[] {0.15, 0.10, 0.05, 0.10, 0.20};
        // Double[] srcQ = new Double[] {0.03, 0.12, 0.04, 0.06, 0.05, 0.10};
        // init(keys, srcP, srcQ);
        // optimalBST();
        // buildWrap();
    }

    /** 支持root[1..n][1..n]的索引 */
    private static Integer root(int x, int y) {
        return root[x - 1][y - 1];
    }

    /** 支持e[1..n+1][0..n]的索引 */
    private static Double e(int x, int y) {
        return e[x - 1][y];
    }

    /** 支持w[1..n+1][0..n]的索引 */
    private static Double w(int x, int y) {
        return w[x - 1][y];
    }

    private static Double p(int x) {
        return p[x - 1];
    }

    private static Double q(int x) {
        return q[x];
    }

    private static void setE(int x, int y, Double val) {
        e[x - 1][y] = val;
    }

    private static void setW(int x, int y, Double val) {
        w[x - 1][y] = val;
    }

    private static void setRoot(int x, int y, Integer val) {
        root[x - 1][y - 1] = val;
    }
}
