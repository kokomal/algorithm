package yuanjun.chen.base.other;

import java.util.Arrays;

public class MaxProfit {
	// 暴力遍历法
	/** LEETCODE 121. */
	public static int maxProfit(int[] prices) {
		int maxProf = 0;
		int len = prices.length;
		for (int i = 0; i < len; i++) {
			for (int j = i + 1; j < len; j++) {
				int prof = prices[j] - prices[i];
				if (prof > maxProf) {
					maxProf = prof;
				}
			}
		}
		return maxProf;
	}

	/** LEETCODE 121. */
	public static int maxProfitDp(int[] prices) {
		int maxProf = 0;
		int len = prices.length;
		int min = prices[0];
		for (int i = 1; i < len; i++) {
			if (prices[i] < min) {
				min = prices[i];
			} else {
				int prof = prices[i] - min;
				if (prof > maxProf) {
					maxProf = prof;
				}
			}
		}
		return maxProf;
	}

	/* LEETCODE 122 */
	/**
	 * 在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。 随后，在第 4
	 * 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6-3 = 3 。.
	 */
	public static int maxProfit2(int[] prices) {
		if (prices.length <= 1) {
			return 0;
		}
		int[] prof = new int[prices.length];
		int gp = prices[1] - prices[0];
		prof[1] = gp > 0 ? gp : 0;
		for (int i = 2; i < prices.length; i++) {
			int candi1 = prof[i - 1]; // 不动
			int candidelta = 0;
			int maxProf = Integer.MIN_VALUE;
			int delta = 1;
			for (; delta < i; delta++) {
				// int gap = prices[i] - prof[delta];
				int gapMax = Integer.MIN_VALUE;
				for (int j = delta; j < i; j++) {
					int gap = prices[i] - prices[j];
					if (gap > gapMax) {
						gapMax = gap;
					}
				}
				if (gapMax > 0) {
					candidelta = prof[delta - 1] + gapMax;
				} else {
					candidelta = prof[delta - 1];
				}
				if (candidelta > maxProf) {
					maxProf = candidelta;
				}
				maxProf = Math.max(maxProf, prices[i] - prices[0]);
			}
			prof[i] = Math.max(maxProf, candi1);
			System.out.println(Arrays.toString(prof));
		}
		System.out.println(Arrays.toString(prof));
		return prof[prices.length - 1];
	}

	public static int maxProfitSmart(int[] prices) {
		int max = 0;
		for (int i = 1; i < prices.length; i++) {
			if (prices[i] > prices[i - 1]) {
				max = max + prices[i] - prices[i - 1];
			}
		}
		return max;
	}

	public static void main(String[] args) {
		System.out.println(maxProfit(new int[] { 7, 1, 5, 3, 6, 4 }));
		System.out.println(maxProfitDp(new int[] { 7, 1, 5, 3, 6, 4 }));
		System.out.println(maxProfit2(new int[] { 7, 1, 5, 3, 6, 4 }));
		System.out.println(maxProfitSmart(new int[] { 7, 1, 5, 3, 6, 4 }));
	}
}
