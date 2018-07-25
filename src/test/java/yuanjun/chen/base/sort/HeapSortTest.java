package yuanjun.chen.base.sort;

import org.junit.Test;
import yuanjun.chen.base.common.GenericAlgoTester;
import yuanjun.chen.base.common.SortOrderEnum;

/*
 * 堆排序测试
 */
public class HeapSortTest {
    @Test
    public void testHeap001() throws Exception {
        testHeapSortRecurProto(20000, 2000, SortOrderEnum.ASC, Integer.class);
    }
    
    @Test
    public void testHeap002() throws Exception {
        testHeapSortRecurProto(30000, 3000, SortOrderEnum.ASC, Integer.class);
    }
    
    @Test
    public void testHeap003() throws Exception {
        testHeapSortRecurProto(40000, 4000, SortOrderEnum.DESC, Double.class);
    }
    
    @Test
    public void testHeap004() throws Exception {
        testHeapSortRecurProto(50000, 5000, SortOrderEnum.DESC, Double.class);
    }
    
    @Test
    public void testHeap005() throws Exception {
        testHeapSortNonRecurProto(20000, 2000, SortOrderEnum.ASC, Integer.class);
    }
    
    @Test
    public void testHeap006() throws Exception {
        testHeapSortNonRecurProto(30000, 3000, SortOrderEnum.ASC, Integer.class);
    }
    
    @Test
    public void testHeap007() throws Exception {
        testHeapSortNonRecurProto(40000, 4000, SortOrderEnum.DESC, Double.class);
    }
    
    @Test
    public void testHeap008() throws Exception {
        testHeapSortNonRecurProto(50000, 5000, SortOrderEnum.DESC, Double.class);
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
    private static void testHeapSortRecurProto(int size, int bound, SortOrderEnum order, Class clazz) throws Exception {
        new GenericAlgoTester("HEAP") {
            @Override
            public void showTime(Comparable[] arr, SortOrderEnum order) {
                HeapSortAlgo.inplaceHeapSort(arr, order, true);
            }
        }.genericTest(size, bound, order, clazz);
    }
    
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void testHeapSortNonRecurProto(int size, int bound, SortOrderEnum order, Class clazz) throws Exception {
        new GenericAlgoTester("HEAP") {
            @Override
            public void showTime(Comparable[] arr, SortOrderEnum order) {
                HeapSortAlgo.inplaceHeapSort(arr, order, false);
            }
        }.genericTest(size, bound, order, clazz);
    }
}
