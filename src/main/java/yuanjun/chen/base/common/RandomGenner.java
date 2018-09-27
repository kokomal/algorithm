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

    public static String[] generateRandomStrings(int N, int baseLenPerString) {
        String[] res = new String[N];
        for (int i = 0; i < N; i++) {
            res[i] = generateString(baseLenPerString);
        }
        return res;
    }
    
    /** 藏语经书生成器*/
    public static String[] generateRandomTibetan(int N, int baseLenPerString) {
        String[] res = new String[N];
        for (int i = 0; i < N; i++) {
            res[i] = generateTibetanString(baseLenPerString);
        }
        return res;
    }

    private static final int[] TIBETANSOURCES =
            new int[] {3954, 3956, 3962, 3964, 3906, 3908, 3909, 3910, 3911, 3913, 3919, 3920, 3921, 3923, 3924, 3925,
                    3926, 3928, 3929, 3930, 3931, 3933, 3934, 3935, 3936, 3937, 3938, 3939, 3940, 3942, 3943, 3934

            };
    private static String generateTibetanString(int baseLenPerString) {
        Random random = new Random();
        int size = random.nextInt(baseLenPerString) + 1;
        char[] text = new char[size];
        for (int i = 0; i < size; i++) {
            text[i] = (char) TIBETANSOURCES[random.nextInt(TIBETANSOURCES.length)]; // 藏语辅音从3904到3944，元音为3954，3956，3962，3964
        }
        return new String(text);
    }

    private static final String SOURCES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

    public static String generateString(int length) {
        Random random = new Random();
        int size = random.nextInt(length) + 1;
        char[] text = new char[size];
        for (int i = 0; i < size; i++) {
            text[i] = SOURCES.charAt(random.nextInt(SOURCES.length()));
        }
        return new String(text);
    }

    public static Double[][] generateRandomUnifiedMatrix(int m, int n) {
        Double[][] matrix = generateRandomDoubleMatrix(m, n, 100);
        // 需要对纵列进行均一化
        for (int i = 0; i < n; i++) { // 遍历每一个纵列
            Double sum = 0.0;
            for (int j = 0; j < m; j++) { // 再遍历每一个value
                sum += matrix[j][i];
            }
            for (int j = 0; j < m; j++) { // 再遍历每一个value
                matrix[j][i] = matrix[j][i] / sum;
            }
        }
        return matrix;
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

        System.out.println(Arrays.toString(generateRandomStrings(100, 20)));
        
        System.out.println(Arrays.toString(generateRandomTibetan(100, 20)));
    }
}
