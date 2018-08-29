package yuanjun.chen.base.dynamicprogramming;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Matrix chain multiplication, MCOP
 */
public class MatrixChainMultiplicationAlgo {
    private static final Logger logger = LogManager.getLogger(MatrixChainMultiplicationAlgo.class);
    /**
     * ____A1___A2________Ak__Ak+1______An
     * p[0]  [1] [2] ...   [k]  [k+1] ... [n]
     * __a * b|b*c|c  ...  x|x * y|y  ...  z
     * <p>
     * M(i,j) = Min(M(i,k), M(k+1,j), Pi-1*Pk*Pj) ----->a*x*z
     **/
    protected static int[][] m;
    protected static int[][] s;

    /**
     * 根据矩阵的维度数组，生成最优的矩阵相乘顺序
     */
    public static void dpGenerateBestChain(Integer[] dms) {
        if (dms == null || dms.length < 2) {
            return;
        }

        int n = dms.length - 1;
        m = new int[n][n];
        s = new int[n][n];

        for (int lenMinusOne = 1; lenMinusOne < n; lenMinusOne++) { // 逐渐放开len，直到最大
            for (int i = 0; i < n - lenMinusOne; i++) {
                int j = i + lenMinusOne; // 右界
                m[i][j] = Integer.MAX_VALUE;
                for (int k = i; k < j; k++) {
                    int cost = m[i][k] + m[k + 1][j] + dms[i] * dms[k + 1] * dms[j + 1];
                    if (cost < m[i][j]) {
                        m[i][j] = cost;
                        s[i][j] = k;
                    }
                }
            }
        }
    }

    public static String fetchSequence() {
        return printAll(0, s.length - 1).toString();
    }

    public static int fetchFinalPrice() {
        return m[0][s.length - 1];
    }

    public static StringBuilder printAll(int i, int j) {
        if (i == j) {
            return new StringBuilder("A" + (i + 1));
        } else {
            StringBuilder sb = new StringBuilder("(");
            sb.append(printAll(i, s[i][j]));
            sb.append(printAll(s[i][j] + 1, j));
            sb.append(")");
            return sb;
        }
    }

    public static void main(String[] args) {
        int a = 19;
        int b = 13;
        int c = 8;
        int d = 17;
        System.out.printf("A size = %d * %d\n", a, b);
        System.out.printf("B size = %d * %d\n", b, c);
        System.out.printf("C size = %d * %d\n", c, d);

        System.out.println("(A*B)*C needs " + (a * b * c + a * c * d));
        System.out.println("A*(B*C) needs " + (b * c * d + a * b * d));
    }
}
