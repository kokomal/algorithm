/**  
 * @Title: OptimalBSTTest.java   
 * @Package: yuanjun.chen.base.dynamicprogramming   
 * @Description: TODO(用一句话描述该文件做什么)   
 * @author: 陈元俊     
 * @date: 2018年9月5日 下午1:05:02   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.base.dynamicprogramming;

import org.junit.Test;

/**   
 * @ClassName: OptimalBSTTest   
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 陈元俊 
 * @date: 2018年9月5日 下午1:05:02  
 */
public class OptimalBSTTest {
    @Test
    public void testOptimalBST() {
        String[] keys = new String[] {"NedStark", "JaimeLannister", "Daenerys", "TheoGreyjoy", "JoraMormont"};
        Double[] srcP = new Double[] {0.15, 0.10, 0.05, 0.10, 0.20};
        Double[] srcQ = new Double[] {0.03, 0.12, 0.04, 0.06, 0.05, 0.10};
        OptimalBSTAlgo.init(keys, srcP, srcQ);
        OptimalBSTAlgo.optimalBST();
        OptimalBSTAlgo.buildWrap();
    }
}
