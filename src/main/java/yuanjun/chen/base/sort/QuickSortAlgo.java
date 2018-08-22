/**
 * @Title: QuickSortAlgo.java
 * @Package: yuanjun.chen.base.sort
 * @Description: 快速排序算法
 * @author: 陈元俊
 * @date: 2018年7月19日 上午8:46:11
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.sort;

import static yuanjun.chen.base.common.CommonUtils.less;
import java.util.Arrays;
import java.util.Random;
import yuanjun.chen.base.common.MyArrayUtils;

/**
 * @ClassName: QuickSortAlgo
 * @Description: 快速排序算法 TODO sortOrderEnum
 * @author: 陈元俊
 * @date: 2018年7月19日 上午8:46:11
 */
public class QuickSortAlgo {
    /** 原始快速排序版本，pivot的选取每次均选择arr[r]. */
    @SuppressWarnings("rawtypes")
    public static void quickSort_v1(Comparable[] arr) {
        quickSortRaw(arr, 0, arr.length - 1);
    }

    /** Pivot的选取每次均选择[r,p]直接的随机值. */
    @SuppressWarnings("rawtypes")
    public static void quickSort_v2(Comparable[] arr) {
        quickSortRandom(arr, 0, arr.length - 1);
    }

    /** Pivot的选取每次均选择[r,p]直接的随机值 采用while循环避免了一半的递归深度. */
    @SuppressWarnings("rawtypes")
    public static void quickSort_v3(Comparable[] arr) {
        quickSortRandomWithLoop(arr, 0, arr.length - 1);
    }

    /** Random快速排序内部实现2 带有while循环，免了一个递归. */
    @SuppressWarnings("rawtypes")
    private static void quickSortRandomWithLoop(Comparable[] arr, int p, int r) {
        while (p <= r) {
            int pivot = randomizedPartition(arr, p, r);
            quickSortRandom(arr, p, pivot - 1);
            p = pivot + 1;
        }
    }

    /** Random快速排序内部实现. */
    @SuppressWarnings("rawtypes")
    private static void quickSortRandom(Comparable[] arr, int p, int r) {
        if (p > r) {
            return;
        }
        int pivot = randomizedPartition(arr, p, r);
        quickSortRandom(arr, p, pivot - 1);
        quickSortRandom(arr, pivot + 1, r);
    }

    @SuppressWarnings("rawtypes")
    public static int randomizedPartition(Comparable[] arr, int p, int r) {
        if (r == p) {
            return p;
        }
        Random rd = new Random();
        int randomPivot = rd.nextInt(r - p + 1) + p;
        MyArrayUtils.swap(arr, randomPivot, r);
        return partition(arr, p, r);
    }

    /** 经典快速排序内部实现. */
    @SuppressWarnings("rawtypes")
    private static void quickSortRaw(Comparable[] arr, int p, int r) {
        int len = arr.length;
        if (len <= 1 || p >= r) {
            return;
        }
        int pivot = partition(arr, p, r);
        quickSortRaw(arr, p, pivot - 1);
        quickSortRaw(arr, pivot + 1, r);
    }

    /**
     * @Title: findPivot
     * @Description: 找到中轴点,目前以尾部序列作为比较点，最后再将尾部值回填pivot位
     * @param: Integer []
     * @param: p
     * @param: r
     * @return: int
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static int partition(Comparable[] arr, int p, int r) {
        if (p == r) {
            return p;
        }
        int i = p - 1;
        Comparable pivotVal = arr[r];
        for (int j = p; j < r; j++) {
            if (less(arr[j], pivotVal)) {
                MyArrayUtils.swap(arr, ++i, j);
            }
        }
        MyArrayUtils.swap(arr, i + 1, r); // 回填arr[r]到pivot点
        return i + 1;
    }

    /** 固定pivot的partition算法 前提是pivot必然存在arr的[p,r]区间内，否则没有任何意义. */
    public static <T extends Comparable<T>> int partitionWithFixedPivot(T[] arr, int p, int r, final T pivot) {
        if (p == r) {
            return p;
        }
        int i = p - 1;
        for (int j = p; j <= r; j++) {
            if (less(arr[j], pivot)) {
                // System.out.println( "i+1= " + (i+1) + " j= " + j + " prepare swap " + arr[i + 1] + " and " +
                // arr[j]);
                MyArrayUtils.swap(arr, ++i, j);
                // i = i + 1;
                // System.out.println(Arrays.toString(arr));
            }
        }
        int j = MyArrayUtils.findIndex(arr, pivot);
        MyArrayUtils.swap(arr, i + 1, j);
        return i + 1;
    }

    public static void main(String[] args) throws Exception {
        Double[] arr = new Double[] {5.689676, 7.6249104, 3.7767596, 3.926818, 0.21661937, 0.44705093, 1.2294692,
                9.2513485, 3.5963755, 4.140664, 5.117749, 6.624312, 1.7996395, 9.451531, 5.047748, 7.8997207, 7.413413,
                7.368796, 7.1566544, 0.36539853};
        System.out.println("before--" + Arrays.toString(arr));
        System.out.println(partitionWithFixedPivot(arr, 0, arr.length - 1, 3.926818));
        System.out.println("after--" + Arrays.toString(arr));
    }
}
