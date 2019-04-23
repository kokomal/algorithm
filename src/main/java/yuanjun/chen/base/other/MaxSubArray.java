/**
 * @Title: MaxSubArray.java
 * @Package: yuanjun.chen.base.other
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2019年4月23日 下午4:43:00
 * @version V1.0
 * @Copyright: 2019 All rights reserved.
 */
package yuanjun.chen.base.other;

/**
 * @ClassName: MaxSubArray
 * @Description: LeetCode 53 最大子序列和 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。 示例: 输入:
 *               [-2,1,-3,4,-1,2,1,-5,4], 输出: 6 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
 * @author: 陈元俊
 * @date: 2019年4月23日 下午4:43:00
 */
public class MaxSubArray {
    public static int maxSubArray(int[] nums) {
        int size = nums.length;
        return divideAndConquer(nums, 0, size - 1);
    }

    private static int divideAndConquer(int[] nums, int left, int right) {
        if (left >= right) {
            return nums[left];
        }
        int i, mid;
        mid = (left + right) / 2;
        int lmax = divideAndConquer(nums, left, mid - 1);
        int rmax = divideAndConquer(nums, mid + 1, right);
        int mmax = nums[mid]; // 很丑陋的左右爬策略
        int tmp = mmax;
        for (i = mid - 1; i >= left; --i) {
            tmp += nums[i];
            mmax = Math.max(mmax, tmp);
        }
        tmp = mmax;
        for (i = mid + 1; i <= right; ++i) {
            tmp += nums[i];
            mmax = Math.max(mmax, tmp);
        }
        return Math.max(mmax, Math.max(lmax, rmax));
    }

    private static int kadane(int[] nums) {
        int max = Integer.MIN_VALUE, sum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (sum <= 0) {
                sum = nums[i];
            } else {
                sum = sum + nums[i];
            }
            if (max < sum) {
                max = sum;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int[] nums = new int[] {-999, 994, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(maxSubArray(nums));
        System.out.println(kadane(nums));
    }
}
