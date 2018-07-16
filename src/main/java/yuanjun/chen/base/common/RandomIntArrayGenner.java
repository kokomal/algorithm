package yuanjun.chen.base.common;

import java.util.Random;

/*
 * 随机数组生成器
 * */
public class RandomIntArrayGenner {

    /**
     * 随机生成指定大小的int数组
     * @param size 元素个数
     * @param bound 每一个元素上界
     **/
    public static Integer[] generateRandomIntArray(int size, int bound) {
        Random seed = new Random();
        Integer[] arr = new Integer[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = seed.nextInt(bound);
        }
        return arr;
    }
}
