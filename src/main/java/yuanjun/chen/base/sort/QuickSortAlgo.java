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

import java.util.Arrays;
import java.util.Random;
import yuanjun.chen.base.common.MyArrayUtils;
import yuanjun.chen.base.common.RandomGenner;
import static yuanjun.chen.base.common.CommonUtils.*;

/**
 * @ClassName: QuickSortAlgo
 * @Description: 快速排序算法
 * TODO sortOrderEnum
 * @author: 陈元俊
 * @date: 2018年7月19日 上午8:46:11
 */
public class QuickSortAlgo {

    /**
     * 原始快速排序版本，pivot的选取每次均选择arr[r]
     **/
    @SuppressWarnings("rawtypes")
    public static void quickSort_v1(Comparable[] arr) {
        quickSortRaw(arr, 0, arr.length - 1);
    }

    /**
     * pivot的选取每次均选择[r,p]直接的随机值
     **/
    @SuppressWarnings("rawtypes")
    public static void quickSort_v2(Comparable[] arr) {
        quickSortRandom(arr, 0, arr.length - 1);
    }
    
    /**
     * pivot的选取每次均选择[r,p]直接的随机值
     * 采用while循环避免了一半的递归深度
     **/
    @SuppressWarnings("rawtypes")
    public static void quickSort_v3(Comparable[] arr) {
        quickSortRandomWithLoop(arr, 0, arr.length - 1);
    }
    
    /*
     * Random快速排序内部实现2
     * 带有while循环，免了一个递归
     */
    @SuppressWarnings("rawtypes")
    private static void quickSortRandomWithLoop(Comparable[] arr, int p, int r) {
        while (p <= r) {
            int pivot = randomizedPartition(arr, p, r);
            quickSortRandom(arr, p, pivot - 1);
            p = pivot + 1;
        }
    }
    
    /*
     * Random快速排序内部实现
     */
    @SuppressWarnings("rawtypes")
    private static void quickSortRandom(Comparable[] arr, int p, int r) {
        if (p > r) return;
        int pivot = randomizedPartition(arr, p, r);
        quickSortRandom(arr, p, pivot - 1);
        quickSortRandom(arr, pivot + 1, r);
    }
    
    
    /**   
     * @Title: findRandomPivot   
     * @Description: 在p到r之间选择随机值替换r  
     * @param: @param arr
     * @param: @param p
     * @param: @param r
     * @return: int      
     * @throws   
     */
    @SuppressWarnings("rawtypes")
    public static int randomizedPartition(Comparable[] arr, int p, int r) {
        if (r == p) return p;
        Random rd = new Random();
        int randomPivot = rd.nextInt(r - p + 1) + p;
        MyArrayUtils.swap(arr, randomPivot, r);
        return partition(arr, p, r);
    }


    /*
     * 经典快速排序内部实现
     */
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
    private static int partition(Comparable[] arr, int p, int r) {
        if (p == r) return p;
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
    
    public static void main(String[] args) throws Exception {
        int size = 200; // 26万条数据
        int bound = 4000;
        Integer[] arr = RandomGenner.generateRandomTArray(size, bound, Integer.class);
        Integer[] arr2 = new Integer[size];
        System.arraycopy(arr, 0, arr2, 0, size);
        System.out.println("before " + Arrays.toString(arr));
        quickSort_v2(arr);
        System.out.println("after " + Arrays.toString(arr));
    }

}
