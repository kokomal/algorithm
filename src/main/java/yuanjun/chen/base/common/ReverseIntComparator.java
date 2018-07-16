package yuanjun.chen.base.common;

import java.util.Comparator;

public class ReverseIntComparator implements Comparator<Integer> {

    @Override
    public int compare(Integer o1, Integer o2) {
        // 如果o1小于o2，我们就返回正值，如果n1大于n2我们就返回负值，
        if (o1 < o2) {
            return 1;
        } else if (o1 > o2) {
            return -1;
        } else {
            return 0;
        }
    }

}
