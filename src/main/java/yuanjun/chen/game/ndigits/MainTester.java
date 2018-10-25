/**
 * @Title: MainTester.java
 * @Package: yuanjun.chen.game.ndigits
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年10月25日 下午3:01:28
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.game.ndigits;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName: MainTester
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author: 陈元俊
 * @date: 2018年10月25日 下午3:01:28
 */
public class MainTester {
    private List<Integer> rawList;
    private List<Integer> modelList;

    /** n*n, 且小数位于左上. */
    private int[][] MODEL_GRIDS;
    /** n*n. */
    private int[][] grids;
    private int N;

    public void generateWithNsizeRawList(List<Integer> rawList) {
        this.N = (int) Math.sqrt(rawList.size()); // 请输入完全平方长度的list，0代表空格
        this.grids = new int[N][N];
        this.MODEL_GRIDS = new int[N][N];
        this.rawList = new ArrayList<>(rawList);
        this.modelList = new ArrayList<>();
        for (int i = 1; i <= N * N - 1; i++) { // 0 - n^2-1
            this.modelList.add(i);
        }
        this.modelList.add(0);
        fillInGrids(N, this.rawList, this.MODEL_GRIDS);
        fillInGrids(N, this.modelList, this.grids);
        System.out.println("SOLVABLE?" + solvable());
        System.out.println("INITIAL MAP IS SHOWN AS BELOW");
        dispGrids(this.grids);
        System.out.println("MODEL MAP IS SHOWN AS BELOW");
        dispGrids(this.MODEL_GRIDS);

    }

    public void generateRandomGame(int n) {
        this.N = n;
        this.grids = new int[N][N];
        this.MODEL_GRIDS = new int[N][N];
        this.rawList = new ArrayList<>();
        this.modelList = new ArrayList<>();
        for (int i = 1; i <= N * N - 1; i++) { // 0 - n^2-1
            this.rawList.add(i);
            this.modelList.add(i);
        }
        this.rawList.add(0);
        this.modelList.add(0);
        fillInGrids(N, this.modelList, this.MODEL_GRIDS);
        do {
            System.out.println("TRY GEN LEGALLY & RANDOMLY---");
            Collections.shuffle(this.rawList); // 打乱
        } while (!solvable());

        fillInGrids(N, this.rawList, this.grids);
        System.out.println("INITIAL MAP IS SHOWN AS BELOW");
        dispGrids(this.grids);
        System.out.println("MODEL MAP IS SHOWN AS BELOW");
        dispGrids(this.MODEL_GRIDS);
    }

    private static void fillInGrids(int N, List<Integer> rawList, int[][] grids) {
        int index = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grids[i][j] = rawList.get(index++);
            }
        }
    }

    private static void dispGrids(int[][] grids) {
        for (int i = 0; i < grids.length; i++) {
            System.out.printf("|");
            for (int x : grids[i]) {
                if (x != 0) {
                    System.out.printf(" %3d |", x);
                } else {
                    System.out.printf(" %3s |", "");
                }
            }
            System.out.printf("\n");
        }
    }

    private boolean solvable() {
        int delta = findZeroLineIndex(MODEL_GRIDS) - findZeroLineIndex(grids);
        int nReverseOrderModel = findReversePairs(modelList);
        int nReverseOrderThis = findReversePairs(rawList);
        // System.out.printf("DELTA %d, modelRv %d, thisRv %d\n", delta, nReverseOrderModel, nReverseOrderThis);
        return (Math.abs(nReverseOrderModel - nReverseOrderThis + delta) & 1) == 0;
    }

    private int findReversePairs(List<Integer> list) {
        int revs = 0;
        for (int i = 1; i < N * N; i++) {
            int j = i - 1;
            int k = i - 2;
            int cur = list.get(i);
            if (cur != 0 && j >= 0 && list.get(j) > cur) { // 情况1，前置有值，且value不为0
                // System.out.printf("PREV %d, cur %d\n", list.get(j), cur);
                revs++;
            } else if (cur != 0 && k >= 0 && list.get(k) > cur) { // 情况2，前前置有值，且value不为0
                // System.out.printf("PREV %d, cur %d\n", list.get(k), cur);
                revs++;
            }
            // 其他情况不看
        }
        return revs;
    }

    private int findZeroLineIndex(int[][] grids) {
        for (int i = 0; i < grids.length; i++) {
            for (int x : grids[i]) {
                if (x == 0) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public void solve() {
        
    }

    public static void main(String[] args) {
        MainTester main = new MainTester();
        main.generateRandomGame(12);
        System.out.println();

        MainTester main2 = new MainTester();
        List<Integer> rawList = new ArrayList<>();
        rawList.add(1);
        rawList.add(2);
        rawList.add(3);
        rawList.add(4);
        rawList.add(5);
        rawList.add(6);
        rawList.add(7);
        rawList.add(8);
        rawList.add(9);
        rawList.add(10);
        rawList.add(11);
        rawList.add(12);
        rawList.add(13);
        rawList.add(15);
        rawList.add(14);
        rawList.add(0);
        main2.generateWithNsizeRawList(rawList);
    }
}
