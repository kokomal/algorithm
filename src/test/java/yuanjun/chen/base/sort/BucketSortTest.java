/**  
 * @Title: BucketSortTest.java   
 * @Package: yuanjun.chen.base.sort   
 * @Description: 测试桶排序   
 * @author: 陈元俊     
 * @date: 2018年7月24日 上午10:45:30   
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
 * @ClassName: BucketSortTest   
 * @Description: 测试桶排序 
 * @author: 陈元俊 
 * @date: 2018年7月24日 上午10:45:30  
 */
public class BucketSortTest {
    private static final Logger logger = Logger.getLogger(BucketSortTest.class);
    
    /**
     * 桶排序是经典的用空间换取时间的“无赖”算法，其具备极高的时间性能O[n]
     * @throws Exception 
     **/
    @Test
    public void testBucket001() throws Exception {
        int size = 65536 * 3; 
        int bound = 4000;
        testBucketInteger(size, bound, SortOrderEnum.ASC);
    }
    
    @Test
    public void testBucket002() throws Exception {
        int size = 65536 * 3; 
        int bound = 40000;
        testBucketInteger(size, bound, SortOrderEnum.ASC);
    }
    
    @Test
    public void testBucket003() throws Exception {
        int size = 65536 * 3; 
        int bound = 400000;
        testBucketInteger(size, bound, SortOrderEnum.ASC);
    }
    
    @Test
    public void testBucket004() {
        DispUtil.embed(40, '~', "NOW WE TURN TO DESC");
    }
    
    @Test
    public void testBucket005() throws Exception {
        int size = 65536 * 3; 
        int bound = 4000;
        testBucketInteger(size, bound, SortOrderEnum.DESC);
    }
    
    @Test
    public void testBucket006() throws Exception {
        int size = 65536 * 3;
        int bound = 40000;
        testBucketInteger(size, bound, SortOrderEnum.DESC);
    }
    
    @Test
    public void testBucket007() throws Exception {
        int size = 65536 * 3; 
        int bound = 400000;
        testBucketInteger(size, bound, SortOrderEnum.DESC);
    }
    
    @Test
    public void testBucket008() {
        DispUtil.embed(40, '*', "NOW WE TURN TO Double");
    }
    
    @Test
    public void testBucket009() throws Exception {
        int size = 65536 * 3; 
        int bound = 4000;
        testBucketDouble(size, bound, SortOrderEnum.ASC);
    }
    
    @Test
    public void testBucket010() throws Exception {
        int size = 65536 * 3; 
        int bound = 40000;
        testBucketDouble(size, bound, SortOrderEnum.ASC);
    }
    
    @Test
    public void testBucket011() throws Exception {
        int size = 65536 * 3; 
        int bound = 400000;
        testBucketDouble(size, bound, SortOrderEnum.ASC);
    }
    
    @Test
    public void testBucket012() {
        DispUtil.embed(40, '~', "NOW WE TURN TO DESC");
    }
    
    @Test
    public void testBucket013() throws Exception {
        int size = 65536 * 3; 
        int bound = 4000;
        testBucketDouble(size, bound, SortOrderEnum.DESC);
    }
    
    @Test
    public void testBucket014() throws Exception {
        int size = 65536 * 3;
        int bound = 20000;
        testBucketDouble(size, bound, SortOrderEnum.DESC);
    }
    
    /**
     * 测试Integer类型
     **/
    public static void testBucketInteger(int size, int bound, SortOrderEnum order) throws Exception {
        if (size < 100000) {
            BucketSortAlgo.showDebug();
        } else {
            BucketSortAlgo.hideDebug();
        }
        Integer[] arr1 = RandomGenner.generateRandomTArray(size, bound, Integer.class);
        Integer[] arr2 = new Integer[size];
        System.arraycopy(arr1, 0, arr2, 0, size);
        DispUtil.embed(50, '*', "BUCKET SORT " + order + " STARTS");
        logger.info("before " + Arrays.toString(arr1));
        long t1 = System.currentTimeMillis();
        BucketSortAlgo.inplaceBucketSort(arr1, order);
        long t2 = System.currentTimeMillis();
        logger.info("after " + Arrays.toString(arr1));
        DispUtil.embed(50, '*', "BUCKET SORT " + order + " ENDS..");
        logger.info("BUCKET SORT time used " + (t2 - t1) + "ms");

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
    
    /**
     * 测试Double类型
     **/
    public static void testBucketDouble(int size, int bound, SortOrderEnum order) throws Exception {
        if (size < 100000) {
            BucketSortAlgo.showDebug();
        } else {
            BucketSortAlgo.hideDebug();
        }
        Double[] arr1 = RandomGenner.generateRandomTArray(size, bound, Double.class);
        Double[] arr2 = new Double[size];
        System.arraycopy(arr1, 0, arr2, 0, size);
        DispUtil.embed(50, '*', "BUCKET SORT " + order + " STARTS");
        logger.info("before " + Arrays.toString(arr1));
        long t1 = System.currentTimeMillis();
        BucketSortAlgo.inplaceBucketSort(arr1, order);
        long t2 = System.currentTimeMillis();
        logger.info("after " + Arrays.toString(arr1));
        DispUtil.embed(50, '*', "BUCKET SORT " + order + " ENDS..");
        logger.info("BUCKET SORT time used " + (t2 - t1) + "ms");

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
