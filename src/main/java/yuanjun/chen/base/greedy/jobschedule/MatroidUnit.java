/**
 * @Title: MatroidUnit.java
 * @Package: yuanjun.chen.base.greedy.jobschedule
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年10月24日 下午5:02:38
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.greedy.jobschedule;

import java.lang.reflect.Array;
import java.util.Arrays;
import yuanjun.chen.base.common.CommonUtils;

/**
 * @ClassName: MatroidUnit
 * @Description: 拟阵元素，自带比较子，可以用jdk内嵌的数组排序算法进行排序
 * @author: 陈元俊
 * @date: 2018年10月24日 下午5:02:38
 */
public class MatroidUnit<T extends Comparable<?>> implements Comparable<MatroidUnit<T>> {
    int index;
    T penalty;
    int deadline;

    @Override
    public int compareTo(MatroidUnit<T> o) { // 逆序排
        if (CommonUtils.more(penalty, o.penalty)) {
            return -1;
        } else if (CommonUtils.eq(penalty, o.penalty)) {
            return 0;
        }
        return 1;
    }

    @Override
    public String toString() {
        return "MatroidUnit [index=" + index + ", penalty=" + penalty + ", deadline=" + deadline + "]";
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        MatroidUnit<Integer>[] matroid = (MatroidUnit<Integer>[]) Array.newInstance(MatroidUnit.class, 10);
        for (int i = 0 ; i < 10; i++) {
            MatroidUnit<Integer> m = new MatroidUnit<>();
            m.penalty = 10 * i;
            m.deadline = i;
            m.index = i;
            matroid[i] = m;
        }
        Arrays.sort(matroid);
        System.out.println(Arrays.toString(matroid));
    }
}
