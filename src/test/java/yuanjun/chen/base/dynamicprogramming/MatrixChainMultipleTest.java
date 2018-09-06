/**
 * @Title: MatrixChainMultipleTest.java
 * @Package: yuanjun.chen.base.dynamicprogramming
 * @Description: MCOP算法测试
 * @author: 陈元俊
 * @date: 2018年8月27日 上午11:13:22
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.dynamicprogramming;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import yuanjun.chen.base.common.DispUtil;
import yuanjun.chen.base.common.RandomGenner;
import yuanjun.chen.base.other.StrassenAlgo;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: MatrixChainMultipleTest
 * @Description: MCOP算法测试
 * @author: 陈元俊
 * @date: 2018年8月27日 上午11:13:22
 */
public class MatrixChainMultipleTest {
    private static final Logger logger = LogManager.getLogger(MatrixChainMultipleTest.class);

    /**
     * @throws Exception
     * @Title: testChain001
     * @Description: 测试链式生成矩阵
     * @return: void
     */
    @Test
    public void testChain001() throws Exception {
        int lowbound = 2;
        int upbound = 20;
        int size = 4;
        Integer[] dms = RandomGenner.generateRandomTArray(size, lowbound, upbound, Integer.class);
        logger.info("dimension list =" + Arrays.toString(dms));
        List<Long[][]> matrice = StrassenAlgo.generateMatriceByDimensions(dms, 100);
        for (Long[][] mat : matrice) {
            DispUtil.showMatrixForCopy(mat);
            DispUtil.split(80, '-');
        }

        Long[][] res = matrice.get(0);
        for (int j = 1; j < size - 1; j++) {
            res = StrassenAlgo.on3calcMatrix(res, matrice.get(j));
            DispUtil.showMatrixForCopy(res);
        }
    }

    @Test
    public void testChainDP() throws Exception {
        int lowbound = 2;
        int upbound = 20;
        int size = 7; // size-1个矩阵
        Integer[] dms = RandomGenner.generateRandomTArray(size, lowbound, upbound, Integer.class);
        System.out.println(Arrays.toString(dms));
        MCOPAlgo.dpGenerateBestChain(dms);

        StringBuilder sb = MCOPAlgo.printAll(0, size - 2);
        System.out.println("the best sequence is " + MCOPAlgo.fetchSequence());
        System.out.println("final price is " + MCOPAlgo.fetchFinalPrice());
    }
}
