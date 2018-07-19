/**  
 * @Title: StrassenAlgo.java   
 * @Package: yuanjun.chen.base.other   
 * @Description: Strassen算法求解矩阵乘法  
 * @author: 陈元俊     
 * @date: 2018年7月17日 下午5:29:16   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.base.other;

import org.apache.log4j.Logger;
import yuanjun.chen.base.common.DispUtil;
import yuanjun.chen.base.common.RandomGenner;

/**   
 * @ClassName: StrassenAlgo   
 * @Description: Strassen算法求解矩阵乘法
 * @author: 陈元俊 
 * @date: 2018年7月17日 下午5:29:16  
 */
public class StrassenAlgo {
    private static final Logger logger = Logger.getLogger(StrassenAlgo.class);
    /*
     * O[n^3]的通用矩阵乘法
     * */
    public static Integer[][] on3calcMatrix(final Integer[][] matrixA, final Integer[][] matrixB) {
        int lenA = matrixA.length;
        int lenB = matrixA[0].length;
        int lenC = matrixB.length;
        int lenD = matrixB[0].length;
        if (lenB != lenC) return null; // 维度不对等，则报错拒绝执行
        Integer[][] res = new Integer[lenA][lenD];
        for (int i = 0; i < lenA; i++) {
            for (int j = 0; j < lenD; j++) {
                // 对于i，j
                int sum = 0;
                for (int idx = 0; idx < lenB; idx++) {
                    sum += matrixA[i][idx] * matrixB[idx][j];
                }
                res[i][j] = sum;
            }
        }
        return res;
    }
    
    public static void main(String[] args) {
        Integer[][] matrixA = RandomGenner.generateRandomIntMatrix(16, 16, 100);
        Integer[][] matrixB = RandomGenner.generateRandomIntMatrix(16, 16, 100);

        logger.info("matrix A:");
        DispUtil.showMatrix(matrixA);
        DispUtil.split(60, '-');
        logger.info("matrix B:");
        DispUtil.showMatrix(matrixB);
        DispUtil.split(60, '-');
        logger.info("matrix A * B:");
        Integer[][] res = on3calcMatrix(matrixA, matrixB);
        DispUtil.showMatrix(res);
    }
}
