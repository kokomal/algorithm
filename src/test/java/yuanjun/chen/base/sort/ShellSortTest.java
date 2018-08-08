/**
 * @Title: ShellSortTest.java
 * @Package: yuanjun.chen.base.sort
 * @Description: 希尔排序测试
 * @author: 陈元俊
 * @date: 2018年7月25日 下午3:54:37
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.sort;

import org.junit.Test;
import yuanjun.chen.base.common.GenericAlgoTester;
import yuanjun.chen.base.common.SortOrderEnum;

/**
 * @ClassName: ShellSortTest
 * @Description: 希尔排序测试
 * @author: 陈元俊
 * @date: 2018年7月25日 下午3:54:37
 */
public class ShellSortTest {
    @Test
    public void testShell001() throws Exception {
        testShellSortProto(100000, 8000, SortOrderEnum.ASC, Integer.class);
    }

    @Test
    public void testShell002() throws Exception {
        testShellSortProto(100000, 8000, SortOrderEnum.DESC, Integer.class);
    }

    @Test
    public void testShell003() throws Exception {
        testShellSortProto(100000, 8000, SortOrderEnum.ASC, Double.class);
    }

    @Test
    public void testShell004() throws Exception {
        testShellSortProto(100000, 8000, SortOrderEnum.DESC, Double.class);
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
    private static void testShellSortProto(int size, int bound, SortOrderEnum order, Class clazz) throws Exception {
        new GenericAlgoTester("SHELL") {
            @Override
            public void showTime(Comparable[] arr, SortOrderEnum order) {
                ShellSortAlgo.inplaceShellSortKnuthWay(arr, order);
            }
        }.genericTest(size, bound, order, clazz);
    }
}
