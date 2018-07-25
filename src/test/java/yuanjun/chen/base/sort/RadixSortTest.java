/**  
 * @Title: RadixSortTest.java   
 * @Package: yuanjun.chen.base.sort   
 * @Description: 基数排序测试   
 * @author: 陈元俊     
 * @date: 2018年7月25日 下午3:31:47   
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
 * @ClassName: RadixSortTest   
 * @Description: 基数排序测试 
 * @author: 陈元俊 
 * @date: 2018年7月25日 下午3:31:47  
 */
public class RadixSortTest {
    private static final Logger logger = Logger.getLogger(RadixSortTest.class);
    
    @Test
    public void testRadix001() throws Exception {
        testBucketInteger(100000, 8000, SortOrderEnum.ASC);
    }
    
    @Test
    public void testRadix002() throws Exception {
        testBucketInteger(100000, 8000, SortOrderEnum.DESC);
    }
    
    /**
     * 测试Integer类型
     **/
    public static void testBucketInteger(int size, int bound, SortOrderEnum order) throws Exception {
        Integer[] arr1 = RandomGenner.generateRandomTArray(size, bound, Integer.class);
        Integer[] arr2 = new Integer[size];
        System.arraycopy(arr1, 0, arr2, 0, size);
        DispUtil.embed(50, '*', "RADIX SORT " + order + " STARTS");
        logger.info("before " + Arrays.toString(arr1));
        long t1 = System.currentTimeMillis();
        RadixSortAlgo.radixSort(arr1, order);
        long t2 = System.currentTimeMillis();
        logger.info("after " + Arrays.toString(arr1));
        DispUtil.embed(50, '*', "RADIX SORT " + order + " ENDS..");
        logger.info("RADIX SORT time used " + (t2 - t1) + "ms");

        DispUtil.embed(50, '*', "J.U.A INNER SORT " + order + " STARTS");
        logger.info("before " + Arrays.toString(arr2));
        long t3 = System.currentTimeMillis();
        if (SortOrderEnum.DESC.equals(order)) {
            Arrays.sort(arr2, Collections.reverseOrder());
        } else {
            Arrays.sort(arr2);
        }
        long t4 = System.currentTimeMillis();
        logger.info("after " + Arrays.toString(arr2));
        DispUtil.embed(50, '*', "J.U.A INNER SORT " + order + " ENDS..");
        logger.info("j.u.a INNER SORT time used " + (t4 - t3) + "ms");
    }
}
