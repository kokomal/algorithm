package yuanjun.chen.base.sort;

import java.util.Arrays;
import org.apache.log4j.Logger;
import org.junit.Test;
import yuanjun.chen.base.common.DispUtil;
import yuanjun.chen.base.common.RandomGenner;
import yuanjun.chen.base.common.SortOrderEnum;

/*
 * 冒泡排序测试
 * */
public class BubbleSortTest {
    private static final Logger logger = Logger.getLogger(BubbleSortTest.class);
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
        DispUtil.embed(50, '*', "BUBBLE TEST ENDS..");
    }
    
    public void testInplaceBubbleSort(int size, int bound, SortOrderEnum order) {
        Integer[] arr = RandomGenner.generateRandomIntArray(size, bound);
        logger.info("the unsorted data---" + Arrays.toString(arr));
        BubbleSortAlgo.inplaceBubbleSort(arr, order);
        logger.info("after " + order + " inplace bubble sort, the sorted data---" + Arrays.toString(arr));
    }
}
