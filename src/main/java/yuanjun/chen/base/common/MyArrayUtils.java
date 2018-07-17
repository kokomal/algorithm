package yuanjun.chen.base.common;

public class MyArrayUtils {
    /**
     * 交换int[]的元素
     * @param int[] arr 原始数组
     * @param int a 待交换的index
     * @param int b 另一个待交换的index
     */
    public static <T> void swap(T[] arr, int a, int b) {
        T temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}
