/**  
 * @Title: TestCase007.java   
 * @Package: yuanjun.chen.base.sort   
 * @Description: 快速排序测试   
 * @author: 陈元俊     
 * @date: 2018年7月19日 上午11:20:23   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.base.sort;

import java.util.Arrays;
import java.util.Collections;
import org.apache.log4j.Logger;
import org.junit.Test;
import yuanjun.chen.base.common.DispUtil;
import yuanjun.chen.base.common.RandomGenner;

/**   
 * @ClassName: TestCase007   
 * @Description: 快速排序测试  
 * @author: 陈元俊 
 * @date: 2018年7月19日 上午11:20:23  
 */
public class TestCase007 {
    private static final Logger logger = Logger.getLogger(TestCase007.class);
    /**
     * 纯乱序测试，quick sort性能优异 
     **/
    @Test
    public void testQuickSort() {
        int size = 256 * 256 * 40; // 260万条数据
        int bound = 4000;
        Integer[] arr = RandomGenner.generateRandomIntArray(size, bound);
        Integer[] arr2 = new Integer[size];
        System.arraycopy(arr, 0, arr2, 0, size);

        DispUtil.embed(50, '*', "QUICK SORT STARTS");
        //logger.info("before " + Arrays.toString(arr));
        long t1 = System.currentTimeMillis();
        QuickSortAlgo.quickSort_v1(arr);
        long t2 = System.currentTimeMillis();
        //logger.info("after " + Arrays.toString(arr));
        DispUtil.embed(50, '*', "QUICK SORT ENDS..");
        logger.info("QUICK SORT time used " + (t2 - t1) + "ms");

        DispUtil.embed(50, '*', "J.U.A INNER SORT STARTS");
        //logger.info("before " + Arrays.toString(arr2));
        long t3 = System.currentTimeMillis();
        testInnerAlgoASC(arr2);
        long t4 = System.currentTimeMillis();
        //logger.info("after " + Arrays.toString(arr2));
        DispUtil.embed(50, '*', "J.U.A INNER SORT ENDS..");
        logger.info("j.u.a INNER SORT time used " + (t4 - t3) + "ms");
    }
    
    /**
     * 大趋势为逆序，quick sort性能糟糕O[n^2]
     * random quick sort 测试随机选pivot，性能有极大提升 O[nlgn]
     **/
    @Test
    public void testQuickSort2() {
        int size = 65536 * 3; 
        int bound = 4000;
        Integer[] arr = RandomGenner.generateRandomIntArray(size, bound);
        Arrays.sort(arr, Collections.reverseOrder());
        arr[size/16] = 99;
        arr[size/4] = 2332;
        arr[size/3] = 991;
        arr[size/2] = 899;
        Integer[] arr2 = new Integer[size];
        System.arraycopy(arr, 0, arr2, 0, size);
        Integer[] arr3 = new Integer[size];
        System.arraycopy(arr, 0, arr3, 0, size);
        Integer[] arr4 = new Integer[size];
        System.arraycopy(arr, 0, arr4, 0, size);
        
        DispUtil.embed(50, '*', "QUICK SORT STARTS");
        logger.info("before " + Arrays.toString(arr));
        long t1 = System.currentTimeMillis();
        QuickSortAlgo.quickSort_v1(arr);
        long t2 = System.currentTimeMillis();
        logger.info("after " + Arrays.toString(arr));
        DispUtil.embed(50, '*', "QUICK SORT ENDS..");
        logger.info("QUICK SORT time used " + (t2 - t1) + "ms");

        DispUtil.embed(50, '*', "QUICK-RANDOM SORT STARTS");
        logger.info("before " + Arrays.toString(arr2));
        long t3 = System.currentTimeMillis();
        QuickSortAlgo.quickSort_v2(arr2);
        long t4 = System.currentTimeMillis();
        logger.info("after " + Arrays.toString(arr2));
        DispUtil.embed(50, '*', "QUICK-RANDOM SORT ENDS..");
        logger.info("QUICK-RANDOM SORT time used " + (t4 - t3) + "ms");
        
        DispUtil.embed(50, '*', "QUICK-RANDOM-LOOP SORT STARTS");
        logger.info("before " + Arrays.toString(arr3));
        long t5 = System.currentTimeMillis();
        QuickSortAlgo.quickSort_v2(arr3);
        long t6 = System.currentTimeMillis();
        logger.info("after " + Arrays.toString(arr3));
        DispUtil.embed(50, '*', "QUICK-RANDOM-LOOP SORT ENDS..");
        logger.info("QUICK-RANDOM SORT time used " + (t6 - t5) + "ms");
        
        DispUtil.embed(50, '*', "J.U.A INNER SORT STARTS");
        logger.info("before " + Arrays.toString(arr4));
        long t7 = System.currentTimeMillis();
        testInnerAlgoASC(arr4);
        long t8 = System.currentTimeMillis();
        logger.info("after " + Arrays.toString(arr4));
        DispUtil.embed(50, '*', "J.U.A INNER SORT ENDS..");
        logger.info("j.u.a INNER SORT time used " + (t8 - t7) + "ms");
    }
   
    /*
     * 用j.u.a的内置collections的顺序排序算法
     */
    public void testInnerAlgoASC(Integer[] arr) {
        Arrays.sort(arr);
    }
}
