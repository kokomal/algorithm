/**
 * @Title: IntervalGraphColorProblemAlgo.java
 * @Package: yuanjun.chen.base.greedy.intervalgraphcolor
 * @Description: 区间图着色问题的贪心解法
 * @author: 陈元俊
 * @date: 2018年10月8日 下午5:55:26
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.greedy.intervalgraphcolor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: IntervalGraphColorProblemAlgo
 * @Description: 区间图着色问题的贪心解法
 * @author: 陈元俊
 * @SpecialThanksTo: 六种鱼 & Michelle Bodnar, Andrew Lohr
 * @date: 2018年10月8日 下午5:55:26
 */
public class IntervalGraphColorProblemAlgo {
    /** 2n维度. */
    private static TimePojo[] times;

    private static List<Integer> freeset;
    private static List<Integer> busyset;

    public static void init(final MyActivity[] srcActivities) {
        freeset = new ArrayList<>();
        busyset = new ArrayList<>();
        int len = srcActivities.length;
        /** N维度. */
        MyActivity[] activities = new MyActivity[len];
        times = new TimePojo[2 * len];
        System.arraycopy(srcActivities, 0, activities, 0, len);
        int idx = 0;
        for (MyActivity act : activities) {
            times[idx] = new TimePojo(act.getIndex(), act.getStartTime(), TimeDir.BEGIN);
            times[++idx] = new TimePojo(act.getIndex(), act.getEndTime(), TimeDir.END);
            ++idx;
        }
        Arrays.sort(times, new TimePojo.TimePojoComparator()); // 将times排序排好
    }

    public static void solve() {
        int classrooms = 0;
        for (TimePojo time : times) {
            if (TimeDir.BEGIN.equals(time.getDir())) { // 开始
                // 如果是开始，那么找一个free的教室，如果free教室不在，则新增一个教室
                if (freeset.isEmpty()) {
                    classrooms++;
                    busyset.add(classrooms);
                    time.setClassroom(classrooms); // 标定教室的号码
                    System.out.println("Activity No." + time.getIndex() + " using classroom " + classrooms + "#");
                } else {
                    busyset.add(freeset.get(0)); // 取出freeset的第一个给busyset
                    time.setClassroom(freeset.get(0));
                    freeset.remove(0);
                    System.out.println(
                            "Activity No." + time.getIndex() + " using classroom " + time.getClassroom() + "#");
                }
                markEndClassroom(time); // 需要将结束点的index的classroom也标记上，这个效率比较低
            } else { // 结束
                freeset.add(0, time.getClassroom());
                busyset.remove((Integer) time.getClassroom());
            }
        }
        System.out.println("finally the maximum classroom needed is " + classrooms);
    }

    private static void markEndClassroom(TimePojo time) {
        for (TimePojo time2 : times) {
            if (time2.getIndex() == time.getIndex() && TimeDir.END.equals(time2.getDir())) {
                time2.setClassroom(time.getClassroom());
                break;
            }
        }
    }

    public static void main(String[] args) {
        MyActivity[] activities = new MyActivity[] {new MyActivity(9, 8L, 12L), // 4#
                new MyActivity(10, 2L, 14L), // 3#
                new MyActivity(11, 12L, 16L), // 4#
                new MyActivity(1, 1L, 4L), // 2#
                new MyActivity(2, 3L, 5L), // 4#
                new MyActivity(3, 0L, 6L), // 1#
                new MyActivity(4, 5L, 7L), // 4#
                new MyActivity(5, 3L, 9L), // 5#
                new MyActivity(6, 5L, 9L), // 2#
                new MyActivity(7, 6L, 10L), // 1#
                new MyActivity(8, 8L, 11L), // 6#
        };
        init(activities);
        solve();
    }
}
