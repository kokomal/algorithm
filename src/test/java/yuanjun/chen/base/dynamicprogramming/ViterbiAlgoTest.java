/**  
 * @Title: ViterbiAlgoTest.java   
 * @Package: yuanjun.chen.base.dynamicprogramming   
 * @Description: TODO(用一句话描述该文件做什么)   
 * @author: 陈元俊     
 * @date: 2018年9月21日 上午11:12:31   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.base.dynamicprogramming;

import java.util.Arrays;
import org.junit.Test;
import yuanjun.chen.base.common.CommonUtils;
import yuanjun.chen.base.common.RandomGenner;

/**   
 * @ClassName: ViterbiAlgoTest   
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 陈元俊 
 * @date: 2018年9月21日 上午11:12:31  
 */
public class ViterbiAlgoTest {
    @Test
    public void testGenerateUnifiedMatrix() {
       int m = 8; int n = 12;
       Double[][] zz = RandomGenner.generateRandomUnifiedMatrix(m, n);
       for (int i = 0; i < m; i++) {
           System.out.println(Arrays.toString(zz[i]));
       }
       System.out.println("------------------------------------");
       Double[][] zzT = CommonUtils.reverseMatrix(zz);
       for (int i = 0; i < n; i++) {
           System.out.println(Arrays.toString(zzT[i]));
       }
    }
}
