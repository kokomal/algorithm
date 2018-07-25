package yuanjun.chen.base.sort;

import java.util.Arrays;
import java.util.Collections;
import org.apache.log4j.Logger;
import org.junit.Test;
import yuanjun.chen.base.common.DispUtil;
import yuanjun.chen.base.common.RandomGenner;
import yuanjun.chen.base.common.SortOrderEnum;

/*
 * 堆排序测试
 */
public class HeapSortTest {
    private static final Logger logger = Logger.getLogger(HeapSortTest.class);
    @Test
    public void testHeap() throws Exception {
        int size = 16;
        int bound = 100;
        Integer[] arr = RandomGenner.generateRandomTArray(size, bound, Integer.class);
        DispUtil.embed(50, '*', "HEAP TEST STARTS");
        testHeapSort(arr, size, bound, SortOrderEnum.ASC, true);
        DispUtil.split(50 + size * 4, '=');
        testHeapSort(arr, size, bound, SortOrderEnum.DESC, true);
        DispUtil.split(50 + size * 4, '=');
        testHeapSort(arr, size, bound, SortOrderEnum.ASC, false);
        DispUtil.split(50 + size * 4, '=');
        testHeapSort(arr, size, bound, SortOrderEnum.DESC, false);
        DispUtil.embed(50, '*', "HEAP TEST ENDS..");
    }

    @Test
    public void testHeapHeavy() throws Exception {
        int size = 16 * 16 * 16 * 16 * 2; // 13万条数据
        int bound = 500000;
        DispUtil.embed(50, '*', "HEAP TEST STARTS");
        Integer[] arr = RandomGenner.generateRandomTArray(size, bound, Integer.class);
        long time1 = System.currentTimeMillis();
        testHeapSort(arr, size, bound, SortOrderEnum.ASC, true);
        DispUtil.split(500, '=');
        long time2 = System.currentTimeMillis();
        logger.info("recursive time = " + (time2 - time1) + "ms");
        
        arr = RandomGenner.generateRandomTArray(size, bound, Integer.class);
        testHeapSort(arr, size, bound, SortOrderEnum.ASC, false);
        long time3 = System.currentTimeMillis();
        DispUtil.split(500, '=');
        logger.info("non-recursive time = " + (time3 - time2) + "ms");

        arr = RandomGenner.generateRandomTArray(size, bound, Integer.class);
        testInnerAlgoASC(arr);
        long time4 = System.currentTimeMillis();
        logger.info("inner ASC algo time = " + (time4 - time3) + "ms");
        DispUtil.split(500, '=');
        arr = RandomGenner.generateRandomTArray(size, bound, Integer.class);
        testInnerAlgoDESC(arr);
        long time5 = System.currentTimeMillis();
        logger.info("inner DESC algo time = " + (time5 - time4) + "ms");
        DispUtil.embed(50, '*', "HEAP TEST ENDS..");
    }

    @SuppressWarnings("rawtypes")
    public void testHeapSort(Comparable[] arr, int size, int bound, SortOrderEnum order, boolean recurFlag) {
        logger.info("before " + order + (recurFlag ? " recursive" : " non-recusive") + " inplace heap sort---"
                + Arrays.toString(arr));
        HeapSortAlgo.inplaceHeapSort(arr, order, recurFlag);
        logger.info("after " + order + (recurFlag ? " recursive" : " non-recusive") + " inplace heap sort---"
                + Arrays.toString(arr));
    }

    /*
     * 用j.u.a的内置collections的顺序排序算法
     * */
    @SuppressWarnings("rawtypes")
    public void testInnerAlgoASC(Comparable[] arr) {
        logger.info("before " + "inner sort---" + Arrays.toString(arr));
        Arrays.sort(arr);
        logger.info("after " + "inner sort---" + Arrays.toString(arr));
    }

    /*
     * 用j.u.a的内置collections的逆序排序算法
     * */
    @SuppressWarnings("rawtypes")
    public void testInnerAlgoDESC(Comparable[] arr) {
        logger.info("before " + "inner sort---" + Arrays.toString(arr));
        Arrays.sort(arr, Collections.reverseOrder());
        logger.info("after " + "inner sort---" + Arrays.toString(arr));
    }
}
