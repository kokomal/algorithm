/**
 * @Title: SubSet.java
 * @Package: yuanjun.chen.base.other
 * @Description: LEETCODE 78
 * @author: 陈元俊
 * @date: 2019年5月6日 上午11:09:00
 * @version V1.0
 * @Copyright: 2019 All rights reserved.
 */
package yuanjun.chen.base.other;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: SubSet
 * @Description: 给定一组不含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。
 * @author: 陈元俊
 * @date: 2019年5月6日 上午11:09:00
 */
public class SubSet {
    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums.length == 1) {
            res.add(new ArrayList<>());
            List<Integer> lis = new ArrayList<>();
            lis.add(nums[0]);
            res.add(lis);
        } else {
            int num = nums[0];
            int[] num2 = new int[nums.length - 1];
            System.arraycopy(nums, 1, num2, 0, nums.length - 1);
            List<List<Integer>> subRes = subsets(num2);
            for (List<Integer> sub : subRes) {
                res.add(sub);
                List<Integer> lis = new ArrayList<>(sub);
                lis.add(num);
                res.add(lis);
            }
        }
        return res;
    }
    
    
    public static List<List<Integer>> subsets2(int[] nums, int pos) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums.length == pos + 1) {
            res.add(new ArrayList<>());
            List<Integer> lis = new ArrayList<>();
            lis.add(nums[nums.length - 1]);
            res.add(lis);
        } else {
            int num = nums[pos];
            List<List<Integer>> subRes = subsets2(nums, pos + 1);
            for (List<Integer> sub : subRes) {
                res.add(sub);
                List<Integer> lis = new ArrayList<>(sub);
                lis.add(num);
                res.add(lis);
            }
        }
        return res;
    }
    
    public static void main(String[] args) {
        int[] nums = {1,2,3};
        List<List<Integer>> res = subsets2(nums, 0);
        for (List<Integer> lis : res) {
            System.out.println(lis);
        }
    }
}
