/**  
 * @Title: MemoizedCutRodTest.java   
 * @Package: yuanjun.chen.base.dynamicprogramming   
 * @Description: MemoizedCutRod测试   
 * @author: 陈元俊     
 * @date: 2018年8月22日 下午2:49:19   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.base.dynamicprogramming;

import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import yuanjun.chen.base.common.RandomGenner;

/**   
 * @ClassName: MemoizedCutRodTest   
 * @Description: 测试暴力法、DP自顶向下，DP自底向上的CUT-ROD算法  
 * @author: 陈元俊 
 * @date: 2018年8月22日 下午2:49:19  
 */
public class MemoizedCutRodTest {
    private static final Logger logger = LogManager.getLogger(MemoizedCutRodTest.class);
    
    @BeforeClass
    public static void before() throws Exception {
        Integer[] arr1 = RandomGenner.generateRandomTArray(100, 0, 400, Integer.class);
        Arrays.sort(arr1);
        int[] rules= Arrays.stream(arr1).mapToInt(Integer::valueOf).toArray(); // java8
        logger.info("rules -- " + Arrays.toString(rules));
        CutRodAlgo.setRules(rules);
    }
    
    @Test
    public void testBrute() {
        int len = 32;
        int maxq = CutRodAlgo.brute(len);
        logger.info("testBrute for len = " + len + " max revenue = " + maxq);
    }
    
    @Test
    public void testTopDP() {
        int len = 32;
        int maxq = CutRodAlgo.topDpCutWrapper(len);
        logger.info("testTopDP for len = " + len + " max revenue = " + maxq);
    }
    
    @Test
    public void testBottomDownDP() {
        int len = 32;
        int maxq = CutRodAlgo.bottomDpCutWrapper(len);
        logger.info("testBottomDownDP for len = " + len + " max revenue = " + maxq);
    }
}
