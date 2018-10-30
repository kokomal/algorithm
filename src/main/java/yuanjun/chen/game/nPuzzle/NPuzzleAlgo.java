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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: MainTester
 * @Description: IDA*解决nDigits问题 在线试运算请参见http://tristanpenman.com/demos/n-puzzle/
 * @author: 陈元俊
 * @date: 2018年10月25日 下午3:01:28
 */
public class NPuzzleAlgo {
    private List<Integer> rawList;
    private List<Integer> modelList;
    
    @Deprecated
    private Map<String, Integer> manhattanCache = new HashMap<>(); // 用hashmap，暂时不考虑并发
    @Deprecated
    private boolean usingCantorCache = false; // 默认不打开cantor的cache
    @Deprecated
    private Long modelHash = 0L;
    @Deprecated
    public void setUsingCantorCache(boolean usingCantorCache) {
        this.usingCantorCache = usingCantorCache;
    }

    /** N*n, 且小数位于左上. */
    private int[][] MODEL_GRIDS; // 目标坐标
    /** N*n. */
    private int[][] grids; // 实际变化的坐标，解完后（如果有解）则与目标坐标吻合
    private int[][] orig_grids; // 原始坐标，用于展示
    private int N;
    /** 全局的寻找flag，找到即可终止所有分支. */
    private boolean g_found;
    /** 初始化的bound，最好有个边界. */
    private int bound;

    private MoveDir[] solutions;

    public void generateWithNsizeRawList(List<Integer> rawList) {
        manhattanCache.clear();
        this.g_found = false;
        this.N = (int) Math.sqrt(rawList.size()); // 请输入完全平方长度的list，0代表空格
        this.grids = new int[N][N];
        this.orig_grids = new int[N][N];
        this.MODEL_GRIDS = new int[N][N];
        this.rawList = new ArrayList<>(rawList);
        this.modelList = new ArrayList<>();
        for (int i = 1; i <= N * N - 1; i++) { // 0 - n^2-1
            this.modelList.add(i);
        }
        this.modelList.add(0);
        fillInGrids(N, this.rawList, this.grids);
        fillInGrids(N, this.rawList, this.orig_grids);
        fillInGrids(N, this.modelList, this.MODEL_GRIDS);
        System.out.println("RAWLIST " + rawList);
        System.out.println("SOLVABLE? " + solvable());
        System.out.println("INITIAL MAP IS SHOWN AS BELOW");
        dispGrids(this.grids);
        System.out.println("ORIGINAL MAP IS SHOWN AS BELOW");
        dispGrids(this.orig_grids);
        System.out.println("MODEL MAP IS SHOWN AS BELOW");
        dispGrids(this.MODEL_GRIDS);
        modelHash = CantorUtil.cantor(MODEL_GRIDS);
    }

