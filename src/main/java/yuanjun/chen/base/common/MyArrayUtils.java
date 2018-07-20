package yuanjun.chen.base.common;

public class MyArrayUtils {
    /**
     * 交换int[]的元素
     * @param int[] arr 原始数组
     * @param int idxA 待交换的index
     * @param int idxB 另一个待交换的index
     */
    public static <T> void swap(T[] arr, int idxA, int idxB) {
        if (idxA != idxB) { // 相等就不要操心了
            T temp = arr[idxA];
            arr[idxA] = arr[idxB];
            arr[idxB] = temp;
        }
    }
}
