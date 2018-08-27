/**
 * @Title: MatrixChainMultipleTest.java
 * @Package: yuanjun.chen.base.dynamicprogramming
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年8月27日 上午11:13:22
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.dynamicprogramming;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import yuanjun.chen.base.common.DispUtil;
import yuanjun.chen.base.common.RandomGenner;
import yuanjun.chen.base.other.StrassenAlgo;

/**
 * @ClassName: MatrixChainMultipleTest
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author: 陈元俊
 * @date: 2018年8月27日 上午11:13:22
 */
public class MatrixChainMultipleTest {
    /**
     * @Title: testChain001
     * @Description: 测试链式生成矩阵
     * @throws Exception
     * @return: void
     */
    @Test
    public void testChain001() throws Exception {
        int lowbound = 2;
        int upbound = 20;
        int size = 4;
        Integer[] dms = RandomGenner.generateRandomTArray(size, lowbound, upbound, Integer.class);
        System.out.println("dms=" + Arrays.toString(dms));
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
}
