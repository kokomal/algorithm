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

import java.util.Arrays;
import java.util.Collections;
import org.apache.log4j.Logger;
import org.junit.Test;
import yuanjun.chen.base.common.DispUtil;
import yuanjun.chen.base.common.RandomGenner;
import yuanjun.chen.base.common.SortOrderEnum;

/**   
 * @ClassName: CountingSortTest   
 * @Description: 测试对比计数排序   
 * @author: 陈元俊 
 * @date: 2018年7月20日 下午2:43:12  
 */
public class CountingSortTest {
    private static final Logger logger = Logger.getLogger(CountingSortTest.class);
    
    /**
     * 20万条数据，元素位于[0,3999]区间的话，计数排序性能极好
     * @throws Exception 
     **/
    @Test
    public void countingSortTest1() throws Exception {
        countingSortTest(200000, 4000);
    }
    
    /**
     * 400000条数据，元素位于[0,300000)区间的话，计数排序性能仍然优秀
     * 但空间占用比较高，需要额外的O[n+m]空间
     * @throws Exception 
     **/
    @Test
    public void countingSortTest2() throws Exception {
        countingSortTest(400000, 300000);
    }
    
    /**
     * 400000条数据，元素位于[0,3)区间的话，计数排序性能仍然优秀
     * m越窄，性能越优秀
     * @throws Exception 
     **/
    @Test
    public void countingSortTest3() throws Exception {
        countingSortTest(400000, 3);
    }
    
    public void countingSortTest(int size, int bound) throws Exception {
        Integer[] arr = RandomGenner.generateRandomTArray(size, bound, Integer.class);
        Integer[] arr2 = new Integer[size];
        System.arraycopy(arr, 0, arr2, 0, size);
        Integer[] arr3 = new Integer[size];
        System.arraycopy(arr, 0, arr3, 0, size);
        Integer[] arr4 = new Integer[size];
        System.arraycopy(arr, 0, arr4, 0, size);
        
        DispUtil.embed(50, '*', "COUNTING ASC SORT STARTS");
        logger.info("before " + Arrays.toString(arr));
        long t1 = System.currentTimeMillis();
        Integer[] res1 = CountingSortAlgo.genericCountingSort(arr, SortOrderEnum.ASC);
        long t2 = System.currentTimeMillis();
        logger.info("after " + Arrays.toString(res1));
        DispUtil.embed(50, '*', "COUNTING SORT ENDS..");
        logger.info("COUNTING ASC SORT time used " + (t2 - t1) + "ms");
        
        DispUtil.embed(50, '*', "COUNTING DESC SORT STARTS");
        logger.info("before " + Arrays.toString(arr2));
        long t3 = System.currentTimeMillis();
        Integer[] res2 = CountingSortAlgo.genericCountingSort(arr2, SortOrderEnum.DESC);
        long t4 = System.currentTimeMillis();
        logger.info("after " + Arrays.toString(res2));
        DispUtil.embed(50, '*', "COUNTING DESC SORT ENDS..");
        logger.info("COUNTING DESC SORT time used " + (t4 - t3) + "ms");
        
        DispUtil.embed(50, '*', "J.U.A INNER ASC SORT STARTS");
        logger.info("before " + Arrays.toString(arr3));
        long t5 = System.currentTimeMillis();
        Arrays.sort(arr3);
        long t6 = System.currentTimeMillis();
        logger.info("after " + Arrays.toString(arr3));
        DispUtil.embed(50, '*', "J.U.A INNER ASC SORT ENDS..");
        logger.info("j.u.a INNER ASC SORT time used " + (t6 - t5) + "ms");
        
        DispUtil.embed(50, '*', "J.U.A INNER DESC SORT STARTS");
        logger.info("before " + Arrays.toString(arr4));
        long t7 = System.currentTimeMillis();
        Arrays.sort(arr4, Collections.reverseOrder());
        long t8 = System.currentTimeMillis();
        logger.info("after " + Arrays.toString(arr4));
        DispUtil.embed(50, '*', "J.U.A INNER DESC SORT ENDS..");
        logger.info("j.u.a INNER DESC SORT time used " + (t8 - t7) + "ms");
    }
}
