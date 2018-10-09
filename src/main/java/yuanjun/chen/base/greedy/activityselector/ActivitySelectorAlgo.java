/**
 * @Title: ActivitySelectorAlgo.java
 * @Package: yuanjun.chen.base.greedy.activityselector
 * @Description: 分别采用递归和非递归的活动安排贪心算法
 * @author: 陈元俊
 * @date: 2018年10月8日 下午4:19:34
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.greedy.activityselector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import yuanjun.chen.base.greedy.MyActivity;

/**
 * @ClassName: ActivitySelectorAlgo
 * @Description: 分别采用递归和非递归的活动安排贪心算法
 * @author: 陈元俊
 * @date: 2018年10月8日 下午4:19:34
 */
public class ActivitySelectorAlgo {
    private static MyActivity[] activities; // n+1维度
    private static MyActivity[] nonRecurActivities; // n维度

    public static void recursiveInit(final MyActivity[] srcActivities) {
        int len = srcActivities.length;
        activities = new MyActivity[len + 1];
        System.arraycopy(srcActivities, 0, activities, 1, len);
        activities[0] = new MyActivity(0, 0L, 0L); // 添加一个假的头部
        Arrays.sort(activities, new MyActivity.MyActivityComparator(true));
        // System.out.println(Arrays.toString(activities)); // after
    }

    public static void nonRecursiveInit(final MyActivity[] srcActivities) {
        int len = srcActivities.length;
        nonRecurActivities = new MyActivity[len];
        System.arraycopy(srcActivities, 0, nonRecurActivities, 0, len);
        Arrays.sort(nonRecurActivities, new MyActivity.MyActivityComparator(true));
    }
    
    /**   
     * @Title: recursiveWrapper   
     * @Description: 递归法        
     * @return: void      
     */
    public static void recursiveWrapper() {
        List<MyActivity> res = recursiveActivitySelect(0, activities.length - 1);
        System.out.println(res);
    }
    
    /**   
     * @Title: greedyNonRecursiveSelectWrapper   
     * @Description: 贪心单调迭代法        
     * @return: void      
     */
    public static void greedyNonRecursiveSelectWrapper() {
        int len = nonRecurActivities.length;
        List<MyActivity> res = new ArrayList<>();
        res.add(nonRecurActivities[0]);
        int k = 1;
        for (int m = 2; m <= len; m++) {
            if (nonRecurActivities[m - 1].getStartTime() >= nonRecurActivities[k - 1].getEndTime()) {
                res.add(nonRecurActivities[m - 1]);
                k = m;
            }
        }
        System.out.println(res);
    }
    
    // 在k和n之间寻找下一个合法的起始值
    public static List<MyActivity> recursiveActivitySelect(int k, int n) {
        int m = k + 1;
        while (m <= n && (activities[m].getStartTime() < activities[k].getEndTime())) {
            m = m + 1;
        }
        System.out.printf("For k = %d AND n = %d, the m is %d\n", k, n, m);
        if (m <= n) {
            List<MyActivity> res = new ArrayList<>();
            res.add(activities[m]);
            res.addAll(recursiveActivitySelect(m, n));
            return res;
        } else {
            return new ArrayList<>();
        }
    }
    
    public static void main(String[] args) {
        MyActivity[] activities = new MyActivity[] {
                new MyActivity(9, 8L, 12L),
                new MyActivity(10, 2L, 14L),
                new MyActivity(11, 12L, 16L),
            new MyActivity(1, 1L, 4L),
            new MyActivity(2, 3L, 5L),
            new MyActivity(3, 0L, 6L),
            new MyActivity(4, 5L, 7L),
            new MyActivity(5, 3L, 9L),
            new MyActivity(6, 5L, 9L),
            new MyActivity(7, 6L, 10L),
            new MyActivity(8, 8L, 11L),
        };
        recursiveInit(activities);
        recursiveWrapper();
        
        nonRecursiveInit(activities);
        greedyNonRecursiveSelectWrapper();
    }

}
