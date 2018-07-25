/**
 * @Title: SelectionSortTest.java
 * @Package: yuanjun.chen.base.sort
 * @Description: 选择排序
 * @author: 陈元俊
 * @date: 2018年7月18日 下午5:40:11
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.sort;

import org.junit.Test;
import yuanjun.chen.base.common.GenericAlgoTester;
import yuanjun.chen.base.common.SortOrderEnum;

/**
 * @ClassName: SelectionSortTest
 * @Description: 选择排序，每次必O[n^2],性能非常差
 * @author: 陈元俊
 * @date: 2018年7月18日 下午5:40:11
 */
public class SelectionSortTest {
    @Test
    public void testSelectionSort001() throws Exception {
        int size = 65536;
        int bound = 1000;
        testSelectionSortProto(size, bound, SortOrderEnum.DESC, Integer.class);
    }

    @Test
    public void testSelectionSort002() throws Exception {
        int size = 65536;
        int bound = 10000;
        testSelectionSortProto(size, bound, SortOrderEnum.DESC, Integer.class);
    }

    @Test
    public void testSelectionSort003() throws Exception {
        int size = 65536;
        int bound = 100000;
        testSelectionSortProto(size, bound, SortOrderEnum.DESC, Integer.class);
    }

    @Test
    public void testSelectionSort004() throws Exception {
        int size = 65536;
        int bound = 1000;
        testSelectionSortProto(size, bound, SortOrderEnum.DESC, Double.class);
    }

    @Test
    public void testSelectionSort005() throws Exception {
        int size = 65536;
        int bound = 10000;
        testSelectionSortProto(size, bound, SortOrderEnum.DESC, Double.class);
    }

    @Test
    public void testSelectionSort006() throws Exception {
        int size = 65536;
        int bound = 100000;
        testSelectionSortProto(size, bound, SortOrderEnum.DESC, Double.class);
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
    private static void testSelectionSortProto(int size, int bound, SortOrderEnum order, Class clazz) throws Exception {
        new GenericAlgoTester("SELECTION") {
            @Override
            public void showTime(Comparable[] arr, SortOrderEnum order) {
                SelectionSortAlgo.inplaceSelectionSort(arr, order);
            }
        }.genericTest(size, bound, order, clazz);
    }
}
