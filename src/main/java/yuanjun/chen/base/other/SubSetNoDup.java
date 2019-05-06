/**
 * @Title: SubSet.java
 * @Package: yuanjun.chen.base.other
 * @Description: LEETCODE 90
 * @author: 陈元俊
 * @date: 2019年5月6日 上午11:09:00
 * @version V1.0
 * @Copyright: 2019 All rights reserved.
 */
package yuanjun.chen.base.other;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: SubSet
 * @Description: 给定一组不含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）不重复。
 * @author: 陈元俊
 * @date: 2019年5月6日 上午11:09:00
 */
public class SubSetNoDup {
    public static List<List<Integer>> subSetNoDup2(int[] nums, int pos) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums.length == pos + 1) {
            res.add(new ArrayList<>());
            List<Integer> lis = new ArrayList<>();
            lis.add(nums[nums.length - 1]);
            res.add(lis);
        } else {
            int num = nums[pos];
            List<List<Integer>> subRes = subSetNoDup2(nums, pos + 1);
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
        int[] nums = {1, 2, 2};
        List<List<Integer>> res = subSetNoDupWrapper(nums);
        for (List<Integer> lis : res) {
            System.out.println(lis);
        }
    }

    private static List<List<Integer>> subSetNoDupWrapper(int[] nums) {
        List<List<Integer>> res = subSetNoDup2(nums, 0);
        List<List<Integer>> resX = new ArrayList<>();
        Set<String> fullSet = new HashSet<>();
        for (int i = 0; i < res.size(); i++) {
            Collections.sort(res.get(i));
            if (!fullSet.contains(res.get(i).toString())) {
                resX.add(res.get(i));
                fullSet.add(res.get(i).toString());
            }
        }
        return resX;
    }
}
