package yuanjun.chen.base.sort;

import static yuanjun.chen.base.common.CommonUtils.*;
import yuanjun.chen.base.common.MyArrayUtils;
import yuanjun.chen.base.common.SortOrderEnum;

/**
 * @ClassName: HeapSortAlgo
 * @Description: 堆排序算法类
 * @author: 陈元俊
 * @date: 2018年7月20日 上午11:29:57
 */
public class HeapSortAlgo {
    /** 采用递归max-heapify算法进行堆整理 缺点是在大数据时会导致栈溢出. */
    @SuppressWarnings("rawtypes")
    public static void inplaceHeapSort(Comparable[] arr, SortOrderEnum order, boolean recurFlag) {
        if (arr.length <= 1) {
            return;
        }
        buildMaxHeap(arr, order, arr.length);
        // 2.调整堆结构+交换堆顶元素与末尾元素
        for (int j = arr.length - 1; j > 0; j--) {
            MyArrayUtils.swap(arr, 0, j);// 将堆顶元素与末尾元素进行交换
            maxheapify(arr, order, recurFlag, 0, j);
        }
    }

    @SuppressWarnings("rawtypes")
    public static void buildMaxHeap(Comparable[] arr, SortOrderEnum order, int lenX) {
        boolean recurFlag = lenX <= 10000;
        // 1.BUILD-MAX-HEAP
        for (int i = lenX >>> 1; i >= 0; i--) {
            maxheapify(arr, order, recurFlag, i, lenX);
        }
    }

    @SuppressWarnings("rawtypes")
    public static void maxheapify(Comparable[] arr, SortOrderEnum order, boolean recurFlag, int i, int length) {
        if (recurFlag) {
            recursiveMaxHeapify(arr, i, length, order);// 重新对堆进行调整
        } else {
            nonRecursiveMaxHeapify(arr, i, length, order);// 重新对堆进行调整
        }
    }
    
    /**
     * @param int[] arr 原始数组
     * @param int i maxheapify操作数组的开始序号
     * @param int length 数组影响范围
     * @param SortOrderEnum order 排序方向
     */
    @SuppressWarnings({"rawtypes"})
    private static void recursiveMaxHeapify(Comparable[] arr, int i, int length, SortOrderEnum order) {
        int maxPos = i;
        int left = 2 * i + 1;
        int right = left + 1;
        boolean leftMoveAsc = left < length && more(arr[left], arr[i]) && SortOrderEnum.ASC.equals(order);
        boolean leftMoveDesc = left < length && less(arr[left], arr[i]) && SortOrderEnum.DESC.equals(order);
        if (leftMoveAsc || leftMoveDesc) { // 左大
            maxPos = left;
        }
        boolean rightMoveAsc = right < length && more(arr[right], arr[maxPos]) && SortOrderEnum.ASC.equals(order);
        boolean rightMoveDesc = right < length && less(arr[right], arr[maxPos]) && SortOrderEnum.DESC.equals(order);
        if (rightMoveAsc || rightMoveDesc) { // 右大
            maxPos = right;
        }
        if (maxPos != i) {
            MyArrayUtils.swap(arr, i, maxPos);
            recursiveMaxHeapify(arr, maxPos, length, order);
        }
    }

    /**
     * 调整大顶堆（仅是调整过程，建立在大顶堆已构建的基础上） MAX-HEAPIFY.
     * 
     * @param int[] arr 原始数组
     * @param int i maxheapify操作数组的开始序号
     * @param int length 数组影响范围
     */
    @SuppressWarnings({"rawtypes"})
    public static void nonRecursiveMaxHeapify(Comparable[] arr, int i, int length, SortOrderEnum order) {
        Comparable temp = arr[i]; // 先取出当前元素i
        for (int k = i * 2 + 1; k < length; k = k * 2 + 1) { // 从i结点的左子结点开始，也就是2i+1处开始
            boolean nextKAsc = k + 1 < length && less(arr[k], arr[k + 1]) && SortOrderEnum.ASC.equals(order);
            boolean nextKDesc = k + 1 < length && more(arr[k], arr[k + 1]) && SortOrderEnum.DESC.equals(order);
            if (nextKAsc || nextKDesc) { // 如果左子结点小于右子结点，k指向右子结点
                k++;
            }
            if ((more(arr[k], temp) && SortOrderEnum.ASC.equals(order))
                    || (less(arr[k], temp) && SortOrderEnum.DESC.equals(order))) { // 如果子节点大于父节点，将子节点值赋给父节点（不用进行交换）
                arr[i] = arr[k];
                i = k;
            } else {
                break;
            }
        }
        arr[i] = temp;// 将temp值放到最终的位置
    }
}
