/**
 * @Title: SortColors.java
 * @Package: yuanjun.chen.base.other
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2019年4月23日 下午5:14:25
 * @version V1.0
 * @Copyright: 2019 All rights reserved.
 */
package yuanjun.chen.base.other;

import java.util.Arrays;

/**
 * @ClassName: SortColors
 * @Description: LeetCode 75 颜色分类 给定一个包含红色、白色和蓝色，一共 n 个元素的数组，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
 *               此题中，我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。 示例: 输入: [2,0,2,1,1,0] 输出: [0,0,1,1,2,2]
 * @author: 陈元俊
 * @date: 2019年4月23日 下午5:14:25
 */
public class SortColors {
    public static void rawSortColors(int[] nums) {
        int zeros = 0;
        int ones = 0;
        for (int x : nums) {
            if (x == 0) {
                zeros++;
            }
            if (x == 1) {
                ones++;
            }
        }
        Arrays.fill(nums, 0);
        for (int j = zeros; j < ones + zeros; j++) {
            nums[j] = 1;
        }
        for (int j = ones + zeros; j < nums.length; j++) {
            nums[j] = 2;
        }
    }

    /*
     * 0  0  0  1  1  1  2  1  0  2  1  2  2  2
     *   
     *      ^         ^             ^
     *
     *    left        i            right
     **/
    public static void smartSortColors(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        int i = 0;
        while (i <= right) {
            System.out.println("-OLD-" + Arrays.toString(nums));
            System.out.println("I=" + i + " left=" + left + " right=" + right);
            if (nums[i] == 0) {
                swap(nums, left, i);
                left++;
                i++;
            } else if (nums[i] == 1) {
                i++;
            } else { // nums[i]=2此时需要定住i
                swap(nums, i, right);
                right--;
            }
            System.out.println("-NEW-" + Arrays.toString(nums));
        }
    }

    private static void swap(int[] nums, int a, int b) {
        int tmp = nums[a];
        nums[a] = nums[b];
        nums[b] = tmp;
    }

    public static void main(String[] args) {
        int[] orig = new int[] {2, 0, 2, 1, 1, 0};
        int[] num1 = Arrays.copyOf(orig, orig.length);
        int[] num2 = Arrays.copyOf(orig, orig.length);
        rawSortColors(num1);
        System.out.println(Arrays.toString(num1));
        smartSortColors(num2);
        System.out.println(Arrays.toString(num2));
    }
}
