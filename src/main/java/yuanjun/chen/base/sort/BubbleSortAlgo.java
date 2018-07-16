package yuanjun.chen.base.sort;

import java.util.Arrays;
import yuanjun.chen.base.common.DispUtil;
import yuanjun.chen.base.common.RandomIntArrayGenner;
import yuanjun.chen.base.common.SortOrderEnum;

public class BubbleSortAlgo {
    /*
     * 每次从位置0开始冒泡，决定最右侧max值，每冒泡一次，则待排序的数组左移一格
     */
    public static void inplaceBubbleSort(int[] arr, SortOrderEnum order) {
        int len = arr.length;
        if (len == 1)
            return; // 为单值则不处理
        for (int i = len - 1; i > 0; i--) { // 右值limit，每次冒泡缩进1
            for (int j = 0; j < i; j++) { // 冒泡起始点
                if ((order.equals(SortOrderEnum.ASC) && arr[j] > arr[j + 1])
                        || (order.equals(SortOrderEnum.DESC) && arr[j] < arr[j + 1])) { // 符合翻转条件，仅为升序时，左值>右值，或者降序时，右值>左值
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
        }
    }

    public static void main(String[] args) {
        testInplaceBubbleSort(20, 100, SortOrderEnum.ASC); // 升序测试
        DispUtil.split(100, '='); // 分行
        testInplaceBubbleSort(20, 100, SortOrderEnum.DESC); // 降序测试
        DispUtil.split(100, '='); // 分行
        testInplaceBubbleSort(2, 100, SortOrderEnum.ASC); // 升序测试
        DispUtil.split(100, '='); // 分行
        testInplaceBubbleSort(2, 100, SortOrderEnum.DESC); // 降序测试
    }

    private static void testInplaceBubbleSort(int size, int bound, SortOrderEnum order) {
        int[] arr = RandomIntArrayGenner.generateRandomIntArray(size, bound);
        System.out.println("the unsorted data---" + Arrays.toString(arr));
        inplaceBubbleSort(arr, order);
        System.out.println("after " + order + " bubble sort, the sorted data---" + Arrays.toString(arr));
    }

}
