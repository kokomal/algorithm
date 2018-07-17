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
import org.junit.Test;
import yuanjun.chen.base.common.DispUtil;
import yuanjun.chen.base.common.RandomIntArrayGenner;
import yuanjun.chen.base.common.SortOrderEnum;

/**
 * @ClassName: TestCase003
 * @Description:测试归并排序
 * @author: 陈元俊
 * @date: 2018年7月17日 上午8:45:03
 */
public class TestCase004 {
    @Test
    public void testMergeSort1() {
        int size = 256 * 256;
        int bound = 10000;

        Integer[] arr = RandomIntArrayGenner.generateRandomIntArray(size, bound);
        DispUtil.embed(50, '*', "MERGE SORT TEST STARTS");
        long time1 = System.currentTimeMillis();
        testMergeSort(arr, size, bound, SortOrderEnum.DESC);
        long time2 = System.currentTimeMillis();
        DispUtil.embed(50, '*', "MERGE SORT TEST ENDS..");
        System.out.println("test meger sort used " + (time2 - time1) + "ms");

        arr = RandomIntArrayGenner.generateRandomIntArray(size, bound);
        DispUtil.embed(50, '*', "INNER J.U.A TEST STARTS");
        long time5 = System.currentTimeMillis();
        testInnerAlgoASC(arr, size, bound);
        long time6 = System.currentTimeMillis();
        DispUtil.embed(50, '*', "INNER J.U.A TEST ENDS..");
        System.out.println("test inner j.u.a sort used " + (time6 - time5) + "ms");
    }

    public void testMergeSort(Integer[] arr, int size, int bound, SortOrderEnum order) {
        System.out.println("before " + order + " merge sort---" + Arrays.toString(arr));
        Integer[] res = MergeSortAlgo.mergeSort(arr, order);
        System.out.println("after " + order + " merge sort---" + Arrays.toString(res));
    }

    /*
     * 用j.u.a的内置collections的顺序排序算法
     * */
    public void testInnerAlgoASC(Integer[] arr, int size, int bound) {
        System.out.println("before " + "inner sort---" + Arrays.toString(arr));
        Arrays.sort(arr);
        System.out.println("after " + "inner sort---" + Arrays.toString(arr));
    }
}
