/**
 * @Title: MergeSortAlgo.java
 * @Package yuanjun.chen.base.sort
 * @Description: 归并排序
 * @author: 陈元俊
 * @date: 2018年7月17日 下午1:43:42
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.sort;

import yuanjun.chen.base.common.SortOrderEnum;
import static yuanjun.chen.base.common.CommonUtils.*;
/**
 * @ClassName: MergeSortAlgo
 * @Description:归并排序
 * @author: 陈元俊
 * @date: 2018年7月17日 下午1:43:42
 */
public class MergeSortAlgo {

    /*
     * 对外包装方法
     */
    @SuppressWarnings("rawtypes")
    public static Comparable[] mergeSort(Comparable[] arr, SortOrderEnum order) {
        return extraSpaceMergeSort(arr, 0, arr.length - 1, order);
    }

    @SuppressWarnings("rawtypes")
    private static Comparable[] extraSpaceMergeSort(Comparable[] arr, int low, int high, SortOrderEnum order) {
        int mid = (low + high) >>> 1; // 劈成2半
        if (low < high) {
            extraSpaceMergeSort(arr, low, mid, order);
            extraSpaceMergeSort(arr, mid + 1, high, order);
            merge(arr, low, mid, high, order);
        }
        return arr;
    }

    /**
     * @Title: merge
     * @Description: 归并
     * @param: @param left
     * @param: @param right
     * @return: Integer []
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void merge(Comparable[] arr, int low, int mid, int high, SortOrderEnum order) {
        Comparable[] temp = new Comparable[high - low + 1];
        int i = low;
        int j = mid + 1;
        int idx = 0;
        // 把较小（升序）|较大（降序）的数先移到新数组中
        while (i <= mid && j <= high) {
            if ((less(arr[i], arr[j]) && order.equals(SortOrderEnum.ASC))
                    || (more(arr[i], arr[j]) && order.equals(SortOrderEnum.DESC))) {
                temp[idx++] = arr[i++];
            } else {
                temp[idx++] = arr[j++];
            }
        }
        // 把左边剩余的数移入数组
        while (i <= mid) {
            temp[idx++] = arr[i++];
        }
        // 把右边剩余的数移入数组
        while (j <= high) {
            temp[idx++] = arr[j++];
        }
        // 把新数组中的数覆盖nums数组,影响篇幅为low~high,左闭右开
        System.arraycopy(temp, 0, arr, low, temp.length);
    }

}
