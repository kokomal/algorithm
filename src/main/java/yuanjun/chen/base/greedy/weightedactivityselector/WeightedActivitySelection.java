package yuanjun.chen.base.greedy.weightedactivityselector;

/**
 * @ClassName: WeightedActivitySelection
 * @Description: 加权活动分配，先以完成时间升序排列
 * @author: 陈元俊
 * @SpecialThanksTo: zhouhao011280s
 * @date: 2018年10月9日 上午11:31:18
 */
public class WeightedActivitySelection {

    public static void constructSolution(int[] opt, int[] q, int[] v, int number) {
        if (number == 0) {
            System.out.println("done");
        } else if (v[number] + opt[q[number]] > opt[number - 1]) { // 说明选择number
            System.out.println("choose Wt." + v[number] + " -- Activity " + number);
            constructSolution(opt, q, v, q[number]);
        } else {
            System.out.println("Bypass Activity " + number);
            constructSolution(opt, q, v, number - 1);
        }
    }

    public static int activitySelectionMaximizingValue(int[] start, int[] finish, int[] v, int N) {
        int[] q = new int[N + 1];
        for (int i = 1; i < q.length; i++) {
            q[i] = binarySearchCompatible(finish, start[i]); // 二分查找q，即对于每一个start[i]而言不冲突的最近一个序列号
        }
        int[] opt = new int[N + 1];
        opt[0] = 0;
        for (int i = 1; i < opt.length; i++) {
            opt[i] = 0;
            if (opt[q[i]] + v[i] > opt[i - 1]) {
                opt[i] = opt[q[i]] + v[i];
            } else {
                opt[i] = opt[i - 1];
            }
        }
        constructSolution(opt, q, v, N);
        return opt[N];
    }

    // 比较tricky的二分查找
    private static int binarySearchCompatible(int[] finish, int startTime) {
        int highestCompatible = 0;
        int low = 0;
        int high = finish.length - 1;
        int mid = 0;
        // Do binary search
        while (low <= high) {
            mid = (low + high) / 2;
            if (low == high) {
                /*
                 *       low   mid    high
                 * index  14    15     16
                 * val    60    99     101
                 * 如果startTime=100,那么下一回合low=high=16,mid=16,应该返回mid-1
                 * 
                 *       low   mid    high
                 * index  14    15     16
                 * val    60    101    104
                 * 如果startTime=100,那么下一回合low=high=14,mid=14,应该返回mid
                 * 
                 **/
                if (startTime < finish[mid]) { 
                    highestCompatible = mid - 1;
                } else if (startTime > finish[mid]) {
                    highestCompatible = mid;
                }
            }
            if (startTime < finish[mid]) {
                high = mid - 1;
            } else if (startTime > finish[mid]) {
                low = mid + 1;
            } else {
                highestCompatible = mid;
                break;
            }
        }
        return highestCompatible;
    }

    public static void main(String[] args) {
        int[] start = new int[] {-1, 1, 3, 0, 5, 3, 5, 6, 8, 8, 2, 12}; // 起始时间N+1维度
        // If finish-time array is not in order, sort it in O(N*lgN)
        int[] finish = new int[] {0, 4, 5, 6, 7, 9, 9, 10, 11, 12, 14, 16}; // 完成时间N+1维度
        // Different weights are given to different activities.
        int[] value = new int[] {0, 3, 2, 4, 8, 2, 5, 6, 5, 7, 4, 5}; // 权重
        int N = start.length - 1;
        System.out
                .println("The maximum value is " + activitySelectionMaximizingValue(start, finish, value, N));
    }
}
