/**  
 * @Title: CountingSortTest.java   
 * @Package: yuanjun.chen.base.sort   
 * @Description: 测试对比计数排序  
 * @author: 陈元俊     
 * @date: 2018年7月20日 下午2:43:12   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.base.sort;

import org.junit.Test;
import yuanjun.chen.base.common.GenericAlgoTester;
import yuanjun.chen.base.common.SortOrderEnum;

/**   
 * @ClassName: CountingSortTest   
 * @Description: 测试对比计数排序   
 * @author: 陈元俊 
 * @date: 2018年7月20日 下午2:43:12  
 */
public class CountingSortTest {
    @Test
    public void countingSortTest1() throws Exception {
        testCountingSortProto(200000, 4000, SortOrderEnum.ASC, Integer.class);
    }
    
    /**
     * 400000条数据，元素位于[0,300000)区间的话，计数排序性能仍然优秀
     * 但空间占用比较高，需要额外的O[n+m]空间
     * @throws Exception 
     **/
    @Test
    public void countingSortTest2() throws Exception {
        testCountingSortProto(400000, 300000, SortOrderEnum.ASC, Integer.class);
    }
    
    /**
     * 400000条数据，元素位于[0,3)区间的话，计数排序性能仍然优秀
     * m越窄，性能越优秀
     * @throws Exception 
     **/
    @Test
    public void countingSortTest3() throws Exception {
        testCountingSortProto(500000, 4, SortOrderEnum.DESC, Integer.class);
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
    private static void testCountingSortProto(int size, int bound, SortOrderEnum order, Class clazz) throws Exception {
        new GenericAlgoTester("COUNTING") {
            @Override
            public void showTime(Comparable[] arr, SortOrderEnum order) {
                Integer[] res = CountingSortAlgo.genericCountingSort((Integer[])arr, order);
                System.arraycopy(res, 0, arr, 0, arr.length);
            }
        }.genericTest(size, bound, order, clazz);
    }
}
