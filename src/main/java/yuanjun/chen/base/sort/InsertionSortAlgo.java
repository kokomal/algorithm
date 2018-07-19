/**
 * @Title: InsertionSortAlgo.java
 * @Package yuanjun.chen.base.sort
 * @Description: 插入排序
 * @author: 陈元俊
 * @date: 2018年7月17日 上午8:37:03
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.sort;

import yuanjun.chen.base.common.SortOrderEnum;

/**
 * @ClassName: InsertionSortAlgo
 * @Description: 插入排序
 * @author: 陈元俊
 * @date: 2018年7月17日 上午8:37:03
 */
public class InsertionSortAlgo {
    /*
     * 简单平移法,逐一遍历后退
     */
    public static void inplaceInsertionSort(Integer[] arr, SortOrderEnum order) {
        int len = arr.length;
        if (len <= 1) {
            return;
        }
        for (int i = 1; i < len; i++) {
            // 对于第i个数而言，需要在前[0~i-1]的有序序列中，找到一个安身之地，然后将余下数组逐一移位
            int pre = i - 1;
            int tmp = arr[i];
            while (pre >= 0 && ((arr[pre] > tmp && order.equals(SortOrderEnum.ASC))
                    || (arr[pre] < tmp && order.equals(SortOrderEnum.DESC)))) {
                arr[pre + 1] = arr[pre]; // 逐一后退，注意此处pre要前置在短路与中
                pre--;
            }
            arr[pre + 1] = tmp; // 终于找到,将tmp塞入
        }
    }

    /*
     * 插入排序二分查找优化
     */
    public static void inplaceInsertionSortBinaryWay(Integer[] arr, SortOrderEnum order) {
        int len = arr.length;
        if (len <= 1) {
            return;
        }
        for (int i = 1; i < len; i++) {
            // 对于第i个数而言，需要在前[0~i-1]的有序序列中，找到一个安身之地，然后将余下数组逐一移位
            int pre = i - 1;
            int tmp = arr[i];
            // 在arr的索引0 - pre里面找tmp，返回idx
            int idx = binarySearch(arr, tmp, 0, pre, order);
            if (idx == -1) // 找不到，说明是顺序，i不用移动
                continue;
            System.arraycopy(arr, idx, arr, idx + 1, pre - idx + 1); // 找到了则需要整体移位
            arr[idx] = tmp; // 终于找到,将tmp塞入
        }
    }

    /*
     * 二分查找，查找指定区域内不大于（小于）指定tmp的值的位置，如果查不到则返回-1
     * */
    private static int binarySearch(Integer[] arr, Integer tmp, int start, int end, SortOrderEnum order) {
        if (start == end) { // 只有1个元素，则看此元素是否符合要求
            if ((arr[end] >= tmp && order.equals(SortOrderEnum.ASC))
                    || (arr[end] <= tmp && order.equals(SortOrderEnum.DESC))) {
                return end;
            } else {
                return -1;
            }
        } else if (start + 1 == end) { // 2个元素，则夹逼
            if ((arr[end] < tmp && order.equals(SortOrderEnum.ASC))
                    || (arr[end] > tmp && order.equals(SortOrderEnum.DESC))) {
                return -1;
            }
            if ((arr[start] >= tmp && order.equals(SortOrderEnum.ASC))
                    || (arr[start] <= tmp && order.equals(SortOrderEnum.DESC))) {
                return start;
            }
            return end;
        } else { // 多个元素，此时校验中值mid进行二分查找
            int mid = (start + end) >>> 1;
            if ((arr[mid] > tmp && order.equals(SortOrderEnum.ASC))
                    || (arr[mid] < tmp && order.equals(SortOrderEnum.DESC))) {
                return binarySearch(arr, tmp, start, mid, order);
            } else {
                return binarySearch(arr, tmp, mid, end, order);
            }
        }
    }

}
