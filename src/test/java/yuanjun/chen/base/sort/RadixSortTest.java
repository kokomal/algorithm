/**
 * @Title: RadixSortTest.java
 * @Package: yuanjun.chen.base.sort
 * @Description: 基数排序测试
 * @author: 陈元俊
 * @date: 2018年7月25日 下午3:31:47
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.sort;

import org.junit.Test;
import yuanjun.chen.base.common.GenericAlgoTester;
import yuanjun.chen.base.common.SortOrderEnum;

/**
 * @ClassName: RadixSortTest
 * @Description: 基数排序测试
 * @author: 陈元俊
 * @date: 2018年7月25日 下午3:31:47
 */
public class RadixSortTest {
    @Test
    public void testRadix001() throws Exception {
        testRadixSortProto(100000, 8000, SortOrderEnum.ASC, Integer.class);
    }

    @Test
    public void testRadix002() throws Exception {
        testRadixSortProto(100000, 8000, SortOrderEnum.DESC, Integer.class);
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
    private static void testRadixSortProto(int size, int bound, SortOrderEnum order, Class clazz) throws Exception {
        new GenericAlgoTester("RADIX") {
            @Override
            public void showTime(Comparable[] arr, SortOrderEnum order) {
                RadixSortAlgo.radixSort((Integer[]) arr, order);
            }
        }.genericTest(size, bound, order, clazz);
    }
}
