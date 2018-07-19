/**
 * @Title: TestCase003.java
 * @Package yuanjun.chen.base.sort
 * @Description: 测试归并排序
 * @author: 陈元俊
 * @date: 2018年7月17日 上午8:45:03
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.sort;

import java.util.Arrays;
import org.apache.log4j.Logger;
import org.junit.Test;
import yuanjun.chen.base.common.DispUtil;
import yuanjun.chen.base.common.RandomGenner;
import yuanjun.chen.base.common.SortOrderEnum;

/**
 * @ClassName: TestCase003
 * @Description:测试归并排序
 * @author: 陈元俊
 * @date: 2018年7月17日 上午8:45:03
 */
public class TestCase004 {
    private static final Logger logger = Logger.getLogger(TestCase004.class);
    @Test
    public void testMergeSort1() {
        int size = 256 * 256 * 4;
        int bound = 10000;
        Integer[] arr = RandomGenner.generateRandomIntArray(size, bound);
        DispUtil.embed(50, '*', "MERGE SORT TEST STARTS");
        long time1 = System.currentTimeMillis();
        testMergeSort(arr, size, bound, SortOrderEnum.DESC);
        long time2 = System.currentTimeMillis();
        DispUtil.embed(50, '*', "MERGE SORT TEST ENDS..");
        logger.info("test meger sort used " + (time2 - time1) + "ms");

        arr = RandomGenner.generateRandomIntArray(size, bound);
        DispUtil.embed(50, '*', "INNER J.U.A TEST STARTS");
        long time5 = System.currentTimeMillis();
        testInnerAlgoASC(arr);
        long time6 = System.currentTimeMillis();
        DispUtil.embed(50, '*', "INNER J.U.A TEST ENDS..");
        logger.info("test inner j.u.a sort used " + (time6 - time5) + "ms");
    }

    public void testMergeSort(Integer[] arr, int size, int bound, SortOrderEnum order) {
        logger.info("before " + order + " merge sort---" + Arrays.toString(arr));
        Integer[] res = MergeSortAlgo.mergeSort(arr, order);
        logger.info("after " + order + " merge sort---" + Arrays.toString(res));
    }

    /*
     * 用j.u.a的内置collections的顺序排序算法
     * */
    public void testInnerAlgoASC(Integer[] arr) {
        logger.info("before " + "inner sort---" + Arrays.toString(arr));
        Arrays.sort(arr);
        logger.info("after " + "inner sort---" + Arrays.toString(arr));
    }
}
