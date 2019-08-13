package yuanjun.chen.base.sort;

import static yuanjun.chen.base.common.CommonUtils.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import yuanjun.chen.base.common.MyArrayUtils;
import yuanjun.chen.base.common.SortOrderEnum;

/** 冒泡排序. */
public class BubbleSortAlgo {
    /** 每次从位置0开始冒泡，决定最右侧max值，每冒泡一次，则待排序的数组左移一格. */
    public static <T> void inplaceSort(Comparable<T>[] arr, SortOrderEnum order) {
        int len = arr.length;
        if (len <= 1) {
            return; // 为单值则不处理
        }
        for (int i = len - 1; i > 0; i--) { // 右值limit，每次冒泡缩进1
            for (int j = 0; j < i; j++) { // 冒泡起始点
                boolean shouldSwapAsc = SortOrderEnum.ASC.equals(order) && more(arr[j], arr[j + 1]);
                boolean shouldSwapDesc = SortOrderEnum.DESC.equals(order) && less(arr[j], arr[j + 1]);
                if (shouldSwapAsc || shouldSwapDesc) { // 符合翻转条件，仅为升序时，左值>右值，或者降序时，右值>左值
                    MyArrayUtils.swap(arr, j, j + 1);
                }
            }
        }
    }

    public static final boolean isInplaceSupported() {
        return true;
    }

    public static final boolean isProtectiveSupported() {
        return false;
    }
    
    public static void main(String[] args) {
        String cc = "10,700.00";
        NumberFormat nf = new DecimalFormat(",###.00");
        String propVal = nf.format(new BigDecimal(cc));
        System.out.println(propVal);
    }
}
