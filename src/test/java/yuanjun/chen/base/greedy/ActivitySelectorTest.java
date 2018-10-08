/**  
 * @Title: ActivitySelectorTest.java   
 * @Package: yuanjun.chen.base.greedy   
 * @Description: TODO(用一句话描述该文件做什么)   
 * @author: 陈元俊     
 * @date: 2018年10月8日 下午4:24:06   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.base.greedy;

import java.util.Arrays;
import org.junit.Test;
import yuanjun.chen.base.greedy.activityselector.MyActivity;
import yuanjun.chen.base.greedy.activityselector.MyActivityComparator;

/**   
 * @ClassName: ActivitySelectorTest   
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 陈元俊 
 * @date: 2018年10月8日 下午4:24:06  
 */
public class ActivitySelectorTest {
    @Test
    public void testSortActivity() {
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
        System.out.println(Arrays.toString(activities)); // before
        Arrays.sort(activities, new MyActivityComparator());
        System.out.println("=====================AFTER SORTING======================");
        System.out.println(Arrays.toString(activities)); // after
    }
}
