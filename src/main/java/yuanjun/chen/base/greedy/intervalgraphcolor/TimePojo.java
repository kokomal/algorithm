/**
 * @Title: TimePojo.java
 * @Package: yuanjun.chen.base.greedy
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年10月9日 上午8:53:51
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.greedy.intervalgraphcolor;

import java.util.Comparator;

/**
 * @ClassName: TimePojo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author: 陈元俊
 * @date: 2018年10月9日 上午8:53:51
 */
public class TimePojo {
    protected int index;
    protected Long time;
    protected TimeDir dir;
    protected int classroom;

    public int getIndex() {
        return index;
    }

    public int getClassroom() {
        return classroom;
    }

    public void setClassroom(int classroom) {
        this.classroom = classroom;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "TimePojo [index=" + index + ", time=" + time + ", dir=" + dir + ", classroom=" + classroom + "]";
    }

    public TimePojo(int index, Long time, TimeDir dir) {
        super();
        this.index = index;
        this.time = time;
        this.dir = dir;
        this.classroom = -1;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public TimeDir getDir() {
        return dir;
    }

    public void setDir(TimeDir dir) {
        this.dir = dir;
    }

    public static class TimePojoComparator implements Comparator<TimePojo> {
        @Override
        public int compare(TimePojo o1, TimePojo o2) {
            if (o1.time < o2.time) {
                return -1;
            } else if (o1.time > o2.time) {
                return 1;
            } else if (!o1.dir.equals(o2.dir)) {
                if (o1.dir.equals(TimeDir.END)) {
                    return -1;
                } else if (o1.dir.equals(TimeDir.BEGIN)) {
                    return 1;
                }
            }
            return 0;
        }
    }
}
