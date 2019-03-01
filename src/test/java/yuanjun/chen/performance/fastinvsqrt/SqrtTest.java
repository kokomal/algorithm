/**  
 * @Title: SqrtTest.java   
 * @Package: yuanjun.chen.performance.fastinvsqrt   
 * @Description: TODO(用一句话描述该文件做什么)   
 * @author: 陈元俊     
 * @date: 2018年11月22日 上午8:47:34   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.performance.fastinvsqrt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.BeforeClass;
import org.junit.Test;
import yuanjun.chen.base.common.MathTool;

/**   
 * @ClassName: SqrtTest   
 * @Description: 快速法求平方根，和牛顿法求平方根各有千秋，牛顿法的好处是计算精度可控，但运算速度略慢（含不可知的迭代）
 * 快速法的好处就是算法及其简洁，缺点是精度有点差
 * @author: 陈元俊 
 * @date: 2018年11月22日 上午8:47:34  
 */
public class SqrtTest {
    private static final int len = 400000;
    private static final int limit = 100000;
    private static List<Double> raws = new ArrayList<>(len);
    
    @BeforeClass
    public static void init() {
        Random rd = new Random();
        for (int i = 0; i < len; i++) {
            int k = rd.nextInt(limit);
            Double dd = rd.nextDouble();
            double x = dd * k;
            raws.add(x);
        }
    }
    
    @Test
    public void testTraditionalNewton() {
        System.out.println("TEST DOUBLE NEWTON SQRT");
        for (Double d : raws) {
            System.out.println("sqrt(" + d + ")=" + MathTool.newtonInvSqrt(d));
        }
    }
    
    @Test
    public void testDoubleFastSqrt() {
        System.out.println("TEST DOUBLE FAST SQRT");
        for (Double d : raws) {
            System.out.println("sqrt(" + d + ")=" + MathTool.fastInvSqrt(d));
        }
    }
}
