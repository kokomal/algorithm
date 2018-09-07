package yuanjun.chen.base.dynamicprogramming;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import yuanjun.chen.base.common.RandomGenner;

/** CLRS-3原版LCS算法 这里新开辟一个类，避免和其他算法公用相同的静态变量导致冲突. */
public class LCSAlgo {
    /**   
     * @Fields SPLITTER : TODO(用一句话描述这个变量表示什么)   
     */
    public static final String SPLITTER = " │ ";
    /** 辅助方向矩阵. */
    static char[][] b;
    /** LCS的长度. */
    static int[][] c;

    /**
     * 原版的LCS-LENGTH算法，带辅助方向矩阵b
     * i/j  0  1  2  3  4  5  6
     * _____y  B  D  C  D  B  A
     * 0 x  0  0  0  0  0  0  0
     * 1 A  0
     * 2 B  0
     * 3 C  0
     * 4 B  0
     * 5 D  0
     * 6 A  0
     * 7 B  0.
     */
    public static void lcs_legth(String X, String Y) {
        int m = X.length();
        int n = Y.length();
        // let b[1...m, 1...n] and c[0...m, 0...n] be new tables
        b = new char[m + 1][n + 1]; // 0为rubbish
        c = new int[m + 1][n + 1]; // 0---m, 其中1---m为数据
        // c[0][j]和c[i][0]都要是0，但鉴于java new的性质，这一步不用了
        for (int i = 1; i <= m; i++) { // 从1开始，到m结束，期间涵盖0，也就是[0,m]
            for (int j = 1; j <= n; j++) {
                if (X.charAt(i-1) == Y.charAt(j-1)) { // common
                    c[i][j] = c[i - 1][j - 1] + 1;
                    b[i][j] = '↖';
                } else if (c[i - 1][j] > c[i][j - 1]) {
                    c[i][j] = c[i - 1][j];
                    b[i][j] = '↑';
                } else if (c[i - 1][j] < c[i][j - 1]){
                    c[i][j] = c[i][j - 1];
                    b[i][j] = '←';
                } else {
                    c[i][j] = c[i][j - 1]; // 代价一致
                    b[i][j] = '×';
                }
            }
        }
    }

    /**
     * 原版的PRINT-LCS算法
     * 入参的取值范围为m,n，即b的维度.
     * @SpecialThanksTo 薛丁文给出打印所有LCS的递归方法！
     */
    public static void print_lcs(String X, int i, int j) {
        if (i == 0 || j == 0) {
            return;
        }
        if (b[i][j] == '↖') {
            print_lcs(X, i - 1, j - 1);
            System.out.print(X.charAt(i - 1));
        } else if (b[i][j] == '↑') {
            print_lcs(X, i - 1, j);
        } else if (b[i][j] == '←'){
            print_lcs(X, i, j - 1);
        } else { // x
            System.out.print('{');
            print_lcs(X, i - 1, j);
            System.out.print('+');
            print_lcs(X, i, j - 1);
            System.out.print('}');
        }
    }
    
    @Deprecated
    public static List<String> print_lcs_list(String X, int i, int j) {
        if (i == 0 || j == 0) {
            List<String> ll = new LinkedList<>();
            ll.add(new String());
            return ll;
        }
        if (b[i][j] == '↖') {
            List<String> old = print_lcs_list(X, i - 1, j - 1);
            // System.out.println("old - " + old);
            List<String> newStrs = new LinkedList<>();
            for (String each : old) {
                newStrs.add(each + X.charAt(i - 1));
            }
            // System.out.println("new - " + newStrs);
            return newStrs;
        } else if (b[i][j] == '↑') {
            return print_lcs_list(X, i - 1, j);
        } else if (b[i][j] == '←'){
            return print_lcs_list(X, i, j - 1);
        } else { // x
            List<String> old1 = print_lcs_list(X, i - 1, j);
            List<String> old2 = print_lcs_list(X, i, j - 1);
            for (String x : old2) {
                old1.add(x);
            }
            return old1;
        }
    }
    
    public static List<String> print_lcs_list_unique(String X, int i, int j) {
        List<String> res;
        if (i == 0 || j == 0) {
            List<String> ll = new LinkedList<>();
            ll.add(new String());
            return ll;
        }
        if (b[i][j] == '↖') {
            List<String> old = print_lcs_list_unique(X, i - 1, j - 1);
            List<String> newStrs = new LinkedList<>();
            for (String each : old) {
                newStrs.add(each + X.charAt(i - 1));
            }
            res = newStrs;
        } else if (b[i][j] == '↑') {
            return print_lcs_list_unique(X, i - 1, j);
        } else if (b[i][j] == '←'){
            return print_lcs_list_unique(X, i, j - 1);
        } else { // x
            List<String> old = print_lcs_list_unique(X, i - 1, j);
            old.addAll(print_lcs_list_unique(X, i, j - 1));
            res = old;
        }
        Set<String> fitres = new HashSet<>(res); // 去重之后
        return new LinkedList<>(fitres);
    }

    public static void main(String[] args) {
        String X = RandomGenner.generateDNASeries(10);
        String Y = RandomGenner.generateDNASeries(10);
        
        X = "CGGCCAAACTGTCGCTACGACCATGCTGCA";
        Y = "CCGTGACAGTCACACTATCAGCTGACAGCGAGGGGTAATC";
        System.out.println("X = " + X);
        System.out.println("Y = " + Y);
        lcs_legth(X, Y);
        showBTable(X, Y);
        List<String> allres = print_lcs_list_unique(X, X.length(), Y.length());
        System.out.println(allres.size());
        for (String xx : allres) {
            System.out.println("---" + xx);
        }
        
        X = "character";
        Y =  new StringBuffer(X).reverse().toString();
        lcs_legth(X, Y);
        List<String> allres2 = print_lcs_list_unique(X, X.length(), Y.length());
        System.out.println(allres2);
    }

    public static void showBTable(String X, String Y) {
        System.out.print("X/Y│ ");
        for (int j = 1; j <= Y.length(); j++) {
            System.out.print(Y.charAt(j-1) + "  ");
        }
        System.out.println("\n───┼─────────────────");
        for (int i = 1; i <= X.length(); i++) {
            System.out.print(" " + X.charAt(i-1) + SPLITTER);
            for (int j = 1; j <= Y.length(); j++) {
                System.out.print(b[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println("------------------------");
    }
}
