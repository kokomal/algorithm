/**
 * @Title: BucketSortTest.java
 * @Package: yuanjun.chen.base.sort
 * @Description: 测试桶排序
 * @author: 陈元俊
 * @date: 2018年7月24日 上午10:45:30
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.sort;

import org.junit.Test;
import yuanjun.chen.base.common.DispUtil;
import yuanjun.chen.base.common.GenericAlgoTester;
import yuanjun.chen.base.common.SortOrderEnum;

/**
 * @ClassName: BucketSortTest
 * @Description: 测试桶排序
 * @author: 陈元俊
 * @date: 2018年7月24日 上午10:45:30
 */
public class BucketSortTest {
    /**
     * 桶排序是经典的用空间换取时间的“无赖”算法，其具备极高的时间性能O[n]
     * 
     * @throws Exception
     **/
    @Test
    public void testBucket001() throws Exception {
        int size = 65536;
        int bound = 4000;
        testBucketSortProto(size, bound, SortOrderEnum.ASC, Integer.class);
    }

    @Test
    public void testBucket002() throws Exception {
        int size = 65536;
        int bound = 40000;
        testBucketSortProto(size, bound, SortOrderEnum.ASC, Integer.class);
    }

    @Test
    public void testBucket003() throws Exception {
        int size = 65536;
        int bound = 400000;
        testBucketSortProto(size, bound, SortOrderEnum.ASC, Integer.class);
    }

    @Test
    public void testBucket004() {
        DispUtil.embed(40, '~', "NOW WE TURN TO DESC");
    }

    @Test
    public void testBucket005() throws Exception {
        int size = 65536;
        int bound = 4000;
        testBucketSortProto(size, bound, SortOrderEnum.DESC, Integer.class);
    }

    @Test
    public void testBucket006() throws Exception {
        int size = 65536;
        int bound = 40000;
        testBucketSortProto(size, bound, SortOrderEnum.DESC, Integer.class);
    }

    @Test
    public void testBucket007() throws Exception {
        int size = 65536;
        int bound = 200000;
        testBucketSortProto(size, bound, SortOrderEnum.DESC, Integer.class);
    }

    @Test
    public void testBucket008() {
        DispUtil.embed(40, '*', "NOW WE TURN TO Double");
    }

    @Test
    public void testBucket009() throws Exception {
        int size = 65536;
        int bound = 4000;
        testBucketSortProto(size, bound, SortOrderEnum.ASC, Double.class);
    }

    @Test
    public void testBucket010() throws Exception {
        int size = 65536;
        int bound = 10000;
        testBucketSortProto(size, bound, SortOrderEnum.ASC, Double.class);
    }

    @Test
    public void testBucket011() throws Exception {
        int size = 65536;
        int bound = 20000;
        testBucketSortProto(size, bound, SortOrderEnum.ASC, Double.class);
    }

    @Test
    public void testBucket012() {
        DispUtil.embed(40, '~', "NOW WE TURN TO DESC");
    }

    @Test
    public void testBucket013() throws Exception {
        int size = 65536;
        int bound = 4000;
        testBucketSortProto(size, bound, SortOrderEnum.DESC, Double.class);
    }

    @Test
    public void testBucket014() throws Exception {
        int size = 65536;
        int bound = 20000;
        testBucketSortProto(size, bound, SortOrderEnum.DESC, Double.class);
    }

    /**
     * @Title testBucketSortProto
     * @Description 原始测试类
     * @param size
     * @param bound
     * @param order
     * @throws Exception
     * @return: void
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void testBucketSortProto(int size, int bound, SortOrderEnum order, Class clazz) throws Exception {
        new GenericAlgoTester("BUCKET") {
            @Override
            public void showTime(Comparable[] arr, SortOrderEnum order) {
                BucketSortAlgo.inplaceBucketSort(arr, order);
            }
        }.genericTest(size, bound, order, clazz);
    }
}
