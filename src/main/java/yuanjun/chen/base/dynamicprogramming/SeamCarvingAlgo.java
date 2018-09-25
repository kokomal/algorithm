/**
 * @Title: SeamCarvingAlgo.java
 * @Package: yuanjun.chen.base.dynamicprogramming
 * @Description: 基于接缝裁剪的图像压缩
 * @author: 陈元俊
 * @date: 2018年9月25日 下午2:48:16
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.dynamicprogramming;

import java.util.Arrays;

/**
 * @ClassName: SeamCarvingAlgo
 * @Description: 基于接缝裁剪的图像压缩(为了显示方便，采用Float，非Double)
 * @author: 陈元俊
 * @date: 2018年9月25日 下午2:48:16
 */
public class SeamCarvingAlgo {
    private static Float[][] damages; // 毁坏度m*n
    private static Integer[][] solutions; // 解决方案m*n
    private static Float[][] prices; // 每一个点的代价m*n

    private static int M;
    private static int N;

    public static void init(Float[][] srcDamages) {
        damages = srcDamages;
        M = damages.length;
        N = damages[0].length;
        solutions = new Integer[M][N];
        prices = new Float[M][N];
    }

    public static void solve() {
        System.arraycopy(damages[0], 0, prices[0], 0, N);
        for (int i = 1; i < M; i++) {
            for (int k = 0; k < N; k++) {
                // 左，中，右，取最小的price
                Float oldMid = prices[i - 1][k]; // 中间不可能越界
                Float oldLeft = priceAt(i - 1, k - 1); // 左边
                Float oldRight = priceAt(i - 1, k + 1); // 右边
                Float minOld = oldMid;
                int move = 0;
                if (oldLeft < minOld) {
                    minOld = oldLeft;
                    move = -1;
                }
                if (oldRight < minOld) {
                    minOld = oldRight;
                    move = 1;
                }
                prices[i][k] = minOld + damages[i][k];
                solutions[i][k] = move; // 回溯时，根据solution的标记，选择下一阶的点
            }
        }
    }

    private static void disp() {
        int bestTop = 0;
        Float minPrice = Float.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            Float candiPrice = prices[M - 1][i];
            if (candiPrice < minPrice) {
                bestTop = i;
                minPrice = candiPrice;
            }
        }
        System.out.println("BestTop = " + bestTop);
        for (int j = M - 1; j > 0; j--) {
            System.out.printf("Choose Points @[%d,%d]\n", j + 1, bestTop + 1);
            bestTop = solutions[j][bestTop] + bestTop;
        } // 最后一个solution要手动写，因为计算的时候没有算第0行的solution
        System.out.printf("Choose Points @[%d,%d]\n", 1, bestTop + 1);
    }
    
    private static Float priceAt(int i, int j) {
        if (j == -1 || j == N) {
            return Float.MAX_VALUE;
        }
        return prices[i][j];
    }
    
    public static void main(String[] args) {
        Float[][] srcDamages = new Float[][]{
            {0.05f, 2.0f, 3.0f, 0.02f},
            {2.5f, 0.06f, 0.07f, 0.22f},
            {12.0f, 9.0f, 0.07f, 0.32f},
            {22.0f, 10.0f, 8.0f, 0.01f}
        };
        init(srcDamages);
        System.out.println("--------DAMAGES----------");
        for (int i = M - 1; i >= 0; i--) {
            System.out.println(Arrays.toString(damages[i]));
        }
        solve();
        System.out.println("--------PRICES----------");
        for (int i = M - 1; i >= 0; i--) {
            System.out.println(Arrays.toString(prices[i]));
        }
        System.out.println("--------SOLUTIONS----------");
        for (int i = M - 1; i >= 0; i--) {
            System.out.println(Arrays.toString(solutions[i]));
        }
        disp();
    }
}
