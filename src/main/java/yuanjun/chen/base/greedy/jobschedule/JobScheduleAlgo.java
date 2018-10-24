/**
 * @Title: JobScheduleAlgo.java
 * @Package: yuanjun.chen.base.greedy.jobschedule
 * @Description: 单位时间任务调度问题
 * @author: 陈元俊
 * @date: 2018年10月24日 下午4:52:30
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.greedy.jobschedule;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName: JobScheduleAlgo
 * @Description: 单位时间任务调度问题
 * @author: 陈元俊
 * @date: 2018年10月24日 下午4:52:30
 */
public class JobScheduleAlgo<T extends Comparable<?>> {
    private static final int NON_OCCUPIED = 0;
    private static final int OCCUPIED = 1;
    private int N; // 多少个候选
    private int[] earlySet; // 最大化的早安排任务set，这里面不允许有delay

    private MatroidUnit<T>[] matroid; // 拟阵

    private List<MatroidUnit<T>> legalOnes;
    private List<MatroidUnit<T>> illegalOnes;

    /* 带截止日期和惩罚的初始化 */
    public void init(final int[] deadlines, final T[] penalties) {
        this.N = deadlines.length;
        initMatroid(deadlines, penalties);
        initArraysAndLists();
    }

    /* 直接带拟阵数组的初始化 */
    public void init(MatroidUnit<T>[] matroid) {
        this.matroid = matroid;
        this.N = this.matroid.length;
        initArraysAndLists();
    }

    private void initArraysAndLists() {
        this.earlySet = new int[N];
        legalOnes = new LinkedList<>();
        illegalOnes = new LinkedList<>();
    }

    @SuppressWarnings("unchecked")
    private void initMatroid(final int[] deadlines, final T[] penalties) {
        this.matroid = (MatroidUnit<T>[]) Array.newInstance(MatroidUnit.class, N);
        for (int i = 0; i < N; i++) {
            this.matroid[i].deadline = deadlines[i];
            this.matroid[i].penalty = penalties[i];
            this.matroid[i].index = i + 1; // index从1开始编号
        }
    }

    public void solve() {
        Arrays.sort(matroid); // 按照惩罚倒序排序取最大惩罚以此类推
        for (MatroidUnit<T> unit : matroid) {
            boolean isArrangeable = scanEarlySetForUnit(unit); // 扫描earlySet还有没有空位，有则添加，无则抛弃
            if (isArrangeable) {
                legalOnes.add(unit);
            } else {
                illegalOnes.add(unit);
            }
        }
        System.out.println("LEGAL ones " + legalOnes);
        System.out.println("ILLEGAL ones " + illegalOnes);
    }

    /**
     * @Title: scanEarlySetForUnit
     * @Description: 侦测按照权重递减的unit是否可以满足进入earlySet的条件，如果有则占位，没有则抛弃 这是此算法的贪心精髓
     * @param unit
     * @return: boolean
     */
    private boolean scanEarlySetForUnit(MatroidUnit<T> unit) {
        int deadline = unit.deadline;
        for (int i = deadline - 1; i >= 0; i--) {
            if (earlySet[i] == NON_OCCUPIED) {
                earlySet[i] = OCCUPIED; // 占位
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        MatroidUnit<Integer>[] matroid = (MatroidUnit<Integer>[]) Array.newInstance(MatroidUnit.class, 7);
        matroid[0] = new MatroidUnit<>();
        matroid[0].index = 1;
        matroid[0].penalty = 70;
        matroid[0].deadline = 4;

        matroid[1] = new MatroidUnit<>();
        matroid[1].index = 2;
        matroid[1].penalty = 60;
        matroid[1].deadline = 2;

        matroid[2] = new MatroidUnit<>();
        matroid[2].index = 3;
        matroid[2].penalty = 50;
        matroid[2].deadline = 4;

        matroid[3] = new MatroidUnit<>();
        matroid[3].index = 4;
        matroid[3].penalty = 40;
        matroid[3].deadline = 3;

        matroid[4] = new MatroidUnit<>();
        matroid[4].index = 5;
        matroid[4].penalty = 30;
        matroid[4].deadline = 1;

        matroid[5] = new MatroidUnit<>();
        matroid[5].index = 6;
        matroid[5].penalty = 20;
        matroid[5].deadline = 4;

        matroid[6] = new MatroidUnit<>();
        matroid[6].index = 7;
        matroid[6].penalty = 10;
        matroid[6].deadline = 6;

        JobScheduleAlgo<Integer> jj = new JobScheduleAlgo<>();
        jj.init(matroid);
        jj.solve();
    }
}
