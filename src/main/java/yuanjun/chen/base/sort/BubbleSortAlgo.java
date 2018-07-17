package yuanjun.chen.base.sort;

import yuanjun.chen.base.common.MyArrayUtils;
import yuanjun.chen.base.common.SortOrderEnum;

/*
 * 冒泡排序
 */
public class BubbleSortAlgo {
    /*
     * 每次从位置0开始冒泡，决定最右侧max值，每冒泡一次，则待排序的数组左移一格
     */
    public static void inplaceBubbleSort(Integer[] arr, SortOrderEnum order) {
        int len = arr.length;
        if (len <= 1) {
            return; // 为单值则不处理
        }
        for (int i = len - 1; i > 0; i--) { // 右值limit，每次冒泡缩进1
            for (int j = 0; j < i; j++) { // 冒泡起始点
                boolean shouldSwapAsc = order.equals(SortOrderEnum.ASC) && arr[j] > arr[j + 1];
                boolean shouldSwapDesc = order.equals(SortOrderEnum.DESC) && arr[j] < arr[j + 1];
                if (shouldSwapAsc || shouldSwapDesc) { // 符合翻转条件，仅为升序时，左值>右值，或者降序时，右值>左值
                    MyArrayUtils.swap(arr, j, j + 1);
                }
            }
        }
    }

}
