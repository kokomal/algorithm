package yuanjun.chen.base.sort;

import java.util.Arrays;
import org.junit.Test;
import yuanjun.chen.base.common.DispUtil;
import yuanjun.chen.base.common.RandomIntArrayGenner;
import yuanjun.chen.base.common.SortOrderEnum;

public class TestCase002 {

    @Test
    public void testHeap() {
        int size = 16;
        int bound = 100;
        DispUtil.embed(50, '*', "HEAP TEST STARTS");
        testHeapSort(size, bound, SortOrderEnum.ASC, true);
        DispUtil.split(50 + size * 4, '=');
        testHeapSort(size, bound, SortOrderEnum.DESC, true);
        DispUtil.split(50 + size * 4, '=');
        testHeapSort(size, bound, SortOrderEnum.ASC, false);
        DispUtil.split(50 + size * 4, '=');
        testHeapSort(size, bound, SortOrderEnum.DESC, false);
        DispUtil.embed(50, '*', "HEAP TEST ENDS");
    }

    public void testHeapSort(int size, int bound, SortOrderEnum order, boolean recurFlag) {
        int[] arr = RandomIntArrayGenner.generateRandomIntArray(size, bound);
        System.out.println("before " + order + (recurFlag ? " recursive" : " non-recusive") + " inplace heap sort---"
                + Arrays.toString(arr));
        HeapSortAlgo.inplaceHeapSort(arr, order, recurFlag);
        System.out.println("after " + order + (recurFlag ? " recursive" : " non-recusive") + " inplace heap sort---"
                + Arrays.toString(arr));
    }
}