    public void generateRandomGame(int n) {
        manhattanCache.clear();
        this.g_found = false;
        this.N = n;
        this.grids = new int[N][N];
        this.orig_grids = new int[N][N];
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
            fillInGrids(N, this.rawList, this.grids);
        } while (!solvable());
        fillInGrids(N, this.rawList, this.orig_grids);
        System.out.println("RAWLIST " + rawList);
        System.out.println("INITIAL MAP IS SHOWN AS BELOW");
        dispGrids(this.grids);
        System.out.println("ORIGINAL MAP IS SHOWN AS BELOW");
        dispGrids(this.orig_grids);
        System.out.println("MODEL MAP IS SHOWN AS BELOW");
        dispGrids(this.MODEL_GRIDS);
        modelHash = CantorUtil.cantor(MODEL_GRIDS);
    }

    /** 将初始化的list填入坐标数组中. */
    private static void fillInGrids(int N, List<Integer> rawList, int[][] grids) {
        int index = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grids[i][j] = rawList.get(index++);
            }
        }
    }

    /** TODO 将来可以用制表符进行修饰. */
    private static void dispGrids(final int[][] grids) {
        System.out.println("==============================");
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
        System.out.println("==============================");
    }

    private boolean solvable() {
        int delta = 0;
        if ((N & 1) == 0) { // 偶数
            delta = findZeroLineIndex(MODEL_GRIDS) - findZeroLineIndex(grids);
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
    private static int findZeroLineIndex(final int[][] grids) {
        return fetchCoordinate(grids, 0).getY();
    }

    public void solve() {
        if (!solvable()) {
            System.out.println("unsolvable!");
            return;
        }
        g_found = false;
        bound = manhattan(usingCantorCache, MODEL_GRIDS, grids);
        System.out.println("bound = " + bound);
        solutions = new MoveDir[100];
        Coordinate initPoint = fetchCoordinate(grids, 0);
        Coordinate dispPoint = new Coordinate(initPoint.getX(), initPoint.getY());
        System.out.println("DISP " + dispPoint);
        System.out.println("INITIAL PT " + initPoint);
        int iter = 0;
        while (!g_found && bound < 6 * N * N) { // 这里假设移动代价不大于6倍的格子数
            bound = DFS(initPoint, 0, MoveDir.INIT);
            iter++;
            System.out.println("ITER LOOP =" + iter);
            if (iter > 26) {
                System.out.println("unsolvable due to timeout!");
                break;
            }
        }
        System.out.println("FOUND? " + g_found + " bound = " + bound);
        dispSolutions(dispPoint);
    }

    private void dispSolutions(Coordinate dispPoint) {
        MoveDir cur = solutions[0];
        int i = 0;
        dispGrids(orig_grids);
        while (cur != null && i < solutions.length) {
            System.out.println("SPACE MOVE --> " + cur);
            move(dispPoint, orig_grids, cur);
            System.out.println("AFTER");
            dispGrids(orig_grids);
            System.out.println("NEXT pt=" + dispPoint);
            i++;
            cur = solutions[i];
        }
        System.out.println("ALL LEGAL STEPS ARE " + i + " STEPS.");
    }

    private int DFS(Coordinate pt, int depth, MoveDir lastMove) {
        int manhattan = manhattan(usingCantorCache, MODEL_GRIDS, grids);
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
            if (legalMove(pt, grids, dir)) {
                move(pt, grids, dir);
                // dispGrids(grids);
                solutions[depth] = dir;
                int new_bound = DFS(pt, depth + 1, dir);
                if (g_found) {
                    return new_bound;
                }
                next_bound = Math.min(next_bound, new_bound);
                move(pt, grids, dir.reverse());
            }
        }
        return next_bound;
    }

    private static void move(Coordinate pt, int[][] grids, MoveDir dir) {
        int x = pt.getX();
        int y = pt.getY();
        int nx = x;
        int ny = y;
        switch (dir) {
            case UP:
                ny--;
                break;
            case DOWN:
                ny++;
                break;
            case LEFT:
                nx--;
                break;
            case RIGHT:
                nx++;
                break;
            default:
                break;
        }
        swap(grids, x, y, nx, ny);
        pt.setX(nx);
        pt.setY(ny);
    }

    /* 注意x代表数组的第二维！y代表第一维*/
    private static void swap(int[][] grids, int x, int y, int nx, int ny) {
        int tmp = grids[y][x];
        grids[y][x] = grids[ny][nx];
        grids[ny][nx] = tmp;
    }

    private static boolean legalMove(final Coordinate pt, int[][] grids, MoveDir dir) {
        int MAX = grids.length - 1;
        boolean legal = (!MoveDir.DOWN.equals(dir) || pt.getY() != MAX) && (!MoveDir.UP.equals(dir) || pt.getY() != 0)
                && (!MoveDir.LEFT.equals(dir) || pt.getX() != 0) && (!MoveDir.RIGHT.equals(dir) || pt.getX() != MAX);
        return legal;
    }

    // manhattan似乎是瓶颈,但cantor带来了更复杂的计算，所以得不偿失
    public int manhattan(boolean cantor, int[][] MODEL_GRIDS, int[][] grids) {
        if (cantor) {
            Long grHash = CantorUtil.cantor(grids);
            String mhtKey = modelHash.toString() + "-" + grHash.toString();
            if (manhattanCache.containsKey(mhtKey)) {
                //System.out.println("cache HIT!!!");
                return manhattanCache.get(mhtKey);
            }
            int manhattan = calcManhattan(MODEL_GRIDS, grids);
            manhattanCache.put(mhtKey, manhattan);
            return manhattan;
        } else {
            int manhattan = calcManhattan(MODEL_GRIDS, grids);
            return manhattan;
        }
    }

    private static int calcManhattan(int[][] MODEL_GRIDS, int[][] grids) {
        int manhattan = 0;
        for (int i = 0; i < MODEL_GRIDS.length; i++) {
            for (int j = 0; j < MODEL_GRIDS[i].length; j++) {
                if (MODEL_GRIDS[i][j] != 0) {
                    Coordinate modelPoint = new Coordinate(j, i);
                    Coordinate other = fetchCoordinate(grids, MODEL_GRIDS[i][j]);
                    manhattan += modelPoint.distance(other);
                    modelPoint = null; // 迅速gc
                    other = null;
                }
            }
        }
        return manhattan;
    }

    /** 坐标均以0开头，右下象限 找到val的坐标. */
    private static Coordinate fetchCoordinate(int[][] grids, int val) {
        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids[i].length; j++) {
                if (val == grids[i][j]) {
                    return new Coordinate(j, i);
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        NPuzzleAlgo main = new NPuzzleAlgo();
        main.generateRandomGame(4);
        main.solve();
        
        main.generateRandomGame(5);
        main.solve();
    }
    
    public static long fact(int n) {
        if (n <= 1) return 1;
        return fact(n - 1) * n;
    }
}
