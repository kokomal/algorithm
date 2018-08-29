package yuanjun.chen.base.common;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import yuanjun.chen.base.exception.UnsupportedTypeException;

/** 随机数组和矩阵生成器. */
public class RandomGenner {
    private static final Set<Class<? extends Comparable<?>>> supportedTypes = new HashSet<>();
    static {
        supportedTypes.add(Integer.class);
        supportedTypes.add(Double.class);
        supportedTypes.add(BigDecimal.class);
        supportedTypes.add(Float.class);
    }

    private static final char[] DNA_SERIES = {'A', 'T', 'G', 'C'};
    /**
     * 随机生成指定大小的int数组.
     * 
     * @param size 元素个数
     * @param bound 每一个元素上界
     */
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
    public static <T extends Object> T[] generateRandomTArray(int size, int lowbound, int upbound, Class<T> clazz)
            throws Exception {
        if (!supportedTypes.contains(clazz)) {
            throw new UnsupportedTypeException("class name " + clazz.getName() + " not supported!");
        }
        Random seed = new Random();
        if (clazz.equals(Integer.class)) {
            Integer intarr[] = new Integer[size];
            for (int i = 0; i < size; i++) {
                intarr[i] = lowbound + seed.nextInt(upbound - lowbound);
            }
            return (T[]) intarr;
        } else if (clazz.equals(Double.class)) {
            Double[] dbarr = new Double[size];
            for (int i = 0; i < size; i++) {
                dbarr[i] = lowbound + seed.nextDouble() * (upbound - lowbound);
            }
            return (T[]) dbarr;
        } else if (clazz.equals(BigDecimal.class)) {
            BigDecimal bdArr[] = new BigDecimal[size];
            for (int i = 0; i < size; i++) {
                bdArr[i] = new BigDecimal(lowbound + seed.nextDouble() * (upbound - lowbound));
            }
            return (T[]) bdArr;
        } else if (clazz.equals(Float.class)) {
            Float flArr[] = new Float[size];
            for (int i = 0; i < size; i++) {
                flArr[i] = lowbound + seed.nextFloat() * (upbound - lowbound);
            }
            return (T[]) flArr;
        } else {
            throw new UnsupportedTypeException("class name " + clazz.getName() + " not supported!");
        }
    }
    
    /** 随机生成m*n维的Integer矩阵. */
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
    
    public static Long[][] generateRandomLongMatrix(int m, int n, long bound) {
        Random seed = new Random();
        Long[][] matrix = new Long[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = (long) (seed.nextFloat() * bound);
            }
        }
        return matrix;
    }

    /** 随机生成m*n维的Double矩阵. */
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

    // ATGC random
    public static String generateDNASeries(int len) {
        Random seed = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(DNA_SERIES[seed.nextInt(DNA_SERIES.length)]);
        }
        return sb.toString();
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println(generateDNASeries(20));
        
        Integer[] ii = generateRandomTArray(19, 0, 85, Integer.class);
        System.out.println(Arrays.toString(ii));

        Float[] ff = generateRandomTArray(19, 0, 85, Float.class);
        System.out.println(Arrays.toString(ff));

        Double[] dd = generateRandomTArray(19, 0, 85, Double.class);
        System.out.println(Arrays.toString(dd));

        BigDecimal[] bb = generateRandomTArray(19, 0, 85, BigDecimal.class);
        System.out.println(Arrays.toString(bb));
    }
}
