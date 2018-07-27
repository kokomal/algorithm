/**  
 * @Title: FindTest.java   
 * @Package: yuanjun.chen.base.find   
 * @Description: 查找测试     
 * @author: 陈元俊     
 * @date: 2018年7月26日 上午10:29:17   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.base.find;

import java.math.BigDecimal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import yuanjun.chen.base.common.MyPair;
import yuanjun.chen.base.common.RandomGenner;

/**   
 * @ClassName: FindTest   
 * @Description: 查找测试   
 * @author: 陈元俊 
 * @date: 2018年7月26日 上午10:29:17  
 */
public class FindTest {
    private static final Logger logger = LogManager.getLogger(FindTest.class);
    @Test
    public void testFindAlgo001() throws Exception {
        BigDecimal[] arr1 = RandomGenner.generateRandomTArray(10000, 20000, BigDecimal.class);
        logger.info("min = " + FindAlgo.findMinOnly(arr1));
        logger.info("max = " + FindAlgo.findMaxOnly(arr1));
        MyPair<BigDecimal> pair = FindAlgo.fineBothMinAndMax(arr1);
        logger.info("max = " + pair.getMax() + ", and min = " + pair.getMin());
    }
}
