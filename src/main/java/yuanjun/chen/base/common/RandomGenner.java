package yuanjun.chen.base.common;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import yuanjun.chen.base.exception.UnsupportedTypeException;

/*
 * 随机数组和矩阵生成器
 */
public class RandomGenner {

    private static final Set<Class<? extends Comparable<?>>> supportedTypes = new HashSet<>();
    static {
        supportedTypes.add(Integer.class);
        supportedTypes.add(Double.class);
        supportedTypes.add(BigDecimal.class);
        supportedTypes.add(Float.class);
    }

    /**
     * 随机生成指定大小的int数组
     * 
     * @param size 元素个数
     * @param bound 每一个元素上界
     **/
    @Deprecated
    public static Integer[] generateRandomIntArray(int size, int bound) {
        Random seed = new Random();
        Integer[] arr = new Integer[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = seed.nextInt(bound);
        }
        return arr;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Comparable<?>> T[] generateRandomTArray(int size, int bound, Class<T> clazz)
            throws Exception {
        if (!supportedTypes.contains(clazz)) {
            throw new UnsupportedTypeException("class name " + clazz.getName() + " not supported!");
        }
        Random seed = new Random();
        if (clazz.equals(Integer.class)) {
            Integer intarr[] = new Integer[size];
            for (int i = 0; i < size; i++) {
                intarr[i] = seed.nextInt(bound);
            }
            return (T[]) intarr;
        } else if (clazz.equals(Double.class)) {
            Double[] dbarr = new Double[size];
            for (int i = 0; i < size; i++) {
                dbarr[i] = seed.nextDouble() * bound;
            }
            return (T[]) dbarr;
        } else if (clazz.equals(BigDecimal.class)) {
            BigDecimal intarr[] = new BigDecimal[size];
            for (int i = 0; i < size; i++) {
                intarr[i] = new BigDecimal(seed.nextDouble() * bound);
            }
            return (T[]) intarr;
        } else if (clazz.equals(Float.class)) {
            Float intarr[] = new Float[size];
            for (int i = 0; i < size; i++) {
                intarr[i] = seed.nextFloat() * bound;
            }
            return (T[]) intarr;
        } else {
            throw new UnsupportedTypeException("class name " + clazz.getName() + " not supported!");
        }
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

    public static void main(String[] args) throws Exception {
        Integer[] ii = generateRandomTArray(19, 85, Integer.class);
        System.out.println(Arrays.toString(ii));

        Float[] ff = generateRandomTArray(19, 85, Float.class);
        System.out.println(Arrays.toString(ff));

        Double[] dd = generateRandomTArray(19, 85, Double.class);
        System.out.println(Arrays.toString(dd));

        BigDecimal[] bb = generateRandomTArray(19, 85, BigDecimal.class);
        System.out.println(Arrays.toString(bb));
    }
}
