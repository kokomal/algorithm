/**  
 * @Title: ViterbiAlgo.java   
 * @Package: yuanjun.chen.base.dynamicprogramming   
 * @Description: 维特比算法   
 * @author: 陈元俊     
 * @date: 2018年9月21日 上午10:42:10   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.base.dynamicprogramming.viterbi;

/**   
 * @ClassName: ViterbiAlgo   
 * @Description: 维特比算法  
 * @author: 陈元俊 
 * @date: 2018年9月21日 上午10:42:10  
 */
public class ViterbiAlgo {
    private static ViterbiNode FIRST;
    private static ViterbiNode END;
    
    public static void init() {
        FIRST = new ViterbiNode(null, 1.0, 0);
        
        // 权重跳转矩阵 1*a1, a1*a2, a2*a3, ... an-1*an
        
        END = new ViterbiNode(null, 1.0, 0);
    }
}
