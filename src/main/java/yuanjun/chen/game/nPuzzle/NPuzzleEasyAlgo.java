/**
 * @Title: MainTester.java
 * @Package: yuanjun.chen.game.ndigits
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年10月25日 下午3:01:28
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.game.nPuzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName: NPuzzleEasyAlgo
 * @Description: IDA*解决nDigits问题 采用精简的内部数据结构应对高阶的时空复杂度
 * @author: 陈元俊
 * @date: 2018年10月25日 下午3:01:28
 */
public class NPuzzleEasyAlgo {
    private List<Integer> rawList;
    private List<Integer> modelList;
    private static long time1 = 0L;
    private int N;
    /** 全局的寻找flag，找到即可终止所有分支. */
    private boolean g_found;
    /** 初始化的bound，最好有个边界. */
    private int bound;

    private MoveDir[] solutions;

    private int[] arrGrids; // 采用1维数组简化数组操作,N*N
    private int[] arrMODEL_GRIDS; // N*N
    private int[] arrOrig_grids; // 原始坐标，用于展示

    private int[] rtMap; // 映射表，索引是面值，存面值在grids里面的序号

    public void generateWithNsizeRawList(List<Integer> rawList) {
        this.g_found = false;
        this.N = (int) Math.sqrt(rawList.size()); // 请输入完全平方长度的list，0代表空格
        int len = rawList.size();
        this.arrGrids = new int[len];
        this.arrMODEL_GRIDS = new int[len];
        this.arrOrig_grids = new int[len];
        this.rtMap = new int[len];

        this.rawList = new ArrayList<>(rawList);
        this.modelList = new ArrayList<>();
        for (int i = 1; i <= N * N - 1; i++) { // 0 - n^2-1
            this.modelList.add(i);
        }
        this.modelList.add(0);

        fillInGrids(this.modelList, this.arrMODEL_GRIDS);
        fillInGrids(this.rawList, this.arrGrids);
        fillInGrids(this.rawList, this.arrOrig_grids);
        
        initRealTimeGridMap(len);
        System.out.println("RAWLIST " + rawList);
        System.out.println("SOLVABLE? " + solvable());
        System.out.println("INITIAL MAP IS SHOWN AS BELOW");
        // dispGrids(this.grids);
        dispGrids(N, arrGrids);
        System.out.println("MODEL MAP IS SHOWN AS BELOW");
        dispGrids(N, arrMODEL_GRIDS);
    }

    public void generateRandomGame(int n, int hard) {
        this.g_found = false;
        this.N = n;
        this.rawList = new ArrayList<>();
        this.modelList = new ArrayList<>();
        for (int i = 1; i <= N * N - 1; i++) { // 0 - n^2-1
            this.rawList.add(i);
            this.modelList.add(i);
        }
        this.rawList.add(0);
        this.modelList.add(0);
        int len = rawList.size();
        this.arrGrids = new int[len];
        this.arrMODEL_GRIDS = new int[len];
        this.arrOrig_grids = new int[len];
        this.rtMap = new int[len];
        fillInGrids(this.modelList, this.arrMODEL_GRIDS);
        do {
            System.out.println("TRY GEN LEGALLY & RANDOMLY---");
            Collections.shuffle(this.rawList); // 打乱
            fillInGrids(this.rawList, this.arrGrids);
        } while (!solvable() || manhattan(N, arrMODEL_GRIDS, arrGrids) >= hard);
        fillInGrids(this.rawList, this.arrOrig_grids);
        initRealTimeGridMap(len);
        System.out.println("MANHATTAN=" + manhattan(N, arrMODEL_GRIDS, arrGrids));
        System.out.println("RAWLIST " + rawList);
        System.out.println("INITIAL MAP IS SHOWN AS BELOW");
        // dispGrids(this.grids);
        dispGrids(N, arrGrids);
        System.out.println("MODEL MAP IS SHOWN AS BELOW");
        // dispGrids(this.MODEL_GRIDS);
        dispGrids(N, arrMODEL_GRIDS);
    }

