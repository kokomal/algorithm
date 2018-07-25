/**
 * @Title: MergeSortTest.java
 * @Package yuanjun.chen.base.sort
 * @Description: 测试归并排序
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
 * @ClassName: MergeSortTest
 * @Description:测试归并排序
 * @author: 陈元俊
 * @date: 2018年7月17日 上午8:45:03
 */
public class MergeSortTest {
    @Test
    public void testMergeSort1() throws Exception {
        testMergeSortProto(10000, 8000, SortOrderEnum.ASC, Integer.class);
    }
       
    @Test
    public void testMergeSort2() throws Exception {
        testMergeSortProto(20000, 8000, SortOrderEnum.DESC, Integer.class);
    }
    
    @Test
    public void testMergeSort3() throws Exception {
        testMergeSortProto(30000, 8000, SortOrderEnum.ASC, Double.class);
    }
       
    @Test
    public void testMergeSort4() throws Exception {
        testMergeSortProto(40000, 8000, SortOrderEnum.DESC, Double.class);
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
    private static void testMergeSortProto(int size, int bound, SortOrderEnum order, Class clazz) throws Exception {
        new GenericAlgoTester("MERGE") {
            @Override
            public void showTime(Comparable[] arr, SortOrderEnum order) {
                MergeSortAlgo.mergeSort(arr, order);
            }
        }.genericTest(size, bound, order, clazz);
    }
}
