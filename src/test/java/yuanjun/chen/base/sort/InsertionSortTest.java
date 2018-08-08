/**
 * @Title: InsertionSortTest.java
 * @Package yuanjun.chen.base.sort
 * @Description: 测试插入排序
 * @author: 陈元俊
 * @date: 2018年7月17日 上午8:45:03
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.sort;

import org.junit.Test;
import yuanjun.chen.base.common.GenericAlgoTester;
import yuanjun.chen.base.common.SortOrderEnum;

/**
 * @ClassName: InsertionSortTest
 * @Description:测试插入排序
 * @author: 陈元俊
 * @date: 2018年7月17日 上午8:45:03
 */
public class InsertionSortTest {
    @Test
    public void testInsertionSort1() throws Exception {
        testInsertionSortProto(10000, 3000, SortOrderEnum.ASC, Integer.class);
    }

    @Test
    public void testInsertionSort2() throws Exception {
        testInsertionSortProto(10000, 3000, SortOrderEnum.DESC, Double.class);
    }

    @Test
    public void testInsertionSort3() throws Exception {
        testInsertionBinarySortProto(10000, 3000, SortOrderEnum.ASC, Integer.class);
    }

    @Test
    public void testInsertionSort4() throws Exception {
        testInsertionBinarySortProto(10000, 3000, SortOrderEnum.DESC, Double.class);
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
    private static void testInsertionSortProto(int size, int bound, SortOrderEnum order, Class clazz) throws Exception {
        new GenericAlgoTester("INSERTION") {
            @Override
            public void showTime(Comparable[] arr, SortOrderEnum order) {
                InsertionSortAlgo.inplaceInsertionSort(arr, order);
            }
        }.genericTest(size, bound, order, clazz);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void testInsertionBinarySortProto(int size, int bound, SortOrderEnum order, Class clazz)
            throws Exception {
        new GenericAlgoTester("INSERTION-BINARY") {
            @Override
            public void showTime(Comparable[] arr, SortOrderEnum order) {
                InsertionSortAlgo.inplaceInsertionSortBinaryWay(arr, order);
            }
        }.genericTest(size, bound, order, clazz);
    }
}
