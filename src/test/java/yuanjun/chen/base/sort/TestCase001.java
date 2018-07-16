package yuanjun.chen.base.sort;

import java.util.Arrays;
import org.junit.Test;
import yuanjun.chen.base.common.DispUtil;
import yuanjun.chen.base.common.RandomIntArrayGenner;
import yuanjun.chen.base.common.SortOrderEnum;

/*
 * 冒泡排序测试
 * */
public class TestCase001 {
    @Test
    public void testBubble() {
        int size1 = 20;
        int bound = 100;
        int size2 = 4;
        DispUtil.embed(50, '*', "BUBBLE TEST STARTS");
        testInplaceBubbleSort(size1, bound, SortOrderEnum.ASC); // 升序测试
        DispUtil.split(50 + size1 * 4, '='); // 分行
        testInplaceBubbleSort(size1, bound, SortOrderEnum.DESC); // 降序测试
        DispUtil.split(50 + size1 * 4, '='); // 分行
        testInplaceBubbleSort(size2, bound, SortOrderEnum.ASC); // 升序测试
        DispUtil.split(50 + size2 * 4, '='); // 分行
        testInplaceBubbleSort(size2, bound, SortOrderEnum.DESC); // 降序测试
        DispUtil.embed(50, '*', "BUBBLE TEST ENDS");
    }
    
    public void testInplaceBubbleSort(int size, int bound, SortOrderEnum order) {
        Integer[] arr = RandomIntArrayGenner.generateRandomIntArray(size, bound);
        System.out.println("the unsorted data---" + Arrays.toString(arr));
        BubbleSortAlgo.inplaceBubbleSort(arr, order);
        System.out.println("after " + order + " inplace bubble sort, the sorted data---" + Arrays.toString(arr));
    }
}
