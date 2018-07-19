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

import yuanjun.chen.base.common.MyArrayUtils;

/**
 * @ClassName: QuickSortAlgo
 * @Description: 快速排序算法
 * @author: 陈元俊
 * @date: 2018年7月19日 上午8:46:11
 */
public class QuickSortAlgo {

    public static void quickSort(Integer[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    /*
     * 经典快速排序内部实现
     */
    private static void quickSort(Integer[] arr, int p, int r) {
        int len = arr.length;
        if (len <= 1 || p >= r) {
            return;
        }
        int pivot = findPivot(arr, p, r);
        quickSort(arr, p, pivot - 1);
        quickSort(arr, pivot + 1, r);
    }

    /**
     * @Title: findPivot
     * @Description: 找到中轴点,目前以尾部序列作为比较点，最后再将尾部值回填pivot位
     * @param: Integer []
     * @param: p
     * @param: r
     * @return: int
     */
    private static int findPivot(Integer[] arr, int p, int r) {
        int i = p - 1;
        int pivotVal = arr[r];
        for (int j = p; j < r; j++) {
            if (arr[j] < pivotVal) {
                MyArrayUtils.swap(arr, ++i, j);
            }
        }
        MyArrayUtils.swap(arr, i + 1, r); // 回填arr[r]到pivot点
        return i + 1;
    }

}
