/**
 * @Title: CutStringAlgo.java
 * @Package: yuanjun.chen.base.dynamicprogramming
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年10月8日 上午10:50:34
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.dynamicprogramming;

import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @ClassName: CutStringAlgo
 * @Description: CUT-STRING,对标CLRS-3习题15-9
 * @author: 陈元俊
 * @date: 2018年10月8日 上午10:50:34
 */
public class CutStringAlgo {
    private static final Logger logger = LogManager.getLogger(CutStringAlgo.class);

    private static Integer[][] weightMap; // n*n的权重map
    private static Integer[][] solution; // n*n的权重map
    private static Integer[] lenArray; // 输入的string长度，n维度，成员均大于0
    private static Integer[][] spanCache; // n*n的距离缓存

    public static void init(Integer[] inputArray) {
        Integer n = inputArray.length;
        lenArray = new Integer[n];
        System.arraycopy(inputArray, 0, lenArray, 0, n);
        weightMap = new Integer[n][n];
        solution = new Integer[n][n];
        spanCache = new Integer[n][n];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                setWeightAt(i, j, Integer.MIN_VALUE);
                setSolutionAt(i, j, Integer.MIN_VALUE);
                setSpanCacheAt(i, j, Integer.MIN_VALUE);
            }
        }
        for (int i = 1; i <= n; i++) {
            setWeightAt(i, i, 0);
        }
        for (int gap = 1; gap <= n - 1; gap++) {
            for (int i = 1; i <= n - gap; i++) {
                int j = i + gap;
                int minWeight = Integer.MAX_VALUE;
                int wtSpanAtIJ = getWtSpanAtIJ(i, j); // 获得i，j区间的总和
                int rec = 0;
                for (int k = i; k < j; k++) { // i-j区间例如第2到第5，可取的范围为2,3,4
                    int candiWeight = wtSpanAtIJ + getWeightAt(i, k) + getWeightAt(k + 1, j);
                    if (candiWeight < minWeight) {
                        minWeight = candiWeight;
                        rec = k;
                    }
                }
                setWeightAt(i, j, minWeight);
                setSolutionAt(i, j, rec);
            }
        }

        for (int i = 0; i < n; i++) {
            logger.info(Arrays.toString(weightMap[i]));
        }

        for (int i = 0; i < n; i++) {
            logger.info(Arrays.toString(solution[i]));
        }

        logger.info("TOTAL COST = " + getWeightAt(1, n));
        displaySolution(1, n);

    }

    private static void displaySolution(int I, int J) {
        if (I == J) {
            return; // 此种情况说明为单块整体，无需再次切割
        }
        int pos = getSolutionAt(I, J);
        logger.info("Between {} AND {}, choose No. {}", I, J, pos);
        int weight = getWtSpanAtIJ(I, J);
        logger.info("Lifting Wt.{}", weight);
        if (pos != I && pos != J - 1) {
            displaySolution(I, pos);
            displaySolution(pos + 1, J);
        } else if (pos == I) {
            displaySolution(pos + 1, J);
        } else if (pos == J - 1) {
            displaySolution(I, pos);
        }

    }

    // 类似于线段树，可以做缓存或者优化，目前先暂时用暴力法计算
    private static int getWtSpanAtIJ(int i, int j) {
        logger.debug("coming into sapn calc of {} and {}.", i, j);
        Integer cached = getSpanCacheAt(i, j);
        if ( cached != Integer.MIN_VALUE) {
            logger.debug("Hit cache!!!");
            return cached;
        }
        int sum = 0;
        for (int idx = i; idx <= j; idx++) {
            sum += lenArray[idx - 1];
        }
        setSpanCacheAt(i, j, sum);
        return sum;
    }

    public static void setWeightAt(int I, int J, int val) {
        weightMap[I - 1][J - 1] = val;
    }

    public static Integer getWeightAt(int I, int J) {
        return weightMap[I - 1][J - 1];
    }

    public static void setSolutionAt(int I, int J, int val) {
        solution[I - 1][J - 1] = val;
    }

    public static Integer getSolutionAt(int I, int J) {
        return solution[I - 1][J - 1];
    }

    public static void setSpanCacheAt(int I, int J, int val) {
        spanCache[I - 1][J - 1] = val;
    }

    public static int getSpanCacheAt(int I, int J) {
        return spanCache[I - 1][J - 1];
    }

    public static void main(String[] args) {
        init(new Integer[] {4, 4, 6, 7, 3, 8});
    }

}
