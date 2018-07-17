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

import java.util.Arrays;
import yuanjun.chen.base.common.RandomIntArrayGenner;
import yuanjun.chen.base.common.SortOrderEnum;

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
    public static Integer[] mergeSort(Integer[] arr, SortOrderEnum order) {
        return extraSpaceMergeSort(arr, 0, arr.length - 1, order);
    }

    private static Integer[] extraSpaceMergeSort(Integer[] arr, int low, int high, SortOrderEnum order) {
        int mid = (low + high) / 2; // 劈成2半
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
    private static void merge(Integer[] arr, int low, int mid, int high, SortOrderEnum order) {
        Integer[] temp = new Integer[high - low + 1];
        int i = low;
        int j = mid + 1;
        int idx = 0;
        // 把较小（升序）|较大（降序）的数先移到新数组中
        while (i <= mid && j <= high) {
            if ((arr[i] < arr[j] && order.equals(SortOrderEnum.ASC))
                    || (arr[i] > arr[j] && order.equals(SortOrderEnum.DESC))) {
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

    public static void main(String[] args) {
        int size = 30;
        int bound = 100;
        Integer[] arr = RandomIntArrayGenner.generateRandomIntArray(size, bound);
        System.out.println("before--" + Arrays.toString(arr));
        Integer[] res = extraSpaceMergeSort(arr, 0, arr.length - 1, SortOrderEnum.DESC);
        System.out.println("after--" + Arrays.toString(res));
    }
}
