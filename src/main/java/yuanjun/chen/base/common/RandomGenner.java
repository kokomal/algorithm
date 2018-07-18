package yuanjun.chen.base.common;

import java.util.Random;

/*
 * 随机数组和矩阵生成器
 * */
public class RandomGenner {

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
    
    /**
     * 随机生成指定大小的double数组
     * @param size 元素个数
     * @param bound 每一个元素上界
     **/
    public static Double[] generateRandomDoubleArray(int size, int bound) {
        Random seed = new Random();
        Double[] arr = new Double[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = seed.nextDouble() * bound;
        }
        return arr;
    }
    
    /**
     * 随机生成m*n维的Integer矩阵
     **/
    public static Integer[][] generateRandomIntMatrix(int m, int n, int bound) {
        Random seed = new Random();
        Integer[][] matrix = new Integer[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = seed.nextInt(bound);
            }
        }
        return matrix;
    }
    
    /**
     * 随机生成m*n维的Double矩阵
     **/
    public static Double[][] generateRandomDoubleMatrix(int m, int n, int bound) {
        Random seed = new Random();
        Double[][] matrix = new Double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = seed.nextDouble() * bound;
            }
        }
        return matrix;
    }
}
