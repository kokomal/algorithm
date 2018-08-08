/**
 * @Title: QuickSortTest.java
 * @Package: yuanjun.chen.base.sort
 * @Description: 快速排序测试
 * @author: 陈元俊
 * @date: 2018年7月19日 上午11:20:23
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.sort;

import org.junit.Test;
import yuanjun.chen.base.common.GenericAlgoTester;
import yuanjun.chen.base.common.SortOrderEnum;

/**
 * @ClassName: QuickSortTest
 * @Description: 快速排序测试
 * @author: 陈元俊
 * @date: 2018年7月19日 上午11:20:23
 */
public class QuickSortTest {
    @Test
    public void testQuickSort001() throws Exception {
        testShellSortProto_v1(10000, 8000, SortOrderEnum.ASC, Integer.class);
    }

    @Test
    public void testQuickSort002() throws Exception {
        testShellSortProto_v1(20000, 8000, SortOrderEnum.ASC, Integer.class);
    }

    @Test
    public void testQuickSort003() throws Exception {
        testShellSortProto_v1(30000, 8000, SortOrderEnum.DESC, Integer.class);
    }

    @Test
    public void testQuickSort004() throws Exception {
        testShellSortProto_v1(40000, 8000, SortOrderEnum.DESC, Integer.class);
    }

    @Test
    public void testQuickSort005() throws Exception {
        testShellSortProto_v1(10000, 8000, SortOrderEnum.ASC, Double.class);
    }

    @Test
    public void testQuickSort006() throws Exception {
        testShellSortProto_v1(20000, 8000, SortOrderEnum.ASC, Double.class);
    }

    @Test
    public void testQuickSort007() throws Exception {
        testShellSortProto_v1(30000, 8000, SortOrderEnum.DESC, Double.class);
    }

    @Test
    public void testQuickSort008() throws Exception {
        testShellSortProto_v1(40000, 8000, SortOrderEnum.DESC, Double.class);
    }

    /**
     * @Title testBucketSortProto
     * @Description 原始测试类
     * @param size
     * @param bound
     * @param order
     * @throws Exception
     * @return void
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void testShellSortProto_v1(int size, int bound, SortOrderEnum order, Class clazz) throws Exception {
        new GenericAlgoTester("QUICK") {
            @Override
            public void showTime(Comparable[] arr, SortOrderEnum order) {
                QuickSortAlgo.quickSort_v1(arr);
            }
        }.genericTest(size, bound, order, clazz);
    }
}
