/**
 * @Title: SelectionSortAlgo.java
 * @Package: yuanjun.chen.base.sort
 * @Description: 选择排序
 * @author: 陈元俊
 * @date: 2018年7月18日 下午5:22:25
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.sort;

import yuanjun.chen.base.common.MyArrayUtils;
import yuanjun.chen.base.common.SortOrderEnum;

/**
 * @ClassName: SelectionSortAlgo
 * @Description: 选择排序
 * @author: 陈元俊
 * @date: 2018年7月18日 下午5:22:25
 */
public class SelectionSortAlgo {
    /*
     * 原地选择排序
     */
    public static void inplaceSelectionSort(Integer[] arr, SortOrderEnum order) {
        int len = arr.length;
        if (len <= 1) {
            return;
        }
        for (int i = 0; i < len - 1; i++) {
            int max = arr[i]; // 最值默认为第i个
            int rec = i;
            for (int j = i + 1; j < len; j++) { // 从i+1后的所有值里面选出最值，如果比i位置的大，则进行交换，然后步进
                boolean shouldUpdate = SortOrderEnum.ASC.equals(order) && arr[j] < max;
                shouldUpdate = shouldUpdate || (SortOrderEnum.DESC.equals(order) && arr[j] > max);
                if (shouldUpdate) { // 如果遇到比当前值更大，置标志位，并且更新max值
                    max = arr[j];
                    rec = j;
                }
            }
            if (rec != i) {
                MyArrayUtils.swap(arr, i, rec); // 交换最值，确保i为当前最值
            }
        }
    }

}
