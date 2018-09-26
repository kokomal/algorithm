/**  
 * @Title: InvestmentConselorTest.java   
 * @Package: yuanjun.chen.base.dynamicprogramming   
 * @Description: 用动态规划（切钢条方法）解决投资顾问的投资包最优问题 
 * @author: 陈元俊     
 * @date: 2018年9月26日 上午8:48:46   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.base.dynamicprogramming;

import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;

/**   
 * @ClassName: InvestmentConselorTest   
 * @Description: 用动态规划（切钢条方法）解决投资顾问的投资包最优问题 
 * @author: 陈元俊 
 * @date: 2018年9月26日 上午8:48:46  
 */
public class InvestmentConselorTest {
    private static final Logger logger = LogManager.getLogger(InvestmentConselorTest.class);
    private static int totalMoney;
    
    @BeforeClass
    public static void before() throws Exception {
        Integer[] arr1 = new Integer[] {1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
        totalMoney = 7000;
        Arrays.sort(arr1);
        int[] rules = Arrays.stream(arr1).mapToInt(Integer::valueOf).toArray(); // java8
        logger.info("rules -- " + Arrays.toString(rules));
        CutRodAlgo.setRules(rules);
    }
}
