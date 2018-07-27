/**
 * @Title: ShellSortAlgo.java
 * @Package: yuanjun.chen.base.sort
 * @Description: 希尔排序
 * @author: 陈元俊
 * @date: 2018年7月24日 下午3:57:36
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 **/
package yuanjun.chen.base.sort;

import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import yuanjun.chen.base.common.CommonUtils;
import yuanjun.chen.base.common.MyArrayUtils;
import yuanjun.chen.base.common.RandomGenner;
import yuanjun.chen.base.common.SortOrderEnum;

/**
 * @ClassName: ShellSortAlgo
 * @Description: 希尔排序
 * @author: 陈元俊
 * @date: 2018年7月24日 下午3:57:36
 **/
public class ShellSortAlgo {
    
    private static final Logger logger = LogManager.getLogger(ShellSortAlgo.class);
    /**
     * Knuth法优化的shell排序
     * 
     **/
    public static <T> void inplaceShellSortKnuthWay(Comparable<T>[] arr, SortOrderEnum order) {
        int len = arr.length;
        if (len <= 1) {
            return;
        }
        int h = getHByKnuthWay(len);
        logger.info("len = " + len + " ,with step h = " + h);
        /**
         * [0] [1] [2] [3] [4] [5] --- [h] [h+1] --- [2h][2h+1] --- [3h][3h+1]
         * |____________________________|_____________|______________| 
         * j-n*h                        j-h           j
         **/
        while (h >= 1) {
            for (int i = h; i < len; i++) {
                for (int j = i; j >= h && ((CommonUtils.less(arr[j], arr[j - h]) && SortOrderEnum.ASC.equals(order))
                        || (CommonUtils.more(arr[j], arr[j - h]) && SortOrderEnum.DESC.equals(order))); j -= h) { // j-h要有意义，则j>=h
                    MyArrayUtils.swap(arr, j, j - h); // h=1则退化成插入排序
                }
            }
            h /= 3;
        }
    }

    /**
     * getHByKnuthWay 通过辗转*3法获得len下适合的h步长
     * 
     * @param: len
     **/
    private static int getHByKnuthWay(int len) {
        int h = 1;
        while (h < len / 3) { // 取得最大的h,使得遵循公式(3^h-1)/2或[1,4,14,40,121,…], 从len/3开始衰减到1
            h = 3 * h + 1;
        }
        return h;
    }
    
    public static void main(String[] args) throws Exception {
        Integer[] arr = new Integer[] {33, 23, 12, 3, 22, 12};
        inplaceShellSortKnuthWay(arr, SortOrderEnum.ASC);
        System.out.println(Arrays.toString(arr));
        int size = 16000;
        int bound = 4000;
        Integer[] arr2 = RandomGenner.generateRandomTArray(size, bound, Integer.class);
        inplaceShellSortKnuthWay(arr2, SortOrderEnum.DESC);
        System.out.println(Arrays.toString(arr2));
    }
}
