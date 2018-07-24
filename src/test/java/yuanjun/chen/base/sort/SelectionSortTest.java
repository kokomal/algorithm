/**
 * @Title: SelectionSortTest.java
 * @Package: yuanjun.chen.base.sort
 * @Description: 选择排序
 * @author: 陈元俊
 * @date: 2018年7月18日 下午5:40:11
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.sort;

import java.util.Arrays;
import org.apache.log4j.Logger;
import org.junit.Test;
import yuanjun.chen.base.common.DispUtil;
import yuanjun.chen.base.common.RandomGenner;
import yuanjun.chen.base.common.SortOrderEnum;

/**
 * @ClassName: SelectionSortTest
 * @Description: 选择排序，每次必O[n^2],性能非常差
 * @author: 陈元俊
 * @date: 2018年7月18日 下午5:40:11
 */
public class SelectionSortTest {
    private static final Logger logger = Logger.getLogger(SelectionSortTest.class);
    @Test
    public void testSelectionSort() {
        int size = 128 * 256;
        int bound = 4000;
        Integer[] arr = RandomGenner.generateRandomIntArray(size, bound);
        Integer[] arr2 = new Integer[size];
        System.arraycopy(arr, 0, arr2, 0, size);

        DispUtil.embed(50, '*', "SELECTION SORT STARTS");
        logger.info("before " + Arrays.toString(arr));
        long t1 = System.currentTimeMillis();
        SelectionSortAlgo.inplaceSelectionSort(arr, SortOrderEnum.ASC);
        long t2 = System.currentTimeMillis();
        logger.info("after " + Arrays.toString(arr));
        DispUtil.embed(50, '*', "SELECTION SORT ENDS..");
        logger.info("SELECT SORT time used " + (t2 - t1) + "ms");

        DispUtil.embed(50, '*', "J.U.A INNER SORT STARTS");
        logger.info("before " + Arrays.toString(arr2));
        long t3 = System.currentTimeMillis();
        testInnerAlgoASC(arr2);
        long t4 = System.currentTimeMillis();
        logger.info("after " + Arrays.toString(arr2));
        DispUtil.embed(50, '*', "J.U.A INNER SORT ENDS..");
        logger.info("j.u.a INNER SORT time used " + (t4 - t3) + "ms");
    }

    /*
     * 用j.u.a的内置collections的顺序排序算法
     */
    public void testInnerAlgoASC(Integer[] arr) {
        Arrays.sort(arr);
    }
}
