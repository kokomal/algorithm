package yuanjun.chen.base.sort;

import java.util.Arrays;
import org.junit.Test;
import yuanjun.chen.base.common.DispUtil;
import yuanjun.chen.base.common.RandomIntArrayGenner;
import yuanjun.chen.base.common.SortOrderEnum;

/*
 * 堆排序测试
 */
public class TestCase002 {

    @Test
    public void testHeap() {
        int size = 16;
        int bound = 100;
        int[] arr = RandomIntArrayGenner.generateRandomIntArray(size, bound);
        DispUtil.embed(50, '*', "HEAP TEST STARTS");
        testHeapSort(arr, size, bound, SortOrderEnum.ASC, true);
        DispUtil.split(50 + size * 4, '=');
        testHeapSort(arr, size, bound, SortOrderEnum.DESC, true);
        DispUtil.split(50 + size * 4, '=');
        testHeapSort(arr, size, bound, SortOrderEnum.ASC, false);
        DispUtil.split(50 + size * 4, '=');
        testHeapSort(arr, size, bound, SortOrderEnum.DESC, false);
        DispUtil.embed(50, '*', "HEAP TEST ENDS");
    }

    @Test
    public void testHeapHeavy() {
        int size = 16 * 16 * 16 * 16 * 2; // 13万条数据
        int bound = 500000;
        DispUtil.embed(50, '*', "HEAP TEST STARTS");
        int[] arr = RandomIntArrayGenner.generateRandomIntArray(size, bound);
        long time1 = System.currentTimeMillis();
        testHeapSort(arr, size, bound, SortOrderEnum.ASC, true);
        DispUtil.split(500, '=');
        //testHeapSort(size, bound, SortOrderEnum.DESC, true);
        //DispUtil.split(500, '=');
        long time2 = System.currentTimeMillis();
        System.out.println("recursive time = " + (time2 - time1) + "ms");
        arr = RandomIntArrayGenner.generateRandomIntArray(size, bound);
        testHeapSort(arr, size, bound, SortOrderEnum.ASC, false);
        //DispUtil.split(500, '=');
        //testHeapSort(size, bound, SortOrderEnum.DESC, false);
        long time3 = System.currentTimeMillis();
        System.out.println("recursive time = " + (time3 - time2) + "ms");
        DispUtil.split(500, '=');
        arr = RandomIntArrayGenner.generateRandomIntArray(size, bound);
        testInnerAlgo(arr, size, bound);
        long time4 = System.currentTimeMillis();
        System.out.println("inner algo time = " + (time4 - time3) + "ms");
        DispUtil.embed(50, '*', "HEAP TEST ENDS");
    }

    public void testHeapSort(int[] arr, int size, int bound, SortOrderEnum order, boolean recurFlag) {
        System.out.println("before " + order + (recurFlag ? " recursive" : " non-recusive") + " inplace heap sort---"
                + Arrays.toString(arr));
        HeapSortAlgo.inplaceHeapSort(arr, order, recurFlag);
        System.out.println("after " + order + (recurFlag ? " recursive" : " non-recusive") + " inplace heap sort---"
                + Arrays.toString(arr));
    }

    /*
     * 用j.u.a的内置collections的排序算法
     * */
    public void testInnerAlgo(int[] arr, int size, int bound) {
        System.out.println("before " + " inner sort---" + Arrays.toString(arr));
        Arrays.sort(arr);
        System.out.println("after " + " inner sort---" + Arrays.toString(arr));
    }
}
