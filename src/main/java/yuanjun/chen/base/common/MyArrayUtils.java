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
    
    public static MyPair<Integer> fetchMinAndMax(Integer[] arr) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int x : arr) { // 比较丑陋的选择最大值,O[n]
            if (x < min) {
                min = x;
            }
            if (x > max) {
                max = x;
            }
        }
        MyPair<Integer> res = new MyPair<>();
        res.setMax(max);
        res.setMin(min);
        return res;
    }
}
