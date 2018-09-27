/**
 * @Title: PrintNeatlyAlgo.java
 * @Package: yuanjun.chen.base.dynamicprogramming
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年9月13日 下午2:22:42
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.dynamicprogramming;

/**
 * 考虑在一个打印机上整齐地打印一段文章的问题。输入的正文是n个长度分别为L1、L2、……、Ln（以字符个数度量）的单词构成的序列。 我们希望将这个段落在一些行上整齐地打印出来，每行至多M个字符。
 * “整齐度”的标准如下：如果某一行包含从i到j的单词（i<j）， 且单词之间只留一个空格，则在行末多余的空格字符个数为 * M - (j-i) - (Li+ …… + * Lj)，
 * 它必须是非负值才能让该行容纳这些单词。我们希望所有行（除最后一行）的行末多余空格字符个数的立方和最小。
 * 请给出一个动态规划的算法，来在打印机整齐地打印一段有n个单词的文章。分析所给算法的执行时间和空间需求。
 * 
 * @ClassName: PrintNeatlyAlgo
 * @Description: print-neatly算法（CLRS-15章课后习题）
 * @author: 陈元俊
 * @date: 2018年9月13日 下午2:22:42
 */
public class PrintNeatlyAlgo {
    private static String SPLITTER;
    private static String[] words;
    private static int M;
    private static int[][] cost;
    private static int[][] solutions;
    private static int length;

    public static void init(String splitter, int srcM, String[] srcWords) {
        SPLITTER = splitter;
        M = srcM;
        length = srcWords.length;
        words = new String[length];
        System.arraycopy(srcWords, 0, words, 0, length);
        cost = new int[length][length];
        solutions = new int[length][length];
    }

    /** M为单行长度限制， words为输入的单词列表 返回最小代价. */
    public static int neatly() {
        for (int i = 0; i < length; i++) {
            cost[i][i] = cube(leftSpace(i, i));
        }
        for (int step = 1; step < length; step++) {
            for (int i = 0; i < length - step; i++) {
                int j = i + step;
                int left = leftSpace(i, j);
                if (left < 0) { // 1行吃不消i-j，拆分
                    int minCost = Integer.MAX_VALUE;
                    int rec = 0;
                    for (int k = i; k < j; k++) {
                        int candi = cost[i][k] + cost[k + 1][j];
                        if (candi < minCost) {
                            minCost = candi;
                            rec = k;
                        }
                    }
                    // System.out.println("step= " + step + " i= " + i + " j= " + j + " the best=" + rec);
                    cost[i][j] = minCost;
                    solutions[i][j] = rec;
                } else { // 正好在一行，哦了
                    // System.out.println("step= " + step + " i= " + i + " j= " + j + " the best= showhand");
                    cost[i][j] = (j == length - 1) ? 0 : cube(left); // 最后一行不计入
                    solutions[i][j] = Integer.MAX_VALUE; // showhand
                }
            }
        }
        return cost[0][length - 1];
    }

    public static void printTheChapter() {
        printResult(0, words.length - 1);
    }

    public static void printResult(int i, int j) {
        if (i == j) {
            System.out.println(words[i]);
            return;
        }
        int best = solutions[i][j];
        if (best == i) {
            System.out.println(words[i]);
            printResult(i + 1, j);
        } else if (best == j) {
            System.out.println(words[i]);
            printResult(i, j - 1);
            System.out.println(words[j]);
        } else if (Integer.MAX_VALUE == best) {
            StringBuilder sb = new StringBuilder();
            for (int k = i; k <= j; k++) {
                sb.append(words[k]).append(SPLITTER);
            }
            System.out.println(sb.toString().trim());
        } else {
            printResult(i, best);
            printResult(best + 1, j);
        }
    }

    private static int leftSpace(int i, int j) {
        int lenIJ = 0;
        for (int idx = i; idx <= j; idx++) {
            lenIJ += words[idx].length();
        }
        return M - (j - i) - lenIJ;
    }

    private static int cube(int i) {
        return i * i * i;
    }

    public static void main(String[] args) {
        String[] nations = new String[] {"china", "philipines", "india", "laos", "mongolia", "thailand"};
        init(" ", 10, nations);
        int res = neatly();
        System.out.println("MinQ = " + res);
        System.out.println("---The chapter is show below---");
        printTheChapter();
    }
}
