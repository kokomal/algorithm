package yuanjun.chen.base.common;
import static yuanjun.chen.base.common.CommonUtils.*;
public class MyArrayUtils {
    /**
     * 交换int[]的元素.
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
        public static <T extends Comparable<T>> int findIndex(T[] arr, T val) {
        if (arr.length == 0) {
            return -1;
        }
        for (int i = 0; i < arr.length; i++) {
        	if (eq(arr[i], val)) {
        		return i;
        	}
        }
        return -1;
    }
        
    public static <T extends Comparable<T>> MyPair<T> fetchMinAndMax(T[] arr) {
        if (arr.length == 0) {
            return null;
        }
                T min = arr[0];
        T max = arr[0];
        for (T x : arr) { // 比较丑陋的选择最大值,O[n]
            if (less(x, min)) {
                min = x;
            }
            if (more(x, max)) {
                max = x;
            }
        }
        MyPair<T> res = new MyPair<>();
        res.setMax(max);
        res.setMin(min);
        return res;
    }
}
