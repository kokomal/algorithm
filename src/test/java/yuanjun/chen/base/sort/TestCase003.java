/**
 * @Title: TestCase003.java
 * @Package yuanjun.chen.base.sort
 * @Description: 测试插入排序
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
 * @Description:测试插入排序
 * @author: 陈元俊
 * @date: 2018年7月17日 上午8:45:03
 */
public class TestCase003 {
    private static final Logger logger = Logger.getLogger(TestCase003.class);
    @Test
    public void testInsertionSort1() {
        int size = 256 * 256;
        int bound = 10000;
        Integer[] arr = RandomGenner.generateRandomIntArray(size, bound);
        DispUtil.embed(50, '*', "LINEAR INSERTION TEST STARTS");
        long time1 = System.currentTimeMillis();
        testInsertionSort(arr, size, bound, SortOrderEnum.DESC);
        long time2 = System.currentTimeMillis();
        DispUtil.embed(50, '*', "LINEAR INSERTION TEST ENDS..");
        logger.info("test linear insertion sort used " + (time2 - time1) + "ms");

        arr = RandomGenner.generateRandomIntArray(size, bound);
        DispUtil.embed(50, '*', "BINARY INSERTION TEST STARTS");
        long time3 = System.currentTimeMillis();
        testInsertionSortBinary(arr, size, bound, SortOrderEnum.DESC);
        long time4 = System.currentTimeMillis();
        DispUtil.embed(50, '*', "BINARY INSERTION TEST ENDS..");
        logger.info("test binary insertion sort used " + (time4 - time3) + "ms");

        arr = RandomGenner.generateRandomIntArray(size, bound);
        DispUtil.embed(50, '*', "INNER J.U.A TEST STARTS");
        long time5 = System.currentTimeMillis();
        testInnerAlgoASC(arr);
        long time6 = System.currentTimeMillis();
        DispUtil.embed(50, '*', "INNER J.U.A TEST ENDS..");
        logger.info("test inner j.u.a sort used " + (time6 - time5) + "ms");
    }

    public void testInsertionSort(Integer[] arr, int size, int bound, SortOrderEnum order) {
        logger.info("before " + order + " inplace insertion sort---" + Arrays.toString(arr));
        InsertionSortAlgo.inplaceInsertionSort(arr, order);
        logger.info("after " + order + " inplace insertion sort---" + Arrays.toString(arr));
    }

    public void testInsertionSortBinary(Integer[] arr, int size, int bound, SortOrderEnum order) {
        logger.info("before " + order + " inplace binary insertion sort---" + Arrays.toString(arr));
        InsertionSortAlgo.inplaceInsertionSortBinaryWay(arr, order);
        logger.info("after " + order + " inplace binary insertion sort---" + Arrays.toString(arr));
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
