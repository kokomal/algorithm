/**
 * @Title: MyActivity.java
 * @Package: yuanjun.chen.base.greedy.activityselector
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年10月8日 下午4:21:37
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.greedy.intervalgraphcolor;

import java.util.Comparator;

/**
 * @ClassName: MyActivity
 * @Description: 活动pojo
 * @author: 陈元俊
 * @date: 2018年10月8日 下午4:21:37
 */
public class MyActivity {
    private int index;
    private Long startTime;
    private Long endTime;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
        result = prime * result + index;
        result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MyActivity other = (MyActivity) obj;
        if (endTime == null) {
            if (other.endTime != null)
                return false;
        } else if (!endTime.equals(other.endTime))
            return false;
        if (index != other.index)
            return false;
        if (startTime == null) {
            return other.startTime == null;
        } else return startTime.equals(other.startTime);
    }

    public MyActivity(int index, Long startTime, Long endTime) {
        super();
        this.index = index;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "MyActivity [index=" + index + ", startTime=" + startTime + ", endTime=" + endTime + "]";
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public static class MyActivityComparator implements Comparator<MyActivity> {
        private final boolean usingEndTime;

        public MyActivityComparator(boolean usingEndTime) {
            this.usingEndTime = usingEndTime;
        }

        @Override
        public int compare(MyActivity o1, MyActivity o2) {
            if (usingEndTime) {
                if (o1.getEndTime() > o2.getEndTime()) {
                    return 1;
                } else if (o1.getEndTime() < o2.getEndTime()) {
                    return -1;
                }
            } else if (o1.getStartTime() > o2.getStartTime()) {
                return 1;
            } else if (o1.getStartTime() < o2.getStartTime()) {
                return -1;
            }
            return 0;
        }
    }

}
