/**  
 * @Title: GenericAlgoTester.java   
 * @Package: yuanjun.chen.base.common   
 * @Description: 通用测试封装类    
 * @author: 陈元俊     
 * @date: 2018年7月25日 下午4:24:07   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.base.common;

import java.util.Arrays;
import java.util.Collections;
import org.apache.log4j.Logger;

/**   
 * @ClassName: GenericAlgoTester   
 * @Description: 通用测试封装类  
 * @author: 陈元俊 
 * @date: 2018年7月25日 下午4:24:07  
 */
public abstract class GenericAlgoTester <T extends Comparable<?>> {
    private static final Logger logger = Logger.getLogger(GenericAlgoTester.class);
    private String algoName = "";
    
    /**
     * @param algo
     */
    public GenericAlgoTester(String algoName) {
        super();
        this.algoName = algoName;
    }

    /**
     * 供其他算法继承
     **/
    public abstract void showTime(T[] arr, SortOrderEnum order);
    
    @SuppressWarnings("unchecked")
    public void genericTest(int size, int bound, SortOrderEnum order, Class<T> clazz) throws Exception {
        T[] arr1 = RandomGenner.generateRandomTArray(size, bound, clazz);
        T[] arr2 = (T[]) new Comparable[size];
        System.arraycopy(arr1, 0, arr2, 0, size);
        DispUtil.embed(50, '*', this.algoName + " SORT " + order + " STARTS");
        logger.info("before " + Arrays.toString(arr1));
        long t1 = System.currentTimeMillis();
        showTime(arr1, order);
        long t2 = System.currentTimeMillis();
        logger.info("after " + Arrays.toString(arr1));
        DispUtil.embed(50, '*', this.algoName + " SORT " + order + " ENDS..");
        logger.info(this.algoName + " SORT time used " + (t2 - t1) + "ms");

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
