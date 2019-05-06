/**
 * @Title: ArithSlice.java
 * @Package: yuanjun.chen.base.other
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2019年5月6日 下午5:28:11
 * @version V1.0
 * @Copyright: 2019 All rights reserved.
 */
package yuanjun.chen.base.other;

/**
 * @ClassName: ArithSlice
 * @Description: 倒数的slow方法，但贵在思路清晰
 * @author: 陈元俊
 * @date: 2019年5月6日 下午5:28:11
 */
public class ArithSlice {
    public static int numberOfArithmeticSlices(int[] A) {
        int len = A.length;
        if (len < 3) {
            return 0;
        }
        int span = 3;
        int count = 0;
        int[][] price = new int[len][len + 1]; // x,y为 start, span， val为gap
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len + 1; j++) {
                price[i][j] = Integer.MIN_VALUE;
            }
        }
        for (; span <= len; span++) {
            for (int i = 0; i + span - 1 < len; i++) { // 以span为宽度滑动窗口
                int head = A[i];
                int tail = A[i + span - 1];
                // [head, tail]
                if (span == 3) {
                    int mid = A[i + 1];
                    if (mid - head == tail - mid) { // 符合等差数列条件！
                        price[i][span] = mid - head;
                        count++; // 符合条件则累计
                    }
                } else if (price[i][span - 1] != Integer.MIN_VALUE) { // 前辈是等差数列
                    int gap = tail - A[i + span - 2];
                    if (price[i][span - 1] == gap) { // 等差数列的差与新的一致，则入伙！
                        price[i][span] = gap;
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public static int smart(int[] A) {
        if (A.length < 3) {
            return 0;
        }
        int sum = 0;
        int[] dp = new int[A.length];
        for (int i = 2; i < A.length; ++i) {
            if (A[i] + A[i - 2] == 2 * A[i - 1]) {
                dp[i] = dp[i - 1] + 1;
                sum += dp[i];
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(numberOfArithmeticSlices(new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}));
        System.out.println(smart(new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}));
    }
}
