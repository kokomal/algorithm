/**
 * @Title: MyActivityComparator.java
 * @Package: yuanjun.chen.base.greedy.activityselector
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年10月8日 下午4:26:38
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.greedy.activityselector;

import java.util.Comparator;

/**
 * @ClassName: MyActivityComparator
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author: 陈元俊
 * @date: 2018年10月8日 下午4:26:38
 */
public class MyActivityComparator implements Comparator<MyActivity> {
    @Override
    public int compare(MyActivity o1, MyActivity o2) {
        if (o1.getEndTime() > o2.getEndTime()) {
            return 1;
        } else if (o1.getEndTime() < o2.getEndTime()) {
            return -1;
        }
        return 0;
    }
}
