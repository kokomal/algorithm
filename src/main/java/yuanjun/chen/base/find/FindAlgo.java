/**
 * @Title: FindAlgo.java
 * @Package: yuanjun.chen.base.find
 * @Description: 通用查找类
 * @author: 陈元俊
 * @date: 2018年7月26日 上午8:30:39
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.find;

import static yuanjun.chen.base.common.CommonUtils.less;
import static yuanjun.chen.base.common.CommonUtils.more;
import java.math.BigDecimal;
import java.util.Arrays;
import org.apache.log4j.Logger;
import yuanjun.chen.base.common.ExtremeEnum;
import yuanjun.chen.base.common.MyPair;
import yuanjun.chen.base.common.RandomGenner;
import yuanjun.chen.base.sort.QuickSortAlgo;

/**
 * @ClassName: FindAlgo
 * @Description: 通用查找类
 * @author: 陈元俊
 * @date: 2018年7月26日 上午8:30:39
 */
public class FindAlgo {
    private static final Logger logger = Logger.getLogger(FindAlgo.class);
    /**
     * 只寻找最小值，n-1次比较
     */
    public static <T extends Comparable<?>> T findMinOnly(final T[] arr) {
        return findOneExtreme(arr, ExtremeEnum.MIN);
    }

    /**
     * 只寻找最大值，n-1次比较
     */
    public static <T extends Comparable<?>> T findMaxOnly(final T[] arr) {
        return findOneExtreme(arr, ExtremeEnum.MAX);
    }

    /**
     * 查找第i大的数据，不应该把原始数据的内容打乱 因此需要保护性拷贝一份
     */
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<?>> T randomizedSelectIthMaxWrapper(final T[] arr, int i) {
        if (i > arr.length)
            return null;
        T[] tmp = (T[]) new Comparable[arr.length];
        System.arraycopy(arr, 0, tmp, 0, arr.length);
        return randomizedSelectIthMax(tmp, 0, tmp.length - 1, i);
    }

    // i从1开始，[1,len]
    private static <T extends Comparable<?>> T randomizedSelectIthMax(final T[] arr, int p, int r, int i) {
        if (p == r)
            return arr[p];
        int q = QuickSortAlgo.randomizedPartition(arr, p, r); // "跨界"调用快速排序的随机partition
        int k = q - p + 1;
        if (i == k) { // 找到了！
            return arr[q];
        } else if (i < k) { // 说明在前面，那么pivot后面的不看了
            return randomizedSelectIthMax(arr, p, q - 1, i); // 这个比较伤，需要在前面重新找前i号
        } else { // 说明在后面，那么pivot前面的也不看了
            return randomizedSelectIthMax(arr, q + 1, r, i - k); // 继续到后半段找前i-k号
        }
    }

    /**
     * 单个遍历，三次比较获得最小值和最大值 耗时O[3/2n]
     */
    public static <T extends Comparable<?>> MyPair<T> fineBothMinAndMax(final T[] arr) {
        int len = arr.length;
        if (len == 0)
            return null;
        MyPair<T> res = new MyPair<>();
        if (len % 2 == 1) { // 奇数
            res.setMax(arr[0]);
            res.setMin(arr[0]);
            for (int i = 1; i < len; i += 2) { // 每次步进2
                challenge(arr, res, i);
            }
        } else { // 偶数
            res.setMax(more(arr[0], arr[1]) ? arr[0] : arr[1]);
            res.setMin(less(arr[0], arr[1]) ? arr[0] : arr[1]);
            for (int i = 2; i < len; i += 2) { // 每次步进2
                challenge(arr, res, i);
            }
        }
        return res;
    }

    /**
     * 3次challenge
     *
     * @Title: challenge
     * @Description: 3次challenge
     * @param: arr
     * @param: res
     * @param: i
     * @return: void
     */
    private static <T extends Comparable<?>> void challenge(final T[] arr, MyPair<T> res, int i) {
        if (more(arr[i], arr[i + 1])) { // compare#1
            res.challengeMax(arr[i]); // compare#2
            res.challengeMin(arr[i + 1]); // compare#3
        } else {
            res.challengeMax(arr[i + 1]);
            res.challengeMin(arr[i]);
        }
    }

    private static <T extends Comparable<?>> T findOneExtreme(final T[] arr, ExtremeEnum extreme) {
        int len = arr.length;
        if (len == 0)
            return null;
        T extremeVal = arr[0];
        for (int i = 1; i < len; i++) {
            if ((ExtremeEnum.MIN.equals(extreme) && less(arr[i], extremeVal))
                    || (ExtremeEnum.MAX.equals(extreme) && more(arr[i], extremeVal))) {
                extremeVal = arr[i];
            }
        }
        return extremeVal;
    }

    public static void main(String[] args) throws Exception {
        BigDecimal[] arr1 = RandomGenner.generateRandomTArray(10000, 20000, BigDecimal.class);
        System.out.println("min = " + findMinOnly(arr1));
        System.out.println("max = " + findMaxOnly(arr1));
        MyPair<BigDecimal> pair = fineBothMinAndMax(arr1);
        System.out.println("max = " + pair.getMax() + ", and min = " + pair.getMin());

        Integer[] arr2 = RandomGenner.generateRandomTArray(100, 400, Integer.class);
        System.out.println("before---" + Arrays.toString(arr2));
        int ith = 12;
        Integer ithVal = randomizedSelectIthMaxWrapper(arr2, ith);
        System.out.println("for ith: " + ith + " max, the val = " + ithVal);
        System.out.println("after---" + Arrays.toString(arr2));
        Arrays.sort(arr2);
        System.out.println("sorted array = " + Arrays.toString(arr2));
    }
}
