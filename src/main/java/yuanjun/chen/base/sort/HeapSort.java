package yuanjun.chen.base.sort;

import java.util.Arrays;
import yuanjun.chen.base.common.RandomIntArrayGenner;
import yuanjun.chen.base.common.SORT_ORDER;

/**
 * 堆排序demo
 */
public class HeapSort {
    /*
     * 采用递归max-heapify算法进行堆整理
     * 缺点是在大数据时会导致栈溢出
     * */
    public static void recursiveHeapsort(int[] arr, SORT_ORDER order) {
        // 1.BUILD-MAX-HEAP
        for (int i = arr.length / 2; i >= 0; i--) {
            recursiveMaxHeapify(arr, i, arr.length, order);
        }
        System.out.println("After MaxHeapify---" + Arrays.toString(arr));
        // 2.调整堆结构+交换堆顶元素与末尾元素
        for (int j = arr.length - 1; j > 0; j--) {
            swap(arr, 0, j);// 将堆顶元素与末尾元素进行交换
            recursiveMaxHeapify(arr, 0, j, order);// 重新对堆进行调整
        }
    }

    /**
     * @param int[] arr 原始数组
     * @param int i maxheapify操作数组的开始序号
     * @param int length 数组影响范围
     * @param SORT_ORDER order 排序方向
     */
    private static void recursiveMaxHeapify(int[] arr, int i, int length, SORT_ORDER order) {
        int maxPos = i;
        int left = 2 * i + 1;
        int right = left + 1;
        if (left < length && arr[left] > arr[i]) { // 左大
            maxPos = left;
        }
        if (right < length && arr[right] > arr[maxPos]) { // 右大
            maxPos = right;
        }
        if (maxPos != i) {
            swap(arr, i, maxPos);
            recursiveMaxHeapify(arr, maxPos, length, order);
        }
    }

    /**
     * 调整大顶堆（仅是调整过程，建立在大顶堆已构建的基础上） MAX-HEAPIFY
     * 
     * @param int[] arr 原始数组
     * @param int i maxheapify操作数组的开始序号
     * @param int length 数组影响范围
     */
    public static void nonRecursiveMaxHeapify(int[] arr, int i, int length) {
        int temp = arr[i]; // 先取出当前元素i
        for (int k = i * 2 + 1; k < length; k = k * 2 + 1) { // 从i结点的左子结点开始，也就是2i+1处开始
            if (k + 1 < length && arr[k] < arr[k + 1]) { // 如果左子结点小于右子结点，k指向右子结点
                k++;
            }
            if (arr[k] > temp) { // 如果子节点大于父节点，将子节点值赋给父节点（不用进行交换）
                arr[i] = arr[k];
                i = k;
            } else {
                break;
            }
        }
        arr[i] = temp;// 将temp值放到最终的位置
    }

    /**
     * 交换int[]的元素
     * 
     * @param int[] arr 原始数组
     * @param int a 待交换的index
     * @param int b 另一个待交换的index
     */
    public static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    public void testHeapSort(int size, int bound, SORT_ORDER order) {
        int[] arr = RandomIntArrayGenner.generateRandomIntArray(size, bound);
        System.out.println("before heap sort---" + Arrays.toString(arr));
        recursiveHeapsort(arr, SORT_ORDER.ASC);
        System.out.println("after heap sort---" + Arrays.toString(arr));
    }
    
    public static void main(String[] args) {
        
    }

}