    private void initRealTimeGridMap(int len) {
        for (int i = 0; i < len; i++) {
            rtMap[arrGrids[i]] = i;
        }
    }

    /** 将初始化的list填入坐标数组中. */
    private static void fillInGrids(List<Integer> rawList, int[] grids) {
        for (int i = 0; i < rawList.size(); i++) {
            grids[i] = rawList.get(i);
        }
    }

    /** TODO 将来可以用制表符进行修饰. */
    public static void dispGrids(int N, final int[] grids) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6 * N + 1; i++) {
            sb.append("=");
        }
        System.out.println(sb.toString());
        for (int i = 0; i < N; i++) {
            System.out.printf("|");
            for (int j = i * N; j < N + N * i; j++) {
                if (grids[j] != 0) {
                    System.out.printf(" %3d |", grids[j]);
                } else {
                    System.out.printf(" %3s |", "");
                }
            }
            System.out.printf("\n");
        }
        System.out.println(sb.toString());
    }

    private boolean solvable() {
        int delta = 0;
        if ((N & 1) == 0) { // 偶数
            delta = findZeroLineIndex(N, arrMODEL_GRIDS) - findZeroLineIndex(N, arrGrids);
        }
        long nReverseOrderModel = findReversePairs(modelList);
        long nReverseOrderThis = findReversePairs(rawList);

        return (Math.abs(nReverseOrderModel - nReverseOrderThis + delta) & 1) == 0;
    }

    private static long findReversePairs(final List<Integer> list) {
        List<Integer> copy = new ArrayList<>(list);
        copy.remove((Integer) 0); // 需要把0去掉！
        int[] A = new int[copy.size()];
        int i = 0;
        for (int x : copy) {
            A[i++] = x;
        }
        IVP ivp = new IVP();
        ivp.getReverseCount(A);
        return ivp.count;
    }

    /** 找到0的Y轴位置. */
    private static int findZeroLineIndex(int N, final int[] grids) {
        for (int i = 0; i < grids.length; i++) {
            if (grids[i] == 0) {
                return i / N;
            }
        }
        return 0;
    }

    public void solve() {
        if (!solvable()) {
            System.out.println("unsolvable!");
            return;
        }
        g_found = false;
        bound = manhattan(N, arrMODEL_GRIDS, arrGrids);
        System.out.println("bound = " + bound);
        solutions = new MoveDir[100];
        Coordinate initPoint = fetchCoordinate(N, arrGrids, 0);
        Coordinate dispPoint = new Coordinate(initPoint.getX(), initPoint.getY());
        System.out.println(initPoint);
        System.out.println("DISP " + dispPoint);
        int iter = 0;
        while (!g_found && bound < 6 * N * N) { // 这里假设移动代价不大于6倍的格子数
            iter++;
            System.out.println("ITER LOOP =" + iter);
            bound = DFS(initPoint, 0, MoveDir.INIT);
            if (iter > 26) {
                System.out.println("unsolvable due to timeout!");
                break;
            }
        }
        System.out.println("FOUND? " + g_found + " bound = " + bound);
        System.out.println(Arrays.toString(arrGrids));
        dispSolutions(dispPoint);
    }

    private void dispSolutions(Coordinate dispPoint) {
        MoveDir cur = solutions[0];
        int i = 0;
        dispGrids(N, arrOrig_grids);
        while (cur != null && i < solutions.length) {
            System.out.println("SPACE MOVE --> " + cur);
            move(dispPoint, N, arrOrig_grids, cur);
            System.out.println("AFTER");
            dispGrids(N, arrOrig_grids);
            System.out.println("NEXT pt=" + dispPoint);
            i++;
            cur = solutions[i];
        }
        System.out.println("ALL LEGAL STEPS ARE " + i + " STEPS.");
        System.out.println("ALL CALC Manhattan time is " + time1 + "ms.");
        // System.out.println("ALL CANTOR Manhattan time is " + time2 + "ms.");
    }

    private int DFS(Coordinate pt, int depth, MoveDir lastMove) {
        int manhattan = manhattan(N, arrMODEL_GRIDS, arrGrids);
        if (manhattan + depth > bound) {
            return manhattan + depth; // 超了上限，不看了
        }
        if (manhattan == 0) {
            g_found = true; // 找到了
            return depth;
        }
        int next_bound = Integer.MAX_VALUE;
        for (MoveDir dir : MoveDir.legalMoves()) {
            if (dir.reverse().equals(lastMove)) {
                continue;
            }
            if (legalMove(pt, N, dir)) {
                move(pt, N, arrGrids, dir);
                // dispGrids(grids);
                solutions[depth] = dir;
                int new_bound = DFS(pt, depth + 1, dir);
                if (g_found) {
                    return new_bound;
                }
                next_bound = Math.min(next_bound, new_bound);
                move(pt, N, arrGrids, dir.reverse());
            }
        }
        return next_bound;
    }

    private void move(Coordinate pt, int N, int[] grids, MoveDir dir) {
        int x = pt.getX();
        int y = pt.getY();
        int nx = x;
        int ny = y;
        if (MoveDir.UP.equals(dir)) {
            ny--;
        } else if (MoveDir.DOWN.equals(dir)) {
            ny++;
        } else if (MoveDir.LEFT.equals(dir)) {
            nx--;
        } else if (MoveDir.RIGHT.equals(dir)) {
            nx++;
        }
        swap(grids, N, x, y, nx, ny);
        pt.setX(nx);
        pt.setY(ny);
    }

    /* 注意x代表数组的第二维！y代表第一维 */
    private void swap(int[] grids, int N, int x, int y, int nx, int ny) {
        int oldIndex = N * y + x;
        int newIndex = N * ny + nx;
        int tmp = grids[oldIndex];
        grids[oldIndex] = grids[newIndex];
        grids[newIndex] = tmp;

        int oldV = grids[oldIndex];
        int newV = tmp;
        
        tmp = rtMap[oldV]; // grids的映射表也发生了变化
        rtMap[oldV] = rtMap[newV];
        rtMap[newV] = tmp;
    }

    private static boolean legalMove(final Coordinate pt, int N, MoveDir dir) {
        int MAX = N - 1;
        return (!MoveDir.DOWN.equals(dir) || pt.getY() != MAX) && (!MoveDir.UP.equals(dir) || pt.getY() != 0)
                && (!MoveDir.LEFT.equals(dir) || pt.getX() != 0) && (!MoveDir.RIGHT.equals(dir) || pt.getX() != MAX);
    }

    // manhattan似乎是瓶颈,但cantor带来了更复杂的计算，所以得不偿失
    public int manhattan(int N, int[] arrMODEL_GRIDS, int[] arrGrids) {
        long x1 = System.currentTimeMillis();
        int manhattan = 0;
        int len = arrMODEL_GRIDS.length;
        for (int i = 0; i < len; i++) {
            int arrModelI = arrMODEL_GRIDS[i];
            if (arrModelI != 0) {
                int arrI = rtMap[arrModelI];
                manhattan += Math.abs(i % N - arrI % N) + Math.abs(i / N - arrI / N);
            }
        }
        long x2 = System.currentTimeMillis();
        time1 += (x2 - x1); // 统计一下总共花在manhattan的时间
        return manhattan;
    }

    /** 坐标均以0开头，右下象限 找到val的坐标. */
    private static Coordinate fetchCoordinate(int N, int[] grids, int val) {
        for (int i = 0; i < grids.length; i++) {
            if (val == grids[i]) {
                return new Coordinate(i % N, i / N);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        NPuzzleEasyAlgo main = new NPuzzleEasyAlgo();
        main.generateRandomGame(4, 46);
        main.solve();
    }
}
